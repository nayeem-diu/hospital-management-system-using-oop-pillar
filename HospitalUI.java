import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

public class HospitalUI extends JFrame {

    // ---------- Design tokens: colors ----------
    private static final Color COLOR_SIDEBAR = new Color(0x12, 0x3B, 0x4A);
    private static final Color COLOR_SIDEBAR_HOVER = new Color(0x1E, 0x55, 0x66);
    private static final Color COLOR_BG = new Color(0xF5, 0xF7, 0xF8);
    private static final Color COLOR_CARD = Color.WHITE;
    private static final Color COLOR_ACCENT = new Color(0x1E, 0x8A, 0x99);
    private static final Color COLOR_DANGER = new Color(0xE4, 0x57, 0x2E);
    private static final Color COLOR_SUCCESS = new Color(0x2E, 0x8B, 0x57);
    private static final Color COLOR_WARNING = new Color(0xE0, 0xA5, 0x1C);
    private static final Color COLOR_TEXT = new Color(0x1D, 0x2B, 0x30);
    private static final Color COLOR_TEXT_MUTED = new Color(0x6B, 0x7B, 0x80);
    private static final Color COLOR_BORDER = new Color(0xDC, 0xE3, 0xE5);

    // ---------- Design tokens: type ----------
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_NAV = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BOLD_LABEL = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_CARD_NUMBER = new Font("Segoe UI", Font.BOLD, 30);

    private final Hospital hospital;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
    private final JLabel pageTitleLabel = new JLabel();
    private final JLabel alertBanner = new JLabel();

    private JLabel statDoctors;
    private JLabel statPatients;
    private JLabel statEmptyBeds;
    private JLabel statBloodAlerts;

    private DefaultTableModel doctorTableModel;
    private JTable doctorTable;

    private DefaultTableModel patientTableModel;
    private JTable patientTable;

    private JPanel bedGridPanel;

    private DefaultTableModel bloodTableModel;
    private JTable bloodTable;

    private JTextArea billingOutput;

    public HospitalUI(Hospital hospital) {
        super("Hospital Management System");
        this.hospital = hospital;
        initFrame();
        startBloodMonitor();
    }

