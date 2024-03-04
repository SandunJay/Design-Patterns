
public class Person {
    private String NIC;
    private String fName;
    private String lName;
    private String dOB;
    private int phone;


    public Person(String NIC, String firstName, String lastName, String dateOfBirth, int phoneNumber) {
        this.NIC = NIC;
        this.fName = firstName;
        this.lName = lastName;
        this.dOB = dateOfBirth;
        this.phone = phoneNumber;
    }

    public String getNIC() {
        return NIC;
    }

    public String getFirstName() {
        return fName;
    }

    public String getLastName() {
        return lName;
    }

    public String getDateOfBirth() {
        return dOB;
    }

    public int getPhoneNumber() {
        return phone;
    }

}

