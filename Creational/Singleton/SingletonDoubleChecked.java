package Creational.Singleton;

public class SingletonDoubleChecked {
    // Static member to ensure that memory is allocated only once for the instance
    private static volatile SingletonDoubleChecked instance;

    // A private constructor to prevent instantiation from outside the class. This ensures that the class cannot be instantiated from outside, thus enforcing the singleton property.
    private SingletonDoubleChecked() {
        System.out.println("Singleton instance created.");
    }

    // Static method to provide a global point of access to the instance. This method checks if the instance is null, and if so, creates a new instance. If an instance already exists, it returns that instance.
    public static SingletonDoubleChecked getInstance() {
        if (instance == null) { // First check (no locking)
            synchronized (SingletonDoubleChecked.class) { // Locking
                if (instance == null) { // Second check (with locking)
                    instance = new SingletonDoubleChecked();
                }
            }
        }
        return instance;
    }

    public void showMessage() {
        System.out.println("Hello from Singleton!");
    }
}
