package com.likeaglove.core.model;

import java.util.Date;

/**
 * A Parking Slot can be free or not and store when it is occupied
 * 
 * @author AceVentura
 *
 */
public class ParkingSlot {

	private Date startDate;
	private boolean isFree;
	private SlotType type;
	private String name;
	private String registrationNumber;

	/**
	 * Nature of the slot.
	 * 
	 * @return the type of car this slot can host
	 */
	public SlotType getType() {
		return type;
	}

	/**
	 * Define the type of slot
	 * 
	 * @param slotType define what kind of car can park on the slot
	 */
	public void setType(SlotType slotType) {
		this.type = slotType;
	}

	/**
	 * Is the slot free?
	 * 
	 * @return true if the slot can be used; false if already in use
	 */
	public boolean isFree() {
		return isFree;
	}

	/**
	 * Free or taken slot
	 * 
	 * @param isFree define if the slot is taken or not
	 */
	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	/**
	 * Define beginning of occupancy
	 * 
	 * @param startDate when a car started to park
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * When occupancy started
	 * 
	 * @return the date when a car started to occupy this slot; might be null in
	 *         case the slot is free
	 */
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * Get the name of this slot
	 * 
	 * @return the name of this slot
	 */
	public String getName() {
		return name;
	}

	/**
	 * Define a name for this slot
	 * 
	 * @param name the name of the slot
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the registration number if there is a car parked on this slot.
	 * 
	 * @return the registration number
	 */
	public String getRegistrationNumber() {
		return this.registrationNumber;
	}

	/**
	 * Provide the registration number.
	 * 
	 * @param registrationNumber registration number of a parked car
	 */
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

}
