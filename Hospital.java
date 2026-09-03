import java.util.ArrayList;
import java.util.List;

class Hospital {
    private List<Doctor> doctors;
    private List<Patient> patients;
    private Bed bedManager;
    private BloodBank bloodBank;
    private Billing billing;

    private int doctorCounter;
    private int patientCounter;

    public Hospital() {
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        bedManager = new Bed();
        bloodBank = new BloodBank();
        billing = new Billing();
        doctorCounter = 1;
        patientCounter = 1;
    }

    public String addDoctor(String name, int age, String gender, String specialist) {
        String id = "DOC-" + String.format("%03d", doctorCounter++);
        doctors.add(new Doctor(id, name, age, gender, specialist));
        return id;
    }

    public boolean deleteDoctor(String id) {
        return doctors.removeIf(d -> d.getId().equalsIgnoreCase(id));
    }

    public void showAllDoctors() {
        if (doctors.isEmpty()) {
            System.out.println("No Doctor found");
            return;
        }
        for (Person p : doctors) {
            p.showDetails();
        }
    }

    public Doctor findDoctor(String id) {
        for (Doctor d : doctors) {
            if (d.getId().equalsIgnoreCase(id)) return d;
        }
        return null;
    }

    public String addPatient(String name, int age, String gender, String disease, boolean needBed) {
        String id = "PAT-" + String.format("%03d", patientCounter++);
        Patient p = new Patient(id, name, age, gender, disease);

        if (needBed) {
            int bedNo = bedManager.assignBed(id);
            if (bedNo == -1) {
                System.out.println("Sorry! No bed assigned to the patient");
            } else {
                p.setBedNumber(bedNo);
                System.out.println("Bed No " + bedNo + " will be assigned to the patient");
            }
        }
        patients.add(p);
        return id;
    }

    public boolean releasePatient(String id) {
        Patient target = findPatient(id);
        if (target == null) return false;
        if (target.getBedNumber() != -1) {
            bedManager.releaseBed(target.getBedNumber());
        }
        patients.remove(target);
        return true;
    }

    public Patient findPatient(String id) {
        for (Patient p : patients) {
            if (p.getId().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    public void showAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No Patient found");
            return;
        }
        for (Person p : patients) {
            p.showDetails();
        }
    }

    public void showBedStatus() {
        bedManager.showBedStatus();
    }

    public void showBloodBank() {
        bloodBank.showStock();
    }

    public boolean bookBlood(String patientId, String group, int qty) {
        Patient p = findPatient(patientId);
        if (p == null) return false;
        boolean success = bloodBank.bookBlood(group, qty);
        if (success) {
            p.addBloodBags(qty);
        }
        return success;
    }

    public boolean isValidBloodGroup(String group) {
        return bloodBank.isValidGroup(group);
    }

    public boolean isBloodStockLow() {
        return bloodBank.isAnyGroupLow();
    }

    public String getLowStockInfo() {
        return bloodBank.getLowStockGroups();
    }

    public double generateBill(String patientId, int bedDays, int bloodBags) {
        return billing.generateBill(patientId, bedDays, bloodBags);
    }

    // ===================== NEW: getters added for the Swing GUI =====================
    // The console version never needed these because Main.java only ever called
    // methods like showAllDoctors() that print straight to System.out. A GUI needs
    // the raw data instead, so it can put it in tables, cards, and grids.

    public List<Doctor> getDoctorList() {
        return new ArrayList<>(doctors); // copy, so the GUI can't edit the real list
    }

    public List<Patient> getPatientList() {
        return new ArrayList<>(patients);
    }

    public Bed getBedManager() {
        return bedManager;
    }

    public BloodBank getBloodBank() {
        return bloodBank;
    }

    public Billing getBilling() {
        return billing;
    }
}
