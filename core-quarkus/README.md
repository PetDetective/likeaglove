# core-quarkus project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run the application in dev mode that enables live coding using:
```
../mvnw quarkus:dev
```

## Packaging and running the application

The application is packageable using `../mvnw package`.
It produces the executable `core-quarkus-0.0.1-SNAPSHOT-runner.jar` file in `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/core-quarkus-0.0.1-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `../mvnw package -Pnative`.

Or you can use Docker to build the native executable using: `../mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your binary: `./target/core-quarkus-0.0.1-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image-guide .

## Configuration of the application

Edit the file `src\main\resources\application.properties`

You can focus on the following configuration properties to adjust them to the parking:

```configuration
# number of standard slots
slot.standard.size=3
# number of slots with 20kW power supply
slot.electric20.size=5
# number of slots with 50kW power supply
slot.electric50.size=9
# fixed amount of the simple policy
policy.fixedAmount=8
# rate by hour of the simple policy
policy.rateByHour=12
```
## Rest API

You can shoot queries to the following endpoints:

### Display the parking content
`curl 'http://localhost:8080/parking'`

### Park a car
`curl -X POST http://localhost:8080/parking/parkCar/ZZ-997-WW/Eletric_50`

### Leave the parking
`curl -X POST http://localhost:8080/parking/unparkCar/ZZ-997-WW`

