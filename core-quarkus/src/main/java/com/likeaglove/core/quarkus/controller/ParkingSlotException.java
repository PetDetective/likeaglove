package com.likeaglove.core.quarkus.controller;

import javax.ws.rs.core.Response.Status;

import com.likeaglove.core.model.SlotType;

/**
 * This is a runtime exception class to manage the error and their status code.
 * 
 * @author AceVentura
 *
 */
public class ParkingSlotException extends RuntimeException {

	private static final long serialVersionUID = -5432361131861756101L;

	private String message;

	private Status status;

	/**
	 * Message to display to the end user.
	 * 
	 * @return a message to describe the error.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Define the error message
	 * 
	 * @param message description of the error
	 */
	private void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Http Status code that will be returned to the end user
	 * 
	 * @return the Http status code
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Define a specific status code to be returned to the end user
	 * 
	 * @param status
	 */
	private void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Utility to generate an error when a similar car was detected in the parking
	 * 
	 * @param slotName the slot name to help finding the car
	 * @return a cleanly constructed exception
	 */
	public static ParkingSlotException carAlreadyParked(String slotName) {
		ParkingSlotException result = new ParkingSlotException();
		result.setMessage(
				"A car with a similar registration number is parker in the following parking slot:" + slotName);
		result.setStatus(Status.CONFLICT);
		return result;
	}

	/**
	 * When there is exhaustion of slots of the correct type
	 * 
	 * @param slotType the type of slot that is exhausted
	 * @return a cleanly constructed exception
	 */
	public static ParkingSlotException noSlotFound(SlotType slotType) {
		ParkingSlotException result = new ParkingSlotException();
		result.setMessage("The parking do not contain any more free slot of type: " + slotType);
		result.setStatus(Status.TOO_MANY_REQUESTS);
		return result;
	}

	/**
	 * Dude where's my Car?
	 * Error that will get throwed if we are not able to find the car.
	 * @return a cleanly constructed exception
	 */
	public static ParkingSlotException carNotFound() {
		ParkingSlotException result = new ParkingSlotException();
		result.setMessage("The parking do not contain this car!");
		result.setStatus(Status.GONE);
		return result;
	}

}
