package com.likeaglove.core.quarkus.resource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.Test;

import com.likeaglove.core.model.SlotType;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

/**
 * Some testing of the {@link ParkingResource}
 * 
 * @author AceVentura
 *
 */
@QuarkusTest
public class ParkingResourceTest {

	@Test
	void testParkingEndpoint() {
		// technical debt: Body content must be checked here
		assertEquals(17, given().when().get("/parking").then().statusCode(200).contentType(ContentType.JSON).extract()
				.response().jsonPath().getList("$").size());
	}

	@Test
	void testParkEndpoint() {
		given().when().post("/parking/parkCar/ZZ-999-WW/Standard").then().statusCode(200)
				.body("type", is(SlotType.Standard.toString())).and().body("free", is(false)).and()
				.body("registrationNumber", is("ZZ-999-WW")).and().body("startDate", notNullValue());
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
	void testUnParkEndpoint() {
		// Trying to remove a car that has never been parked
		given().when().post("/parking/unparkCar/ZZ-000-WW").then().statusCode(410);
		given().when().post("/parking/parkCar/ZZ-000-WW/Electric_50").then().statusCode(200);
		given().when().post("/parking/unparkCar/ZZ-000-WW").then().statusCode(200).body("amount",
				Matchers.greaterThanOrEqualTo(0));
	}

}