# Singleton Design Pattern

## Table of Contents

- [Overview](#overview)
- [Implementations](#implementations)
    - [Basic Singleton](#basic-singleton)
    - [Double-Checked Locking Singleton](#double-checked-locking-singleton)
    - [Eager Initialization Singleton](#eager-initialization-singleton)
    - [Static Inner Class Singleton](#static-inner-class-singleton)
    - [Enum Singleton](#enum-singleton)
- [Usage Examples](#usage-examples)
- [Advantages](#advantages)
- [Disadvantages](#disadvantages)
- [When to Use](#when-to-use)
- [When Not to Use](#when-not-to-use)
- [Common Pitfalls](#common-pitfalls)
- [Real-World Examples](#real-world-examples)


## Overview

The Singleton design pattern ensures that a class has only one instance and provides a global point of access to that instance. This pattern is one of the simplest design patterns and belongs to the creational pattern category.

## Implementations

### Basic Singleton

```java
package Creational.Singleton;

public class Singleton {
    // Static member to ensure that memory is allocated only once for the instance
    private static Singleton instance;

    // A private constructor to prevent instantiation from outside the class
    private Singleton() {
        System.out.println("Singleton instance created.");
    }

    // Static method to provide a global point of access to the instance
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
```

**How it works:**

- The constructor is private, preventing external instantiation
- The `getInstance()` method creates an instance if none exists, otherwise returns the existing one
- This implementation is lazy-initialized (created only when needed)

**Thread Safety:**

- This implementation is NOT thread-safe
- Multiple threads calling `getInstance()` simultaneously could create multiple instances


### Double-Checked Locking Singleton

```java
package Creational.Singleton;

public class SingletonDoubleChecked {
    // Static member with volatile keyword to ensure visibility across threads
    private static volatile SingletonDoubleChecked instance;

    // Private constructor
    private SingletonDoubleChecked() {
        System.out.println("Singleton instance created.");
    }

    // Thread-safe implementation with double-checked locking
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
```

**How it works:**

- Uses double-checked locking pattern for thread safety with better performance
- The `volatile` keyword ensures that changes to the instance variable are visible to all threads
- First check avoids unnecessary synchronization once instance is created
- The synchronized block is only entered if the instance is null

**Thread Safety:**

- Thread-safe due to synchronized block and volatile keyword
- The `volatile` keyword is crucial for preventing partially constructed objects due to instruction reordering


### Eager Initialization Singleton

```java
package Creational.Singleton;

public class SingletonEager {
    // Instance is created at the time of class loading
    private static final SingletonEager instance = new SingletonEager();

    // Private constructor
    private SingletonEager() {
        System.out.println("Singleton instance created.");
    }

    // Static method to get the instance
    public static SingletonEager getInstance() {
        return instance;
    }

    public void showMessage() {
        System.out.println("Hello from Singleton!");
    }
}
```

**How it works:**

- Instance is created when the class is loaded, not when it's first used
- Guarantees thread safety without synchronization

**Thread Safety:**

- Inherently thread-safe because the instance is created during class initialization by the JVM
- No additional synchronization is needed


### Static Inner Class Singleton

```java
package Creational.Singleton;

public class SingletonStaticInnerClass {
    // Private constructor
    private SingletonStaticInnerClass() {
        System.out.println("Singleton instance created.");
    }

    // Static inner class - not loaded until needed
    private static class SingletonHolder {
        private static final SingletonStaticInnerClass INSTANCE = new SingletonStaticInnerClass();
    }

    // Static method to get the instance
    public static SingletonStaticInnerClass getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void showMessage() {
        System.out.println("Hello from Singleton!");
    }
}
```

**How it works:**

- Uses a static inner class to hold the singleton instance
- Inner class isn't loaded until the `getInstance()` method is called
- Combines benefits of lazy initialization and thread safety

**Thread Safety:**

- Thread-safe because class initialization is guaranteed to be sequential by the JVM
- No explicit synchronization needed


### Enum Singleton

```java
package Creational.Singleton;

public enum SingletonEnum {
    INSTANCE;
    
    // You can add fields and methods
    private String data;
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    public void showMessage() {
        System.out.println("Hello from Singleton Enum!");
    }
}
```

**How it works:**

- Uses Java's enum feature which guarantees only one instance exists
- Simplest implementation with built-in serialization and thread-safety

**Thread Safety:**

- Inherently thread-safe as Java guarantees enum instance is unique
- Immune to reflection attacks and serialization issues


## Usage Examples

### Basic Usage

```java
public class SingletonDemo {
    public static void main(String[] args) {
        // Get the singleton instance
        Singleton singleton = Singleton.getInstance();
        
        // Use the singleton
        singleton.showMessage();
        
        // Will return the same instance
        Singleton anotherReference = Singleton.getInstance();
        
        // Verify both references point to the same object
        System.out.println(singleton == anotherReference); // Outputs: true
    }
}
```


### Enum Singleton Usage

```java
public class EnumSingletonDemo {
    public static void main(String[] args) {
        // Access the singleton instance
        SingletonEnum singleton = SingletonEnum.INSTANCE;
        
        // Set some data
        singleton.setData("Some data");
        
        // Use the singleton
        singleton.showMessage();
        System.out.println("Data: " + singleton.getData());
    }
}
```


## Advantages

1. **Controlled Access**: Provides a single point of access to a shared resource
2. **Reduced Memory Footprint**: Only one instance is created, saving memory
3. **Avoids Global Variables**: Provides a way to access an object globally without using global variables
4. **Thread Coordination**: Can be used to coordinate actions across a system
5. **Lazy Initialization**: Can implement lazy loading to improve startup performance (except eager initialization)

## Disadvantages

1. **Global State**: Acts like a global variable, which can make testing and debugging harder
2. **Thread Safety Issues**: Requires careful implementation to be thread-safe
3. **Difficult to Subclass**: Private constructor makes inheritance challenging
4. **Tight Coupling**: Systems using singletons become tightly coupled to them
5. **Violates Single Responsibility Principle**: The class manages its own creation and lifecycle in addition to its primary responsibility
6. **Difficult to Unit Test**: Creates dependencies that are hard to mock or replace in tests

## When to Use

- **Resource Management**: When you need to control access to shared resources (file systems, database connections)
- **Configuration Management**: For application configuration settings
- **Logging**: For application-wide logging services
- **Caching**: When implementing caching mechanisms
- **Thread Pools**: For managing a pool of worker threads
- **Device Drivers**: When interfacing with hardware devices that can only have one connection


## When Not to Use

- **When State Varies by Client**: If different clients need different object states
- **When Testing Is a Priority**: Singletons make unit testing difficult
- **When Concurrency Is Complex**: If your application has complex threading requirements
- **When You Need Multiple Instances in the Future**: Your design might evolve to need multiple instances
- **When Clean Dependency Injection Is Preferred**: Dependency injection often provides a cleaner alternative


## Common Pitfalls

1. **Forgetting Thread Safety**: Not addressing concurrency can create race conditions
2. **Serialization Issues**: Regular singletons may lose their singleton property when deserialized
3. **Reflection Attacks**: Reflection can be used to create multiple instances by accessing private constructors
4. **Multiple Class Loaders**: Different class loaders can load different instances of a singleton
5. **Memory Leaks**: Singletons persisting for the application lifetime can cause memory issues if they hold references to other objects

## Real-World Examples

- **java.lang.Runtime**: Each Java application has a single Runtime instance that allows the application to interface with the environment
- **java.awt.Desktop**: Provides access to the desktop, with methods to open files, URLs, etc.
- **Spring Beans**: Many beans in Spring applications are configured as singletons by default
- **Logger Instances**: In logging frameworks like Log4j
- **Database Connection Pools**: Often implemented as singletons to manage database connections
- **File Managers**: For controlling access to file system resources

---

## Best Practices

1. **Use Enum for Simple Singletons**: For most cases, the enum implementation is preferred
2. **Use Static Inner Class Pattern**: For lazy initialization with thread safety
3. **Double-Check Locking With Volatile**: If fine-grained control over initialization is needed
4. **Keep Singleton Classes Immutable**: Avoid state changes that could cause issues in a multi-threaded environment
5. **Consider Dependency Injection**: As an alternative to direct singleton usage
