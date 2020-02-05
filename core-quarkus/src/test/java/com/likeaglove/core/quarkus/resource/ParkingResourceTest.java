package com.likeaglove.core.quarkus.resource;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

/**
 * Ultra weak testing here. Must be improved in the future
 * 
 * @author AceVentura
 *
 */
@QuarkusTest
public class ParkingResourceTest {

	@Test
	public void testParkingEndpoint() {
		// technical debt: Body content must be checked here
		given().when().get("/parking").then().statusCode(200);
	}

	@Test
	public void testParkEndpoint() {
		// technical debt: Body content must be checked here
		given().when().post("/parking/parkCar/ZZ-999-WW/Standard").then().statusCode(200);
		given().when().post("/parking/parkCar/ZZ-998-WW/Electric_20").then().statusCode(200);
		given().when().post("/parking/parkCar/ZZ-997-WW/Electric_50").then().statusCode(200);
		// Parking twice the same car
		given().when().post("/parking/parkCar/ZZ-999-WW/Standard").then().statusCode(409);
		// Going to exhaust resources
		given().when().post("/parking/parkCar/ZZ-996-WW/Standard").then().statusCode(200);
		given().when().post("/parking/parkCar/ZZ-995-WW/Standard").then().statusCode(200);
		given().when().post("/parking/parkCar/ZZ-994-WW/Standard").then().statusCode(429);
		// make some room
		given().when().post("/parking/unparkCar/ZZ-996-WW").then().statusCode(200);
		// see if we manage to park again
		given().when().post("/parking/parkCar/ZZ-996-WW/Standard").then().statusCode(200);
	}

	@Test
	public void testUnParkEndpoint() {
		// Trying to remove a car that has never been parked
		given().when().post("/parking/unparkCar/ZZ-000-WW").then().statusCode(410);
		given().when().post("/parking/parkCar/ZZ-000-WW/Electric_50").then().statusCode(200);
		given().when().post("/parking/unparkCar/ZZ-000-WW").then().statusCode(200);
	}

}