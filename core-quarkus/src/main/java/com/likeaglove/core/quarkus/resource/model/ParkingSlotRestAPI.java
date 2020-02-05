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

	public SlotType getType() {
		return parkingSlot.getType();
	}

	public void setType(SlotType slotType) {
		parkingSlot.setType(slotType);
	}

	public boolean isFree() {
		return parkingSlot.isFree();
	}

	public void setFree(boolean isFree) {
		parkingSlot.setFree(isFree);
	}

	public void setStartDate(Date startDate) {
		parkingSlot.setStartDate(startDate);
	}

	public Date getStartDate() {
		return parkingSlot.getStartDate();
	}

	public String getName() {
		return parkingSlot.getName();
	}

	public void setName(String name) {
		parkingSlot.setName(name);
	}

	public String getRegistrationNumber() {
		return parkingSlot.getRegistrationNumber();
	}

	public void setRegistrationNumber(String registrationNumber) {
		parkingSlot.setRegistrationNumber(registrationNumber);
	}

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
