package com.likeaglove.core.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.joda.time.DateTime;
import org.joda.time.Hours;

import com.likeaglove.core.model.ParkingSlot;
import com.likeaglove.core.model.SlotType;

/**
 * A RangeParkingSlot is a set of Parking Slots of the same nature
 * 
 * @author AceVentura
 *
 */
public class RangeParkingSlot {

	// Ensure this.inUse thread safety and guarantee this.inUse and this.freeSlots
	// stays inSync
	private final static Object lock = new Object();
	private Vector<ParkingSlot> freeSlots;
	private Map<String, ParkingSlot> inUse;

	/**
	 * This will initialize the range with un-occupied slots
	 * 
	 * @param slotType     nature of the parking slots
	 * @param numberOfSlot the maximum number of car that can be hosted
	 */
	public RangeParkingSlot(SlotType slotType, int numberOfSlot) {
		this.inUse = new HashMap<String, ParkingSlot>(numberOfSlot);
		this.freeSlots = new Vector<ParkingSlot>(numberOfSlot);
		// Fill in the freeSlots.
		IntStream.rangeClosed(1, numberOfSlot).forEach(i -> {
			ParkingSlot slot = new ParkingSlot();
			slot.setName(slotType.toString() + " " + i);
			// This implementation delegate the occupancy and the slot type to the
			// RangeParkingSlot itself
			// the following 2 lines are useless.
			slot.setFree(true);
			slot.setType(slotType);
			this.freeSlots.add(slot);
		});
	}

	/**
	 * Is there a remaining slot?
	 * 
	 * @return true if there is a free slot
	 */
	public boolean hasFreeSlot() {
		return !freeSlots.isEmpty();
	}

	/**
	 * Does this slot range contains the car with the given registration number
	 * 
	 * @param registrationPlate the registration number of a car
	 * @return true if this slot contains a car with the provided registration plate
	 */
	public boolean hasCar(String registrationPlate) {
		return this.inUse.containsKey(registrationPlate);
	}

	/**
	 * Will park a car if a slot is available.
	 * 
	 * @param registrationNumber the registration number of the car
	 * @return an occupied parking slot if possible
	 */
	public ParkingSlot parkCar(String registrationNumber) {
		// Ensure this.inUse thread safety and guarantee this.inUse and this.freeSlots
		// stays inSync
		synchronized (lock) {
			if (!freeSlots.isEmpty() && !inUse.containsKey(registrationNumber)) {
				ParkingSlot slot = freeSlots.remove(0);
				slot.setRegistrationNumber(registrationNumber);
				slot.setFree(false);
				slot.setStartDate(new Date());
				inUse.put(registrationNumber, slot);
				return slot;
			}
			return null;
		}
	}

	/**
	 * Provide the time spent in the parking in hours when a car is leaving. Calling
	 * this method will free the parking slot.
	 * 
	 * @param registrationPlate the registration number
	 * @return time spent in hours
	 */
	public Integer unparkCar(String registrationPlate) {
		// Ensure this.inUse thread safety and guarantee this.inUse and this.freeSlots
		// stays inSync
		synchronized (lock) {
			if (inUse.containsKey(registrationPlate)) {
				ParkingSlot slot = inUse.remove(registrationPlate);
				int hourSpent = Hours.hoursBetween(new DateTime(slot.getStartDate()), new DateTime()).getHours();
				slot.setRegistrationNumber(null);
				slot.setStartDate(null);
				slot.setFree(true);
				freeSlots.add(slot);
				return hourSpent;
			}
			return null;
		}
	}

	/**
	 * Retrieve all the parking slots of this range.
	 * 
	 * @return an unmodifiable list of parking slots
	 */
	public List<ParkingSlot> getSlots() {
		synchronized (lock) {
			List<ParkingSlot> result = Stream.concat(this.freeSlots.stream(), this.inUse.values().stream())
					.collect(Collectors.toUnmodifiableList());
			return result;
		}

	}

}
