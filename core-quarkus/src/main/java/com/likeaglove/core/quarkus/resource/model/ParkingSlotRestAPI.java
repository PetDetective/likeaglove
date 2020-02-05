package com.likeaglove.core.quarkus.resource.model;

import java.util.Date;

import com.likeaglove.core.model.ParkingSlot;
import com.likeaglove.core.model.SlotType;

/**
 * 
 * This is to avoid exposing the internal model or the model of the core-api for
 * potential evolutions.
 * 
 * @author AceVentura
 *
 */
public class ParkingSlotRestAPI {
	private ParkingSlot parkingSlot;

	ParkingSlotRestAPI() {
		parkingSlot = new ParkingSlot();
	}

	/**
	 * Get the type of this slot
	 * 
	 * @return type of this slot
	 */
	public SlotType getType() {
		return parkingSlot.getType();
	}

	/**
	 * Define the type of the slot
	 * 
	 * @param slotType the type of the slot
	 */
	public void setType(SlotType slotType) {
		parkingSlot.setType(slotType);
	}

	/**
	 * Know if the slot is empty
	 * 
	 * @return true if empty
	 */
	public boolean isFree() {
		return parkingSlot.isFree();
	}

	/**
	 * Define occupancy of the slot
	 * 
	 * @param isFree true if the slot is vacant
	 */
	public void setFree(boolean isFree) {
		parkingSlot.setFree(isFree);
	}

	/**
	 * When did the car parked on this slot
	 * 
	 * @param startDate date when the car parked
	 */
	public void setStartDate(Date startDate) {
		parkingSlot.setStartDate(startDate);
	}

	/**
	 * Get the date when the park parked.
	 * 
	 * @return the date when the park parked
	 */
	public Date getStartDate() {
		return parkingSlot.getStartDate();
	}

	/**
	 * Get the name of the slot
	 * 
	 * @return slot name
	 */
	public String getName() {
		return parkingSlot.getName();
	}

	/**
	 * Define the name of the slot.
	 * 
	 * @param name the name of the slot
	 */
	public void setName(String name) {
		parkingSlot.setName(name);
	}

	/**
	 * Get the registration number of the car on the slot
	 * 
	 * @return the registration number of the car
	 */
	public String getRegistrationNumber() {
		return parkingSlot.getRegistrationNumber();
	}

	/**
	 * Define the registration number of the car
	 * 
	 * @param registrationNumber the registration number of the car
	 */
	public void setRegistrationNumber(String registrationNumber) {
		parkingSlot.setRegistrationNumber(registrationNumber);
	}

	/**
	 * Utility method that translate a parking slot from its core-api model to the
	 * rest model.
	 * 
	 * @param parkingSlot slot to convert
	 * @return slot for rest services
	 */
	public static ParkingSlotRestAPI convert(ParkingSlot parkingSlot) {
		ParkingSlotRestAPI result = new ParkingSlotRestAPI();
		result.setName(parkingSlot.getName());
		result.setFree(parkingSlot.isFree());
		result.setRegistrationNumber(parkingSlot.getRegistrationNumber());
		result.setStartDate(parkingSlot.getStartDate());
		result.setType(parkingSlot.getType());
		return result;
	}

}
