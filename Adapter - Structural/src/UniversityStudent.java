
public class UniversityStudent implements Student{

    private String fullName;
    private String age;
    private int contactNumber;

    public UniversityStudent(String fullName, String age, int contactNumber) {
        this.fullName = fullName;
        this.age = age;
        this.contactNumber = contactNumber;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getAge() {
        return age;
    }

    @Override
    public int getContactNumber() {
        return contactNumber;
    }
}
