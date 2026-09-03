
class Doctor extends Person {
    private final String specialist;

    public Doctor(String id, String name, int age, String gender, String specialist) {
        super(id, name, age, gender);
        this.specialist = specialist;
    }

    public String getSpecialist() {
        return specialist;
    }

    @Override
    public void showDetails() {
        System.out.println("---------------------------------");
        System.out.println("Doctor ID   : " + id);
        System.out.println("Name        : " + name);
        System.out.println("Age         : " + age);
        System.out.println("Gender      : " + gender);
        System.out.println("Specialist  : " + specialist);
        System.out.println("---------------------------------");
    }
}
