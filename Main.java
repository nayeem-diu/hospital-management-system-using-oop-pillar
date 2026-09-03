import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        Scanner sc = new Scanner(System.in);

        Thread bloodMonitorThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20000);
                    if (hospital.isBloodStockLow()) {
                        System.out.println("\n[ALERT - Background Monitor Thread] "
                                + "Stock is running low for the following blood groups: "
                                + hospital.getLowStockInfo());
                        System.out.print("Enter your choice: ");
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        bloodMonitorThread.setDaemon(true);
        bloodMonitorThread.start();

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter your choice: ");
            String choiceStr = sc.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (1-11).");
                continue;
            }

            switch (choice) {
                case 1: // Add Doctor
                    System.out.print("Doctor Name: ");
                    String dName = sc.nextLine();
                    System.out.print("Age: ");
                    int dAge = readInt(sc);
                    System.out.print("Gender: ");
                    String dGender = sc.nextLine();
                    System.out.print("Specialist: ");
                    String specialist = sc.nextLine();
                    String newDocId = hospital.addDoctor(dName, dAge, dGender, specialist);
                    System.out.println("Doctor added successfully. Doctor ID: " + newDocId);
                    break;

                case 2: // Delete Doctor
                    System.out.print("Enter the Doctor ID to delete: ");
                    String delDocId = sc.nextLine();
                    if (hospital.deleteDoctor(delDocId)) {
                        System.out.println("Doctor has been deleted.");
                    } else {
                        System.out.println("No doctor found with this ID.");
                    }
                    break;

                case 3: // Show all doctors
                    hospital.showAllDoctors();
                    break;

                case 4: // Add Patient
                    System.out.print("Patient Name: ");
                    String pName = sc.nextLine();
                    System.out.print("Age: ");
                    int pAge = readInt(sc);
                    System.out.print("Gender: ");
                    String pGender = sc.nextLine();
                    System.out.print("Disease: ");
                    String disease = sc.nextLine();
                    System.out.print("Need a bed? (yes/no): ");
                    String needBedInput = sc.nextLine().trim().toLowerCase();
                    boolean needBed = needBedInput.equals("yes") || needBedInput.equals("y");
                    String newPatId = hospital.addPatient(pName, pAge, pGender, disease, needBed);
                    System.out.println("Patient added successfully. Patient ID: " + newPatId);
                    break;

                case 5: // Release Patient
                    System.out.print("Enter the Patient ID to release: ");
                    String relId = sc.nextLine();
                    if (hospital.releasePatient(relId)) {
                        System.out.println("Patient released, bed freed (if any was assigned).");
                    } else {
                        System.out.println("No patient found with this ID.");
                    }
                    break;

                case 6: // Update patient details
                    System.out.print("Enter the Patient ID to update: ");
                    String updId = sc.nextLine();
                    Patient patientToUpdate = hospital.findPatient(updId);
                    if (patientToUpdate == null) {
                        System.out.println("No patient found with this ID.");
                        break;
                    }
                    System.out.print("New Name (leave blank to keep current): ");
                    String newName = sc.nextLine();
                    if (!newName.isBlank()) patientToUpdate.setName(newName);

                    System.out.print("New Age (enter 0 to keep current): ");
                    int newAge = readInt(sc);
                    if (newAge > 0) patientToUpdate.setAge(newAge);

                    System.out.print("New Disease (leave blank to keep current): ");
                    String newDisease = sc.nextLine();
                    if (!newDisease.isBlank()) patientToUpdate.setDisease(newDisease);

                    System.out.println("Patient details updated.");
                    break;

                case 7: // Show all patients
                    hospital.showAllPatients();
                    break;

                case 8: // Show bed
                    hospital.showBedStatus();
                    break;

                case 9: // Show blood bank + booking option
                    hospital.showBloodBank();
                    System.out.print("\nWould you like to book blood? (yes/no): ");
                    String wantBook = sc.nextLine().trim().toLowerCase();
                    if (wantBook.equals("yes") || wantBook.equals("y")) {
                        System.out.print("Patient ID: ");
                        String bookPatId = sc.nextLine();
                        if (hospital.findPatient(bookPatId) == null) {
                            System.out.println("No admitted patient found with this ID.");
                            break;
                        }
                        System.out.print("Blood Group (e.g. A+, O-): ");
                        String group = sc.nextLine().trim();
                        if (!hospital.isValidBloodGroup(group)) {
                            System.out.println("Invalid blood group.");
                            break;
                        }
                        System.out.print("How many bags do you need: ");
                        int qty = readInt(sc);
                        if (hospital.bookBlood(bookPatId, group, qty)) {
                            System.out.println(qty + " bag(s) of " + group + " blood booked. "
                                    + "Price: " + (qty * BloodBank.PRICE_PER_BAG) + " tk");
                        } else {
                            System.out.println("Booking failed! Not enough stock available.");
                        }
                    }
                    break;

                case 10: // Billing manage
                    System.out.print("Patient ID: ");
                    String billPatId = sc.nextLine();
                    if (hospital.findPatient(billPatId) == null) {
                        System.out.println("No patient found with this ID.");
                        break;
                    }
                    System.out.print("How many days was the bed used: ");
                    int bedDays = readInt(sc);
                    System.out.print("Total blood bags used: ");
                    int bloodBags = readInt(sc);
                    hospital.generateBill(billPatId, bedDays, bloodBags);
                    break;

                case 11: // Exit
                    running = false;
                    System.out.println("Shutting down the program... Thank you!");
                    break;

                default:
                    System.out.println("Please choose a valid option (1-11).");
            }
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n===== HOSPITAL MANAGEMENT SYSTEM =====");
        System.out.println("1. Add Doctor");
        System.out.println("2. Delete Doctor (by ID)");
        System.out.println("3. Show All Doctors");
        System.out.println("4. Add Patient");
        System.out.println("5. Release Patient (by ID)");
        System.out.println("6. Update Patient Details");
        System.out.println("7. Show All Patients");
        System.out.println("8. Show Bed Status");
        System.out.println("9. Show Blood Bank");
        System.out.println("10. Billing Manage");
        System.out.println("11. Exit");
        System.out.println("=======================================");
    }

    private static int readInt(Scanner sc) {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}