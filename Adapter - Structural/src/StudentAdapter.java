public class StudentAdapter implements Student{
    private Person p;

    public StudentAdapter(Person p) {
        this.p = p;
    }


    @Override
    public String getFullName() {
        return p.getFirstName() + " " + p.getLastName();
    }

    @Override
    public String getAge() {
        String[] dobParts = p.getDateOfBirth().split("-");
        int birthYear = Integer.parseInt(dobParts[0]);
        int currentYear = java.time.LocalDate.now().getYear();
        return String.valueOf(currentYear - birthYear);
    }

    @Override
    public int getContactNumber() {
        return p.getPhoneNumber();
    }
}