    private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1150, 720);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout());

        add(buildSidebar(), BorderLayout.WEST);
        add(buildMainArea(), BorderLayout.CENTER);

        showPage("Dashboard");
    }

    // =========================================================================
    // Sidebar navigation
    // =========================================================================

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(COLOR_SIDEBAR);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(28, 20, 20, 20));

        JLabel logo = new JLabel("Green Valley Hospital");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel logoSub = new JLabel("Management System");
        logoSub.setFont(FONT_SUBTITLE);
        logoSub.setForeground(new Color(0xA9, 0xC6, 0xCC));
        logoSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(logoSub);
        sidebar.add(Box.createVerticalStrut(36));

        String[] pages = {"Dashboard", "Doctors", "Patients", "Beds", "Blood Bank", "Billing"};
        for (String page : pages) {
            JButton navBtn = createNavButton(page);
            navBtn.addActionListener(e -> showPage(page));
            sidebar.add(navBtn);
            sidebar.add(Box.createVerticalStrut(6));
        }

        sidebar.add(Box.createVerticalGlue());

        JButton exitBtn = createNavButton("Exit");
        exitBtn.setForeground(new Color(0xFF, 0xB4, 0xA0));
        exitBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?", "Exit",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        sidebar.add(exitBtn);

        return sidebar;
    }

    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFont(FONT_NAV);
        btn.setForeground(Color.WHITE);
        btn.setBackground(COLOR_SIDEBAR);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(COLOR_SIDEBAR_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(COLOR_SIDEBAR);
            }
        });
        return btn;
    }

    // =========================================================================
    // Main content area + page switching
    // =========================================================================

    private JPanel buildMainArea() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(COLOR_BG);
        main.setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        pageTitleLabel.setFont(FONT_TITLE);
        pageTitleLabel.setForeground(COLOR_TEXT);
        header.add(pageTitleLabel, BorderLayout.WEST);

        alertBanner.setFont(FONT_SUBTITLE);
        alertBanner.setForeground(COLOR_DANGER);
        header.add(alertBanner, BorderLayout.EAST);

        main.add(header, BorderLayout.NORTH);

        contentPanel.setOpaque(false);
        contentPanel.add(buildDashboardPanel(), "Dashboard");
        contentPanel.add(buildDoctorsPanel(), "Doctors");
        contentPanel.add(buildPatientsPanel(), "Patients");
        contentPanel.add(buildBedsPanel(), "Beds");
        contentPanel.add(buildBloodBankPanel(), "Blood Bank");
        contentPanel.add(buildBillingPanel(), "Billing");

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        wrapper.add(contentPanel, BorderLayout.CENTER);
        main.add(wrapper, BorderLayout.CENTER);

        return main;
    }

    private void showPage(String name) {
        pageTitleLabel.setText(name);
        cardLayout.show(contentPanel, name);
        if (name.equals("Dashboard")) {
            refreshDashboard();
        } else if (name.equals("Doctors")) {
            refreshDoctorTable();
        } else if (name.equals("Patients")) {
            refreshPatientTable();
        } else if (name.equals("Beds")) {
            refreshBedGrid();
        } else if (name.equals("Blood Bank")) {
            refreshBloodTable();
        }
    }

    // =========================================================================
    // Dashboard page
    // =========================================================================

    private JPanel buildDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        statDoctors = new JLabel("0");
        statPatients = new JLabel("0");
        statEmptyBeds = new JLabel("0");
        statBloodAlerts = new JLabel("0");

        JPanel cardsRow = new JPanel(new GridLayout(1, 4, 20, 0));
        cardsRow.setOpaque(false);
        cardsRow.add(createStatCard("Total Doctors", statDoctors, COLOR_ACCENT));
        cardsRow.add(createStatCard("Admitted Patients", statPatients, COLOR_SUCCESS));
        cardsRow.add(createStatCard("Empty Beds", statEmptyBeds, COLOR_WARNING));
        cardsRow.add(createStatCard("Low Blood Groups", statBloodAlerts, COLOR_DANGER));

        JLabel welcome = new JLabel("Here's a quick snapshot of the hospital right now.");
        welcome.setFont(FONT_SUBTITLE);
        welcome.setForeground(COLOR_TEXT_MUTED);
        welcome.setBorder(BorderFactory.createEmptyBorder(0, 4, 20, 0));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(welcome, BorderLayout.NORTH);
        top.add(cardsRow, BorderLayout.CENTER);
        panel.add(top, BorderLayout.NORTH);

        JButton refreshBtn = createNeutralButton("Refresh");
        refreshBtn.addActionListener(e -> refreshDashboard());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 20));
        bottom.setOpaque(false);
        bottom.add(refreshBtn);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createStatCard(String title, JLabel valueLabel, Color accent) {
        RoundedPanel card = new RoundedPanel(14);
        card.setBackground(COLOR_CARD);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        card.setPreferredSize(new Dimension(230, 110));

        JPanel accentBar = new JPanel();
        accentBar.setBackground(accent);
        accentBar.setPreferredSize(new Dimension(5, 0));
        card.add(accentBar, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FONT_LABEL);
        titleLabel.setForeground(COLOR_TEXT_MUTED);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        valueLabel.setFont(FONT_CARD_NUMBER);
        valueLabel.setForeground(COLOR_TEXT);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(6));
        textPanel.add(valueLabel);

        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    private void refreshDashboard() {
        statDoctors.setText(String.valueOf(hospital.getDoctorList().size()));
        statPatients.setText(String.valueOf(hospital.getPatientList().size()));
        statEmptyBeds.setText(String.valueOf(hospital.getBedManager().getEmptyCount()));

        int lowCount = 0;
        for (int qty : hospital.getBloodBank().getStockSnapshot().values()) {
            if (qty <= 2) lowCount++;
        }
        statBloodAlerts.setText(String.valueOf(lowCount));

        updateAlertBanner();
    }

    private void updateAlertBanner() {
        if (hospital.isBloodStockLow()) {
            alertBanner.setText("Low blood stock: " + hospital.getLowStockInfo().trim());
        } else {
            alertBanner.setText("");
        }
    }

    private JPanel buildDoctorsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.setOpaque(false);

        doctorTableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Age", "Gender", "Specialist"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        doctorTable = new JTable(doctorTableModel);
        styleTable(doctorTable);
        panel.add(wrapInCard(new JScrollPane(doctorTable)), BorderLayout.CENTER);

        JTextField nameField = new JTextField(12);
        JTextField ageField = new JTextField(4);
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        JTextField specialistField = new JTextField(12);
        JButton addBtn = createPrimaryButton("Add Doctor");

        JPanel addRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 8));
        addRow.setOpaque(false);
        addRow.add(labeledField("Name", nameField));
        addRow.add(labeledField("Age", ageField));
        addRow.add(labeledField("Gender", genderBox));
        addRow.add(labeledField("Specialist", specialistField));
        addRow.add(buttonRowWrap(addBtn));

        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String specialist = specialistField.getText().trim();
            if (name.isEmpty() || ageText.isEmpty() || specialist.isEmpty()) {
                showWarning("Please fill in Name, Age, and Specialist.");
                return;
            }
            int age;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException ex) {
                showWarning("Age must be a number.");
                return;
            }
            String gender = (String) genderBox.getSelectedItem();
            String id = hospital.addDoctor(name, age, gender, specialist);
            showInfo("Doctor added successfully. ID: " + id);
            nameField.setText("");
            ageField.setText("");
            specialistField.setText("");
            refreshDoctorTable();
            refreshDashboard();
        });

        JTextField deleteIdField = new JTextField(10);
        JButton deleteBtn = createDangerButton("Delete Doctor");
        JPanel deleteRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 8));
        deleteRow.setOpaque(false);
        deleteRow.add(labeledField("Doctor ID", deleteIdField));
        deleteRow.add(buttonRowWrap(deleteBtn));

        deleteBtn.addActionListener(e -> {
            String id = deleteIdField.getText().trim();
            if (id.isEmpty()) {
                showWarning("Enter a Doctor ID.");
                return;
            }
            if (hospital.deleteDoctor(id)) {
                showInfo("Doctor deleted.");
                deleteIdField.setText("");
                refreshDoctorTable();
                refreshDashboard();
            } else {
                showWarning("No doctor found with ID " + id);
            }
        });

        JPanel southWrap = new JPanel();
        southWrap.setOpaque(false);
        southWrap.setLayout(new BoxLayout(southWrap, BoxLayout.Y_AXIS));
        southWrap.add(wrapInCard(addRow));
        southWrap.add(Box.createVerticalStrut(12));
        southWrap.add(wrapInCard(deleteRow));

        panel.add(southWrap, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshDoctorTable() {
        doctorTableModel.setRowCount(0);
        for (Doctor d : hospital.getDoctorList()) {
            doctorTableModel.addRow(new Object[]{
                    d.getId(), d.getName(), d.getAge(), d.getGender(), d.getSpecialist()
            });
        }
    }

    // =========================================================================
    // Patients page
    // =========================================================================

    private JPanel buildPatientsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.setOpaque(false);

        patientTableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Age", "Gender", "Disease", "Bed", "Blood Bags"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        patientTable = new JTable(patientTableModel);
        styleTable(patientTable);
        panel.add(wrapInCard(new JScrollPane(patientTable)), BorderLayout.CENTER);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(FONT_BOLD_LABEL);
        tabs.addTab("Add Patient", buildAddPatientTab());
        tabs.addTab("Release Patient", buildReleasePatientTab());
        tabs.addTab("Update Patient", buildUpdatePatientTab());

        panel.add(wrapInCard(tabs), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildAddPatientTab() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        row.setOpaque(false);

        JTextField nameField = new JTextField(12);
        JTextField ageField = new JTextField(4);
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        JTextField diseaseField = new JTextField(14);
        JCheckBox needBedBox = new JCheckBox("Needs a bed");
        needBedBox.setOpaque(false);
        needBedBox.setFont(FONT_LABEL);
        JButton addBtn = createPrimaryButton("Add Patient");

        row.add(labeledField("Name", nameField));
        row.add(labeledField("Age", ageField));
        row.add(labeledField("Gender", genderBox));
        row.add(labeledField("Disease", diseaseField));
        row.add(buttonRowWrap(needBedBox));
        row.add(buttonRowWrap(addBtn));

        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String disease = diseaseField.getText().trim();
            if (name.isEmpty() || ageText.isEmpty() || disease.isEmpty()) {
                showWarning("Please fill in Name, Age, and Disease.");
                return;
            }
            int age;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException ex) {
                showWarning("Age must be a number.");
                return;
            }
            String gender = (String) genderBox.getSelectedItem();
            String id = hospital.addPatient(name, age, gender, disease, needBedBox.isSelected());
            showInfo("Patient added successfully. ID: " + id);
            nameField.setText("");
            ageField.setText("");
            diseaseField.setText("");
            needBedBox.setSelected(false);
            refreshPatientTable();
            refreshBedGrid();
            refreshDashboard();
        });

        return row;
    }

    private JPanel buildReleasePatientTab() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        row.setOpaque(false);

        JTextField idField = new JTextField(12);
        JButton releaseBtn = createDangerButton("Release Patient");

        row.add(labeledField("Patient ID", idField));
        row.add(buttonRowWrap(releaseBtn));

        releaseBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            if (id.isEmpty()) {
                showWarning("Enter a Patient ID.");
                return;
            }
            if (hospital.releasePatient(id)) {
                showInfo("Patient released. Bed freed if one was assigned.");
                idField.setText("");
                refreshPatientTable();
                refreshBedGrid();
                refreshDashboard();
            } else {
                showWarning("No patient found with ID " + id);
            }
        });

        return row;
    }

    private JPanel buildUpdatePatientTab() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        row.setOpaque(false);

        JTextField idField = new JTextField(10);
        JTextField newNameField = new JTextField(12);
        JTextField newAgeField = new JTextField(4);
        JTextField newDiseaseField = new JTextField(14);
        JButton updateBtn = createPrimaryButton("Update Patient");

        row.add(labeledField("Patient ID", idField));
        row.add(labeledField("New Name (optional)", newNameField));
        row.add(labeledField("New Age (optional)", newAgeField));
        row.add(labeledField("New Disease (optional)", newDiseaseField));
        row.add(buttonRowWrap(updateBtn));

        updateBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            if (id.isEmpty()) {
                showWarning("Enter a Patient ID.");
                return;
            }
            Patient patient = hospital.findPatient(id);
            if (patient == null) {
                showWarning("No patient found with ID " + id);
                return;
            }

            String newName = newNameField.getText().trim();
            String newAgeText = newAgeField.getText().trim();
            String newDisease = newDiseaseField.getText().trim();

            if (!newName.isEmpty()) {
                patient.setName(newName);
            }
            if (!newAgeText.isEmpty()) {
                try {
                    int newAge = Integer.parseInt(newAgeText);
                    if (newAge > 0) patient.setAge(newAge);
                } catch (NumberFormatException ex) {
                    showWarning("New age must be a number.");
                    return;
                }
            }
            if (!newDisease.isEmpty()) {
                patient.setDisease(newDisease);
            }

            showInfo("Patient details updated.");
            idField.setText("");
            newNameField.setText("");
            newAgeField.setText("");
            newDiseaseField.setText("");
            refreshPatientTable();
        });

        return row;
    }

    private void refreshPatientTable() {
        patientTableModel.setRowCount(0);
        for (Patient p : hospital.getPatientList()) {
            String bed = (p.getBedNumber() == -1) ? "-" : String.valueOf(p.getBedNumber());
            patientTableModel.addRow(new Object[]{
                    p.getId(), p.getName(), p.getAge(), p.getGender(),
                    p.getDisease(), bed, p.getBloodBagsBooked()
            });
        }
    }

    // =========================================================================
    // Beds page
    // =========================================================================

    private JPanel buildBedsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.setOpaque(false);

        bedGridPanel = new JPanel(new GridLayout(5, 10, 10, 10));
        bedGridPanel.setOpaque(false);

        JPanel legend = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        legend.setOpaque(false);
        legend.add(legendDot(COLOR_SUCCESS, "Empty"));
        legend.add(legendDot(COLOR_DANGER, "Occupied"));

        JButton refreshBtn = createNeutralButton("Refresh");
        refreshBtn.addActionListener(e -> refreshBedGrid());

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(legend, BorderLayout.WEST);
        JPanel refreshWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        refreshWrap.setOpaque(false);
        refreshWrap.add(refreshBtn);
        top.add(refreshWrap, BorderLayout.EAST);

        panel.add(top, BorderLayout.NORTH);
        panel.add(wrapInCard(new JScrollPane(bedGridPanel)), BorderLayout.CENTER);

        return panel;
    }

    private JPanel legendDot(Color color, String text) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        p.setOpaque(false);
        JPanel dot = new JPanel();
        dot.setPreferredSize(new Dimension(12, 12));
        dot.setBackground(color);
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(COLOR_TEXT_MUTED);
        p.add(dot);
        p.add(label);
        return p;
    }

    private void refreshBedGrid() {
        bedGridPanel.removeAll();
        String[] occupants = hospital.getBedManager().getOccupantSnapshot();
        for (int i = 0; i < occupants.length; i++) {
            boolean empty = occupants[i] == null;
            JPanel cell = new JPanel(new BorderLayout());
            cell.setBackground(empty ? new Color(0xE4, 0xF3, 0xEA) : new Color(0xFC, 0xE4, 0xDD));
            cell.setBorder(BorderFactory.createLineBorder(empty ? COLOR_SUCCESS : COLOR_DANGER, 1));
            JLabel numLabel = new JLabel("Bed " + (i + 1), SwingConstants.CENTER);
            numLabel.setFont(FONT_BOLD_LABEL);
            numLabel.setForeground(COLOR_TEXT);
            cell.add(numLabel, BorderLayout.CENTER);
            cell.setToolTipText(empty ? "Empty" : "Patient: " + occupants[i]);
            bedGridPanel.add(cell);
        }
        bedGridPanel.revalidate();
        bedGridPanel.repaint();
    }

    // =========================================================================
    // Blood Bank page
    // =========================================================================

    private JPanel buildBloodBankPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.setOpaque(false);

        bloodTableModel = new DefaultTableModel(
                new Object[]{"Blood Group", "Bags Available", "Price / Bag"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        bloodTable = new JTable(bloodTableModel);
        styleTable(bloodTable);
        panel.add(wrapInCard(new JScrollPane(bloodTable)), BorderLayout.CENTER);

        JComboBox<String> groupBox = new JComboBox<>(
                new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"});
        JTextField patientIdField = new JTextField(10);
        JTextField qtyField = new JTextField(4);
        JButton bookBtn = createPrimaryButton("Book Blood");

        JPanel formRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 8));
        formRow.setOpaque(false);
        formRow.add(labeledField("Patient ID", patientIdField));
        formRow.add(labeledField("Blood Group", groupBox));
        formRow.add(labeledField("Bags Needed", qtyField));
        formRow.add(buttonRowWrap(bookBtn));

        bookBtn.addActionListener(e -> {
            String pid = patientIdField.getText().trim();
            String group = (String) groupBox.getSelectedItem();
            String qtyText = qtyField.getText().trim();
            if (pid.isEmpty() || qtyText.isEmpty()) {
                showWarning("Enter Patient ID and quantity.");
                return;
            }
            if (hospital.findPatient(pid) == null) {
                showWarning("No admitted patient found with ID " + pid);
                return;
            }
            int qty;
            try {
                qty = Integer.parseInt(qtyText);
            } catch (NumberFormatException ex) {
                showWarning("Bags needed must be a number.");
                return;
            }
            if (hospital.bookBlood(pid, group, qty)) {
                showInfo(qty + " bag(s) of " + group + " booked. Price: "
                        + (qty * BloodBank.PRICE_PER_BAG) + " tk");
                qtyField.setText("");
                refreshBloodTable();
                refreshPatientTable();
                refreshDashboard();
            } else {
                showWarning("Booking failed. Not enough stock available.");
            }
        });

        panel.add(wrapInCard(formRow), BorderLayout.SOUTH);
        return panel;
    }

    private void refreshBloodTable() {
        bloodTableModel.setRowCount(0);
        Map<String, Integer> stock = hospital.getBloodBank().getStockSnapshot();
        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            bloodTableModel.addRow(new Object[]{
                    entry.getKey(), entry.getValue(), BloodBank.PRICE_PER_BAG + " tk"
            });
        }
        updateAlertBanner();
    }

    // =========================================================================
    // Billing page
    // =========================================================================

    private JPanel buildBillingPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.setOpaque(false);

        JTextField patientIdField = new JTextField(10);
        JTextField bedDaysField = new JTextField(4);
        JTextField bloodBagsField = new JTextField(4);
        JButton generateBtn = createPrimaryButton("Generate Bill");

        JPanel formRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 8));
        formRow.setOpaque(false);
        formRow.add(labeledField("Patient ID", patientIdField));
        formRow.add(labeledField("Bed Days Used", bedDaysField));
        formRow.add(labeledField("Blood Bags Used", bloodBagsField));
        formRow.add(buttonRowWrap(generateBtn));

        billingOutput = new JTextArea();
        billingOutput.setEditable(false);
        billingOutput.setFont(new Font("Consolas", Font.PLAIN, 14));
        billingOutput.setBackground(COLOR_CARD);
        billingOutput.setForeground(COLOR_TEXT);
        billingOutput.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        billingOutput.setText("No invoice generated yet.\nFill the form above and click \"Generate Bill\".");

        panel.add(wrapInCard(formRow), BorderLayout.NORTH);
        panel.add(wrapInCard(new JScrollPane(billingOutput)), BorderLayout.CENTER);

        generateBtn.addActionListener(e -> {
            String pid = patientIdField.getText().trim();
            if (pid.isEmpty()) {
                showWarning("Enter a Patient ID.");
                return;
            }
            if (hospital.findPatient(pid) == null) {
                showWarning("No patient found with ID " + pid);
                return;
            }
            int bedDays;
            int bloodBags;
            try {
                bedDays = Integer.parseInt(bedDaysField.getText().trim());
                bloodBags = Integer.parseInt(bloodBagsField.getText().trim());
            } catch (NumberFormatException ex) {
                showWarning("Bed days and blood bags must be numbers.");
                return;
            }

            hospital.generateBill(pid, bedDays, bloodBags);
            Billing billing = hospital.getBilling();

            StringBuilder sb = new StringBuilder();
            sb.append("BILLING INVOICE\n");
            sb.append("================================\n");
            sb.append("Patient ID     : ").append(pid).append("\n");
            sb.append("Bed Days       : ").append(bedDays).append("\n");
            sb.append("Blood Bags     : ").append(bloodBags).append("\n");
            sb.append("--------------------------------\n");
            sb.append("Subtotal       : ").append(billing.getLastSubtotal()).append(" tk\n");
            if (billing.getLastDiscount() > 0) {
                sb.append("Discount (10%) : -").append(billing.getLastDiscount()).append(" tk\n");
            } else {
                sb.append("Discount       : Not applicable\n");
            }
            sb.append("--------------------------------\n");
            sb.append("Total Payable  : ").append(billing.getLastTotal()).append(" tk\n");

            billingOutput.setText(sb.toString());
        });

        return panel;
    }

    // =========================================================================
    // Shared small helpers (buttons, cards, form fields, tables, dialogs)
    // =========================================================================

    private JPanel wrapInCard(Component c) {
        RoundedPanel card = new RoundedPanel(14);
        card.setBackground(COLOR_CARD);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        card.add(c, BorderLayout.CENTER);
        return card;
    }

    private JPanel labeledField(String label, JComponent field) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        JLabel l = new JLabel(label);
        l.setFont(FONT_LABEL);
        l.setForeground(COLOR_TEXT_MUTED);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        Dimension pref = field.getPreferredSize();
        field.setMaximumSize(new Dimension(Math.max(pref.width, 80), 32));
        p.add(l);
        p.add(Box.createVerticalStrut(4));
        p.add(field);
        return p;
    }

    private JPanel buttonRowWrap(JComponent component) {
        JPanel wrap = new JPanel();
        wrap.setOpaque(false);
        wrap.setLayout(new BoxLayout(wrap, BoxLayout.Y_AXIS));
        wrap.add(Box.createVerticalStrut(20));
        wrap.add(component);
        return wrap;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(28);
        table.setFont(FONT_LABEL);
        table.setForeground(COLOR_TEXT);
        table.setGridColor(COLOR_BORDER);
        table.setSelectionBackground(new Color(0xDD, 0xEE, 0xF0));
        table.setSelectionForeground(COLOR_TEXT);
        table.getTableHeader().setFont(FONT_BOLD_LABEL);
        table.getTableHeader().setBackground(COLOR_BG);
        table.getTableHeader().setForeground(COLOR_TEXT);
        table.setFillsViewportHeight(true);
    }

    private JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        styleButton(btn, COLOR_ACCENT, Color.WHITE);
        return btn;
    }

    private JButton createDangerButton(String text) {
        JButton btn = new JButton(text);
        styleButton(btn, COLOR_DANGER, Color.WHITE);
        return btn;
    }

    private JButton createNeutralButton(String text) {
        JButton btn = new JButton(text);
        styleButton(btn, new Color(0xE7, 0xEC, 0xED), COLOR_TEXT);
        return btn;
    }

    private void styleButton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(FONT_BOLD_LABEL);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void showWarning(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Check input", JOptionPane.WARNING_MESSAGE);
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Done", JOptionPane.INFORMATION_MESSAGE);
    }

    // A plain JPanel that paints itself with rounded corners, used for every
    // "card" in the UI (stat cards, table wrappers, form wrappers).
    private static class RoundedPanel extends JPanel {
        private final int radius;

        RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.setColor(COLOR_BORDER);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // =========================================================================
    // Background monitor thread — same idea as the one in Main.java, just
    // updated to refresh a GUI label instead of printing to the console.
    // =========================================================================

    private void startBloodMonitor() {
        Thread monitor = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20000);
                    SwingUtilities.invokeLater(this::updateAlertBanner);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        monitor.setDaemon(true);
        monitor.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // fall back to the default look and feel
            }
            Hospital hospital = new Hospital();
            HospitalUI ui = new HospitalUI(hospital);
            ui.setVisible(true);
        });
    }
}
