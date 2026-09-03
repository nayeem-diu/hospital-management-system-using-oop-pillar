
class Patient extends Person {
    private String disease;
    private int bedNumber;
    private int bloodBagsBooked;

    public Patient(String id, String name, int age, String gender, String disease) {
        super(id, name, age, gender);
        this.disease = disease;
        this.bedNumber = -1;
        this.bloodBagsBooked = 0;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    public int getBloodBagsBooked() {
        return bloodBagsBooked;
    }

    public void addBloodBags(int qty) {
        this.bloodBagsBooked += qty;
    }

    @Override
    public void showDetails() {
        System.out.println("---------------------------------");
        System.out.println("Patient ID  : " + id);
        System.out.println("Name        : " + name);
        System.out.println("Age         : " + age);
        System.out.println("Gender      : " + gender);
        System.out.println("Disease     : " + disease);
        System.out.println("Bed Number  : " + (bedNumber == -1 ? "No Bed Assigned" : bedNumber));
        System.out.println("Blood Booked: " + bloodBagsBooked + " bag(s)");
        System.out.println("---------------------------------");
    }
}
