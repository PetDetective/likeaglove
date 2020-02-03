package com.ikeaglove.core.sampleapp;

import com.likeaglove.core.model.SlotType;

/**
 * Aimed to display in the console wizard
 * 
 * @author AceVentura
 *
 */
public enum CarType {
	/**
	 * Gasoline car
	 */
	Sedan("Gasoline powered"),

	/**
	 * 20kW Electric Car
	 */
	Electric_20("Electric Car 20kW"),
	/**
	 * 50kW Electric Car
	 */
	Electric_50("Electric Car 50kW");

	private String displayName;

	private CarType(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}

	/**
	 * Convert a car type to a valid parking slot type
	 * 
	 * @return a parking slot type
	 */
	public SlotType toSlotType() {
		switch (this) {
		case Sedan:
			return SlotType.Standard;
		case Electric_20:
			return SlotType.Electric_20;
		case Electric_50:
			return SlotType.Electric_50;
		default:
			return null;
		}
	}

	/**
	 * Determine the type of car when it is parked on a given parking slot type
	 * 
	 * @param parkingSlotType the type of the parking slot
	 * @return a deduced car type
	 */
	public static CarType fromSlotType(SlotType parkingSlotType) {
		switch (parkingSlotType) {
		case Standard:
			return Sedan;
		case Electric_20:
			return Electric_20;
		case Electric_50:
			return Electric_50;
		default:
			return null;

		}
	}
}
