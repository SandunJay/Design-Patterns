
# Builder Design Pattern

## Table of Contents

- [Overview](#overview)
- [Problem It Solves](#problem-it-solves)
- [Solution](#solution)
- [Structure and Implementation](#structure-and-implementation)
- [Variations](#variations)
- [When to Use](#when-to-use)
- [When Not to Use](#when-not-to-use)
- [Advantages](#advantages)
- [Disadvantages](#disadvantages)
- [Real-World Examples](#real-world-examples)
- [Implementation Examples](#implementation-examples)
- [Best Practices](#best-practices)


## Overview

The Builder pattern is a creational design pattern that provides a flexible solution to various object creation problems in object-oriented programming. It separates the construction of a complex object from its representation, allowing the same construction process to create different representations[^5].

The pattern is particularly useful when dealing with objects that have numerous parameters, especially when many of them are optional or when you need to ensure object immutability[^2].

## Problem It Solves

Builder pattern addresses several common issues in object creation:

1. **Telescoping constructors**: When a class has many parameters, it often leads to constructors with multiple overloads to handle different combinations of parameters[^1].
2. **Object consistency**: When using multiple setter methods to configure an object, the object may be in an inconsistent state during construction until all required parameters have been set[^3].
3. **Complex object creation**: Creating objects with many configuration options or steps becomes difficult to manage within a single class[^5].
4. **Subclass explosion**: As noted in the party planning example, having different subclasses for each combination of optional attributes can lead to an explosion of subclasses (2^n for n optional attributes)[^4].

## Solution

The Builder pattern suggests:

1. Extracting the object construction code from the main class and moving it to separate objects called builders[^1].
2. Organizing object construction into a series of steps (e.g., `buildWalls`, `buildDoor`)[^1].
3. Allowing step-by-step construction, where only necessary steps are called[^1].
4. Using a `build()` method to return the final constructed object to the client[^3].

## Structure and Implementation

A typical Builder pattern implementation includes:

1. **Product**: The complex object being built.
2. **Builder**: An interface that defines the steps to construct the product.
3. **Concrete Builder**: Classes that implement the Builder interface to construct specific representations of the product.
4. **Director**: (Optional) A class that defines the order in which to execute the building steps.

Basic structure:

```java
// Product class
public class Product {
    // Fields, getters, etc.
}

// Builder interface
public interface Builder {
    Builder buildPartA();
    Builder buildPartB();
    Builder buildPartC();
    Product build();
}

// Concrete Builder implementation
public class ConcreteBuilder implements Builder {
    private Product product = new Product();
    
    @Override
    public Builder buildPartA() {
        // Set part A of the product
        return this;
    }
    
    @Override
    public Builder buildPartB() {
        // Set part B of the product
        return this;
    }
    
    @Override
    public Builder buildPartC() {
        // Set part C of the product
        return this;
    }
    
    @Override
    public Product build() {
        return product;
    }
}

// Client code (with or without Director)
public class Client {
    public static void main(String[] args) {
        Product product = new ConcreteBuilder()
            .buildPartA()
            .buildPartC() // Note that we can skip steps
            .build();
    }
}
```


## Variations

### 1. Inner Static Builder

The most common implementation in Java, where the Builder is defined as a static inner class of the Product.

```java
public class Computer {
    // Required parameters
    private final String cpu;
    private final String ram;
    
    // Optional parameters
    private final String storage;
    private final String gpu;
    
    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
    }
    
    public static class Builder {
        // Required parameters
        private final String cpu;
        private final String ram;
        
        // Optional parameters
        private String storage;
        private String gpu;
        
        public Builder(String cpu, String ram) {
            this.cpu = cpu;
            this.ram = ram;
        }
        
        public Builder storage(String storage) {
            this.storage = storage;
            return this;
        }
        
        public Builder gpu(String gpu) {
            this.gpu = gpu;
            return this;
        }
        
        public Computer build() {
            return new Computer(this);
        }
    }
}

// Usage
Computer computer = new Computer.Builder("Intel i7", "16GB")
    .storage("512GB SSD")
    .gpu("NVIDIA RTX 3080")
    .build();
```


### 2. Director-Driven Builder

When you want to encapsulate various ways to construct a product using the same builder.

```java
// Builder interface
public interface HouseBuilder {
    HouseBuilder buildWalls();
    HouseBuilder buildRoof();
    HouseBuilder buildWindows();
    HouseBuilder buildDoors();
    House build();
}

// Concrete Builder
public class WoodenHouseBuilder implements HouseBuilder {
    private House house = new House();
    
    // Implementation details
}

// Director class
public class HouseDirector {
    public House constructBasicHouse(HouseBuilder builder) {
        return builder.buildWalls().buildRoof().build();
    }
    
    public House constructFullHouse(HouseBuilder builder) {
        return builder.buildWalls().buildRoof().buildWindows().buildDoors().build();
    }
}

// Usage
HouseBuilder builder = new WoodenHouseBuilder();
HouseDirector director = new HouseDirector();
House basicHouse = director.constructBasicHouse(builder);
```


### 3. Fluent Interface Builder

A variation focused on creating a readable, fluent API.

```java
public class EmailBuilder {
    private Email email = new Email();
    
    public EmailBuilder from(String from) {
        email.setFrom(from);
        return this;
    }
    
    public EmailBuilder to(String to) {
        email.setTo(to);
        return this;
    }
    
    public EmailBuilder subject(String subject) {
        email.setSubject(subject);
        return this;
    }
    
    public EmailBuilder body(String body) {
        email.setBody(body);
        return this;
    }
    
    public Email build() {
        return email;
    }
}

// Usage
Email email = new EmailBuilder()
    .from("sender@example.com")
    .to("recipient@example.com")
    .subject("Meeting")
    .body("Let's meet tomorrow")
    .build();
```


## When to Use

Use the Builder pattern when:

1. You need to get rid of "telescoping constructors" with too many parameters[^1].
2. You want your code to be able to create different representations of a complex object (e.g., stone and wooden houses)[^1].
3. You need to construct complex objects step by step[^1][^6].
4. You want to ensure object immutability (objects cannot be modified after creation)[^2].
5. You're dealing with objects that have many optional components or configurations[^1][^4].
6. You need to build Composite trees or other complex objects[^1].
7. You want to defer the construction until all the necessary information is available[^6].

## When Not to Use

Avoid the Builder pattern when:

1. The objects you're creating are simple and don't have many parameters.
2. You don't need multiple representations of the same object.
3. Performance is a critical concern and the overhead of additional classes is problematic.
4. Your team is unfamiliar with the pattern and the added complexity might lead to maintainability issues.
5. The construction process doesn't have distinct steps or doesn't benefit from step-by-step construction.

## Advantages

1. **Step-by-step construction**: Objects can be created gradually and methodically[^6].
2. **Deferred construction**: Object creation can be postponed until all necessary information is available[^6].
3. **Clean code**: Applies the Single Responsibility Principle by isolating complex construction from business logic[^6].
4. **Maintainability**: More maintainable if the number of fields required exceeds 4 or 5[^7].
5. **Reduced errors**: Users know what they're passing because of explicit method calls[^7].
6. **Object consistency**: Only fully constructed objects are available to clients[^7].
7. **Flexibility**: You can create different representations using the same construction process[^1].
8. **Readability**: Makes code more readable and less error-prone, especially with many parameters.

## Disadvantages

1. **Increased complexity**: Requires more code and can be harder to understand[^6].
2. **Code duplication**: The Builder needs to copy all fields from the original class[^7].
3. **Additional classes**: Requires creating extra classes, increasing the codebase size[^6].
4. **Verbosity**: Can make the code more verbose compared to direct construction[^4].
5. **Learning curve**: Might be harder for team members unfamiliar with the pattern.

## Real-World Examples

1. **Party Planning Application**: Creating different types of parties with varying attributes like theme, dress code, food options, etc.[^4]
2. **Document Builder**: Creating different document formats (PDF, DOC, HTML) using the same construction steps.
3. **Meal Ordering System**: Building meals with various combinations of main dishes, sides, drinks, and desserts.
4. **UI Form Builder**: Creating complex forms with different fields, validations, and layouts.
5. **Database Query Builder**: Building SQL queries with different clauses and conditions.
6. **Network Request Builder**: Configuring HTTP requests with headers, parameters, and body content.
7. **Configuration Systems**: Building complex configuration objects for applications.

## Implementation Examples

### Basic Computer Builder Example

```java
public class Computer {
    // Required parameters
    private final String cpu;
    private final String ram;
    
    // Optional parameters
    private final String storage;
    private final String gpu;
    private final String opticalDrive;
    
    private Computer(ComputerBuilder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
        this.opticalDrive = builder.opticalDrive;
    }
    
    // Getters
    public String getCpu() { return cpu; }
    public String getRam() { return ram; }
    public String getStorage() { return storage; }
    public String getGpu() { return gpu; }
    public String getOpticalDrive() { return opticalDrive; }
    
    @Override
    public String toString() {
        return "Computer{" +
                "cpu='" + cpu + '\'' +
                ", ram='" + ram + '\'' +
                ", storage='" + storage + '\'' +
                ", gpu='" + gpu + '\'' +
                ", opticalDrive='" + opticalDrive + '\'' +
                '}';
    }
    
    public static class ComputerBuilder {
        // Required parameters
        private final String cpu;
        private final String ram;
        
        // Optional parameters - initialized with default values
        private String storage = "";
        private String gpu = "";
        private String opticalDrive = "";
        
        public ComputerBuilder(String cpu, String ram) {
            this.cpu = cpu;
            this.ram = ram;
        }
        
        public ComputerBuilder storage(String storage) {
            this.storage = storage;
            return this;
        }
        
        public ComputerBuilder gpu(String gpu) {
            this.gpu = gpu;
            return this;
        }
        
        public ComputerBuilder opticalDrive(String opticalDrive) {
            this.opticalDrive = opticalDrive;
            return this;
        }
        
        public Computer build() {
            return new Computer(this);
        }
    }
}

// Usage example
public class Main {
    public static void main(String[] args) {
        Computer computer = new Computer.ComputerBuilder("Intel i9", "32GB")
                .storage("1TB SSD")
                .gpu("NVIDIA RTX 4090")
                .build();
        
        System.out.println(computer);
    }
}
```


### Person Builder with Separate Builder Class

```java
public class Person {
    // Required parameters
    private final String firstName;
    private final String lastName;
    
    // Optional parameters
    private final int age;
    private final String phone;
    private final String address;
    
    Person(PersonBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
    }
    
    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
}

public class PersonBuilder {
    // Required parameters
    final String firstName;
    final String lastName;
    
    // Optional parameters - initialized with default values
    int age = 0;
    String phone = "";
    String address = "";
    
    public PersonBuilder(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public PersonBuilder age(int age) {
        this.age = age;
        return this;
    }
    
    public PersonBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }
    
    public PersonBuilder address(String address) {
        this.address = address;
        return this;
    }
    
    public Person build() {
        return new Person(this);
    }
}

// Usage example
public class Main {
    public static void main(String[] args) {
        Person person = new PersonBuilder("John", "Doe")
                .age(30)
                .phone("123-456-7890")
                .address("123 Main St")
                .build();
        
        System.out.println(person.getFirstName() + " " + person.getLastName() +
                ", age: " + person.getAge() +
                ", phone: " + person.getPhone() +
                ", address: " + person.getAddress());
    }
}
```


## Best Practices

1. **Make the product class immutable**: Use the builder to set all values during construction and make the product class fields final.
2. **Require essential parameters in the builder constructor**: This ensures that required fields are always provided.
3. **Return the builder instance from setter methods**: This allows for method chaining (fluent interface).
4. **Perform validation in the build() method**: Ensure the object is in a valid state before returning it.
5. **Make the product constructor private or package-private**: Force clients to use the builder.
6. **Consider using a director class**: When you have standard ways to create a product, encapsulate them in a director.
7. **Keep the builder focused on construction**: Don't add business logic to the builder.
8. **Use clear, descriptive method names**: Make the builder API self-documenting.
9. **Consider using builders for objects with more than 4-5 parameters**: This is a good rule of thumb for when to use the pattern.
10. **Document the builder pattern usage**: Ensure that other developers understand how to use your implementation.
