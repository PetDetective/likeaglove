# 'Like a glove' Parking

You always dreamed about parking like Ace Ventura in Pet Detective movie ?? [see reference](https://www.youtube.com/watch?v=3nOxdKcqC_I)

Stop kidding: Like a glove parking is a Java API to control a toll parking.

## Getting Started

We have defined a core-api that will let your applicative code implementation agnostic.
It provides a basic implementation (in-memory) and a sample application (terminal based).

### Prerequisites

Java 11+ & Maven

### Installing

Build the code with

```
mvn clean install
```

Run application with

```
java -jar core-sampleApp\target\core-sampleApp-0.0.1-SNAPSHOT.jar
```

## Integration in other projects

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

