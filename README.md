# 'Like a glove' Parking

You always dreamed about parking like Ace Ventura in Pet Detective movie ([see reference](https://www.youtube.com/watch?v=3nOxdKcqC_I)) ?

Stop kidding: Like a glove parking is a Java API to control a toll parking.

The project is composed of the following modules:
- core-api: generic contract definition of the Toll Parking API; with SPI mechanism to inject a custom implementation at runtime.
- core-impl:  a "no nano bullshit" technology implementation. Basic, in memory implementation of the core-api. 
- core-sampleApp: a sample application showcasing the SPI mechanism of the core-api by using the core-impl at runtime only. This application interacts directly in a terminal.
- core-quarkus: a sample application based on Quarkus (for compilation and various bundled APIs); providing another implementation of the core-api with Hibernate and Panache and exposing some http rest services.

## Getting Started

### Prerequisites

Java 11+

### Installing the core-sampleApp

core-api; core-impl and core-sampleApp can be compiled in one shot via the following command:

```
mvnw clean install
```

Run the core-sampleApp application with

```
java -jar core-sampleApp\target\core-sampleApp-0.0.1-SNAPSHOT.jar
```

You will get in your terminal and interactive wizard:

```
What do you want to do?
  1: Park a car
  2: Unpark a car
  3: Display parking map
  4: Close the application
Enter your choice: 3
+-----------------+------------------+--------+--------------+
|    Slot Name    |       Type       | Status | Registration |
+-----------------+------------------+--------+--------------+
| Electric_20 1   | Electric Car 20k |  Free  | -            |
| Electric_20 2   | Electric Car 20k |  Free  | -            |
| Electric_20 3   | Electric Car 20k |  Free  | -            |
| Electric_20 4   | Electric Car 20k |  Free  | -            |
| Electric_20 5   | Electric Car 20k |  Free  | -            |
| Electric_20 6   | Electric Car 20k |  Free  | -            |
| Electric_20 7   | Electric Car 20k |  Free  | -            |
| Electric_20 8   | Electric Car 20k |  Free  | -            |
| Electric_50 1   | Electric Car 50k |  Free  | -            |
| Electric_50 2   | Electric Car 50k |  Free  | -            |
| Electric_50 3   | Electric Car 50k |  Free  | -            |
| Electric_50 4   | Electric Car 50k |  Free  | -            |
| Electric_50 5   | Electric Car 50k |  Free  | -            |
| Electric_50 6   | Electric Car 50k |  Free  | -            |
| Electric_50 7   | Electric Car 50k |  Free  | -            |
| Electric_50 8   | Electric Car 50k |  Free  | -            |
| Standard 1      | Gasoline powered |  Free  | -            |
| Standard 2      | Gasoline powered |  Free  | -            |
| Standard 3      | Gasoline powered |  Free  | -            |
| Standard 4      | Gasoline powered |  Free  | -            |
| Standard 5      | Gasoline powered |  Free  | -            |
+-----------------+------------------+--------+--------------+
```

### Installing the core-quarkus sample

In the root folder of the project run the compilation of the project:

```
mvnw clean install
```

then go inside the core-quarkus folder

```
cd core-quarkus
```

package the application

```
..\mvnw package
```

start the application

```
java -jar target/core-quarkus-0.0.1-SNAPSHOT-runner.jar
```

You can find more details on how to use the core-quarkus application or alternative deployment modes in core-quarkus/README.md

## Integration in other projects of core-api and core-impl

### Maven coordinates

Add the following dependencies to your project:

```xml
<dependency>
  <groupId>com.likeaglove</groupId>
  <artifactId>core-api</artifactId>
  <version>${likeaglove.version}</version>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>com.likeaglove</groupId>
  <artifactId>core-impl</artifactId>
  <version>${likeaglove.version}</version>
  <scope>runtime</scope>
</dependency>
```

### How to configure a Parking?

There is a dedicated builder for that:

```java
ParkingConfiguration.newBuilder().withSimplePricePolicy(10, 3)
  .addSlots()
    .addRange(SlotType.Standard, 5)
    .addRange(SlotType.Electric_20, 8)
    .addRange(SlotType.Electric_50, 8)
  .rangeSlotsDone()
.build();
```

### How to use an alternative implementation?

Based on SPI; you can provide your own implementation via 'META-INF/services/com.likeaglove.core.spi.ParkingManagerProvider' and defining the fully qualified name.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Quarkus](https://quarkus.io) - Supersonic Subatomic Java
* [Maven Wrapper](https://github.com/takari/maven-wrapper) - Maven wrapper

