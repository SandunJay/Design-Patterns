package Creational.Singleton;

public class Singleton {

    // Static member to ensure that memory is allocated only once for the instance
    private static Singleton instance;

    // A private constructor to prevent instantiation from outside the class. This ensures that the class cannot be instantiated from outside, thus enforcing the singleton property.
    private Singleton() {
        System.out.println("Singleton instance created.");
    }

    // Static method to provide a global point of access to the instance. This method checks if the instance is null, and if so, creates a new instance. If an instance already exists, it returns that instance.
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public void showMessage() {
        System.out.println("Hello from Singleton!");
    }
}