package com.likeaglove.core.quarkus.resource;

import java.util.stream.Stream;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.likeaglove.core.model.SlotType;
import com.likeaglove.core.quarkus.controller.ParkingController;
import com.likeaglove.core.quarkus.controller.ParkingSlotException;
import com.likeaglove.core.quarkus.resource.model.ParkingBillRestAPI;
import com.likeaglove.core.quarkus.resource.model.ParkingSlotRestAPI;

/**
 * 
 * Http entry point of our application.
 * 
 * @author AceVentura
 *
 */
@Path("/parking")
public class ParkingResource {

	@Inject
	ParkingController parkingController;

	/**
	 * Display the structure and usage of the parking. might be problematic in term
	 * of performance (no paging implemented)
	 * 
	 * @return the full content of the parking
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Stream<ParkingSlotRestAPI> getAllParkingContent() {
		Stream<ParkingSlotRestAPI> result = parkingController.getAllSlots().stream()
				.map(parkingSlot -> ParkingSlotRestAPI.convert(parkingSlot));
		return result;
	}

	/**
	 * Service to park a car
	 * 
	 * @param registrationNumber the registration number of the car
	 * @param slotType           the type of slot this car needs to be parked on
	 * @return where the car was parked
	 */
	@POST
	@Path("/parkCar/{registrationNumber}/{slotType}")
	@Produces(MediaType.APPLICATION_JSON)
	public ParkingSlotRestAPI parkCar(@PathParam("registrationNumber") String registrationNumber,
			@PathParam("slotType") SlotType slotType) {
		return parkingController.findFreeParkingSlotAndPark(slotType, registrationNumber)
				.map(slotAndPark -> ParkingSlotRestAPI.convert(slotAndPark))
				.orElseThrow(() -> ParkingSlotException.noSlotFound(slotType));
	}

	/**
	 * When a car is leaving, this service will provide the parking bill.
	 * 
	 * @param registratioNumber the registration number of the car
	 * @return a bill
	 */
	@POST
	@Path("/unparkCar/{registrationNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public ParkingBillRestAPI unparkCar(@PathParam("registrationNumber") String registratioNumber) {
		return parkingController.leaveParkingSlot(registratioNumber).map(bill -> ParkingBillRestAPI.convert(bill))
				.orElseThrow(() -> ParkingSlotException.carNotFound());
	}
}