package Creational.Singleton;

// Test class to demonstrate the Singleton pattern
public class SingletonTest {
    public static void main(String[] args) {
        // Get the only instance of Singleton
        Singleton singleton = Singleton.getInstance();

        // Call a method on the singleton instance
        singleton.showMessage();
    }
}
