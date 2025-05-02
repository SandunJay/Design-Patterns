# Factory Design Pattern

## Table of Contents

- [Overview](#overview)
- [Types of Factory Patterns](#types-of-factory-patterns)
- [Implementations](#implementations)
    - [Simple Factory](#simple-factory)
    - [Factory Method](#factory-method)
    - [Abstract Factory](#abstract-factory)
- [Usage Examples](#usage-examples)
- [Advantages](#advantages)
- [Disadvantages](#disadvantages)
- [When to Use](#when-to-use)
- [When Not to Use](#when-not-to-use)
- [Common Pitfalls](#common-pitfalls)
- [Real-World Examples](#real-world-examples)
- [Best Practices](#best-practices)


## Overview

The Factory Design Pattern is a creational pattern that provides an interface for creating objects without specifying their concrete classes. It centralizes object creation logic and promotes loose coupling by allowing clients to work with created objects through common interfaces rather than concrete implementations.

## Types of Factory Patterns

### 1. Simple Factory

Not a formal design pattern but a common programming idiom that provides a static method for creating objects.

### 2. Factory Method

Defines an interface for creating objects but lets subclasses decide which classes to instantiate.

### 3. Abstract Factory

Provides an interface for creating families of related or dependent objects without specifying their concrete classes.

## Implementations

### Simple Factory

```java
package Creational.Factory.SimpleFactory;

// Step 1: Create a common interface
public interface Product {
    void operation();
}

// Step 2: Create concrete products
public class ConcreteProductA implements Product {
    @Override
    public void operation() {
        System.out.println("ConcreteProductA operation");
    }
}

public class ConcreteProductB implements Product {
    @Override
    public void operation() {
        System.out.println("ConcreteProductB operation");
    }
}

// Step 3: Create the factory
public class SimpleFactory {
    // Create a product based on a type parameter
    public static Product createProduct(String type) {
        if (type.equalsIgnoreCase("A")) {
            return new ConcreteProductA();
        } else if (type.equalsIgnoreCase("B")) {
            return new ConcreteProductB();
        }
        throw new IllegalArgumentException("Unknown product type: " + type);
    }
}

// Step 4: Client code
public class Client {
    public static void main(String[] args) {
        // Use the factory to create a Product
        Product productA = SimpleFactory.createProduct("A");
        productA.operation();  // Output: ConcreteProductA operation
        
        Product productB = SimpleFactory.createProduct("B");
        productB.operation();  // Output: ConcreteProductB operation
    }
}
```

**How it works:**

- The `SimpleFactory` class contains a static method that acts as a factory for creating products
- The factory creates objects based on input parameters
- Client code uses the factory to create objects without knowing the concrete classes


### Factory Method

```java
package Creational.Factory.FactoryMethod;

// Step 1: Create a common product interface
public interface Product {
    void operation();
}

// Step 2: Create concrete products
public class ConcreteProductA implements Product {
    @Override
    public void operation() {
        System.out.println("ConcreteProductA operation");
    }
}

public class ConcreteProductB implements Product {
    @Override
    public void operation() {
        System.out.println("ConcreteProductB operation");
    }
}

// Step 3: Create a Creator interface with factory method
public abstract class Creator {
    // The factory method
    public abstract Product createProduct();
    
    // Template method that uses the factory method
    public void someOperation() {
        // Call the factory method to create a Product object
        Product product = createProduct();
        // Use the product
        product.operation();
    }
}

// Step 4: Create concrete Creator classes
public class ConcreteCreatorA extends Creator {
    @Override
    public Product createProduct() {
        return new ConcreteProductA();
    }
}

public class ConcreteCreatorB extends Creator {
    @Override
    public Product createProduct() {
        return new ConcreteProductB();
    }
}

// Step 5: Client code
public class Client {
    public static void main(String[] args) {
        // Create a concrete creator
        Creator creatorA = new ConcreteCreatorA();
        // Use the template method which will use the factory method
        creatorA.someOperation();  // Output: ConcreteProductA operation
        
        Creator creatorB = new ConcreteCreatorB();
        creatorB.someOperation();  // Output: ConcreteProductB operation
    }
}
```

**How it works:**

- The `Creator` abstract class defines the factory method (`createProduct`) and a template method (`someOperation`)
- Concrete creator classes override the factory method to create specific products
- The client works with creators, which internally create and use the products


### Abstract Factory

```java
package Creational.Factory.AbstractFactory;

// Step 1: Define product interfaces for each product family
// Button family
public interface Button {
    void render();
    void onClick();
}

// Checkbox family
public interface Checkbox {
    void render();
    void onSelect();
}

// Step 2: Create concrete products for each family and theme
// Windows theme products
public class WindowsButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering a Windows-style button");
    }
    
    @Override
    public void onClick() {
        System.out.println("Windows button clicked");
    }
}

public class WindowsCheckbox implements Checkbox {
    @Override
    public void render() {
        System.out.println("Rendering a Windows-style checkbox");
    }
    
    @Override
    public void onSelect() {
        System.out.println("Windows checkbox selected");
    }
}

// macOS theme products
public class MacOSButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering a macOS-style button");
    }
    
    @Override
    public void onClick() {
        System.out.println("macOS button clicked");
    }
}

public class MacOSCheckbox implements Checkbox {
    @Override
    public void render() {
        System.out.println("Rendering a macOS-style checkbox");
    }
    
    @Override
    public void onSelect() {
        System.out.println("macOS checkbox selected");
    }
}

// Step 3: Create the abstract factory interface
public interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// Step 4: Create concrete factories for each theme
public class WindowsFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }
    
    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}

public class MacOSFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacOSButton();
    }
    
    @Override
    public Checkbox createCheckbox() {
        return new MacOSCheckbox();
    }
}

// Step 5: Create an application class that uses the factory
public class Application {
    private Button button;
    private Checkbox checkbox;
    
    public Application(GUIFactory factory) {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }
    
    public void render() {
        button.render();
        checkbox.render();
    }
    
    public void handleUserInteraction() {
        button.onClick();
        checkbox.onSelect();
    }
}

// Step 6: Client code
public class Client {
    public static void main(String[] args) {
        // Determine which factory to use based on configuration or environment
        GUIFactory factory;
        String osName = System.getProperty("os.name").toLowerCase();
        
        if (osName.contains("windows")) {
            factory = new WindowsFactory();
        } else {
            factory = new MacOSFactory();
        }
        
        // Create the application with the selected factory
        Application app = new Application(factory);
        app.render();
        app.handleUserInteraction();
    }
}
```

**How it works:**

- The `GUIFactory` interface declares methods for creating each product type
- Concrete factories implement these methods to create specific product variants (Windows or macOS)
- The `Application` class uses the factory to create a consistent set of UI components
- Client code configures the app with the appropriate factory based on the operating system


## Usage Examples

### Simple Factory Example

```java
// Creating products through the Simple Factory
Product productA = SimpleFactory.createProduct("A");
productA.operation();

// Adding a new product type would require modifying the factory
Product productC = SimpleFactory.createProduct("C");  // If C was implemented in the factory
```


### Factory Method Example

```java
// Working with creators that contain factory methods
Creator creator = determineCreator();  // Some logic to determine which creator to use
creator.someOperation();  // The template method uses the factory method internally

// To add new product types, create new Creator and Product subclasses
```


### Abstract Factory Example

```java
// Creating a UI for the current operating system
GUIFactory factory = getFactoryForCurrentOS();
Application app = new Application(factory);
app.render();  // Renders UI components consistent with the OS theme

// To support a new theme (e.g., Linux), add new product implementations and a new factory
```


## Advantages

1. **Decoupling**: Separates object creation from object usage, reducing coupling between components
2. **Centralized Creation Logic**: Centralizes complex creation logic in a single place
3. **Flexibility**: Makes it easier to swap implementations without changing client code
4. **Consistent Object Creation**: Ensures objects are created in a consistent way
5. **Supports Open/Closed Principle**: Allows adding new product types without modifying existing code (especially in Factory Method)
6. **Encapsulation**: Hides implementation details of products from client code
7. **Family of Objects**: Abstract Factory ensures that created objects work together (consistency across product families)

## Disadvantages

1. **Increased Complexity**: Adds more classes and interfaces to the codebase
2. **Harder to Understand**: The indirection can make code harder to follow for those unfamiliar with the pattern
3. **More Code**: Requires writing more code than direct object instantiation
4. **Factory Proliferation**: Can lead to many factory classes, especially with the Factory Method pattern
5. **Harder to Extend**: Adding new product types to Abstract Factory requires modifying the factory interface and all concrete factories
6. **Increased Compilation Dependencies**: May increase coupling at compile time even while reducing it at runtime

## When to Use

1. **Complex Object Creation**: When object creation involves complex logic beyond a simple constructor call
2. **Dependency Inversion**: When you want client code to depend on abstractions rather than concrete classes
3. **Multiple Product Variants**: When a system needs to work with multiple families of related products
4. **Platform Independence**: When you need to develop code that works across different platforms or environments
5. **Extensibility**: When you anticipate adding new product types in the future
6. **Testing**: When you need to substitute product implementations for testing purposes
7. **Framework Development**: When developing a framework where concrete classes are determined by the application using the framework

## When Not to Use

1. **Simple Object Creation**: When object creation is straightforward and doesn't involve complex logic
2. **Limited Product Types**: When you only have one or two product types that are unlikely to change or expand
3. **Stable Requirements**: When the product hierarchy is stable and not expected to change
4. **Overengineering**: When it adds unnecessary complexity to a simple application
5. **Performance-Critical Code**: When the overhead of multiple objects and indirection could impact performance in critical sections
6. **Small Applications**: In very small applications where maintainability and extensibility are less important

## Common Pitfalls

1. **Overuse**: Applying the pattern when simple constructor calls would suffice
2. **Rigid Hierarchies**: Creating deep inheritance hierarchies that become difficult to maintain
3. **Tight Coupling to Factory**: Making client code depend too much on a specific factory implementation
4. **Ignoring Initialization Parameters**: Not providing mechanism to initialize products with varying parameters
5. **Neglecting Error Handling**: Not properly handling errors in factory methods
6. **Forgetting Dependency Injection**: Using factories when dependency injection might be a better solution
7. **Monolithic Factories**: Creating factories that create too many different types of objects

## Real-World Examples

1. **Java Collections Framework**: `Collections.sort()`, `Collections.synchronizedList()`, etc.
2. **JDBC Factory APIs**: `DriverManager.getConnection()` returns different database connection objects
3. **UI Frameworks**: Swing and JavaFX both use factory patterns for creating UI components
4. **Spring Framework**: BeanFactory and ApplicationContext serve as factories for creating beans
5. **Logging Frameworks**: Log4j and SLF4J use factory patterns to create logger instances
6. **Database Connection Pools**: Factories for creating and managing database connections
7. **Document Object Model (DOM)**: `DocumentBuilderFactory` and `DocumentBuilder` in Java's XML processing

## Best Practices

1. **Meaningful Names**: Choose clear naming conventions for factories and products
2. **Single Responsibility**: Each factory should focus on creating objects of a single hierarchy
3. **Factory Parameters**: Design factory methods to accept parameters that determine the type of object to create
4. **Error Handling**: Implement proper error handling in factory methods
5. **Documentation**: Clearly document the purpose and usage of each factory and product
6. **Dependency Injection**: Consider using dependency injection frameworks alongside factories
7. **Interface-Based Design**: Design for interfaces rather than concrete implementations
8. **Testing**: Create mock factories for testing purposes
9. **Lazy Initialization**: Consider initializing products only when needed to improve performance
10. **Caching**: Consider caching products if they are expensive to create and can be reused
