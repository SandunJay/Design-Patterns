// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Creating a Person
        Person person = new Person("123456789V", "John", "Doe", "1990-01-01", 123456789);

        Student student = new StudentAdapter(person);

        // Testing the functionality
        System.out.println("Full Name: " + student.getFullName());
        System.out.println("Age: " + student.getAge());
        System.out.println("Contact Number: " + student.getContactNumber());

    }
}