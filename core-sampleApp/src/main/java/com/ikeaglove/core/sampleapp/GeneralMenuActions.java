package com.ikeaglove.core.sampleapp;

/**
 * The general menu actions for the console wizard
 * 
 * @author AceVentura
 *
 */
public enum GeneralMenuActions {
	/**
	 * To park a car
	 */
	ParkCar("Park a car"),
	/**
	 * To unpark a car
	 */
	UnParkCar("Unpark a car"),
	/**
	 * to display the parking map
	 */
	DisplaySlotUsage("Display parking map"),
	/**
	 * to close this application
	 */
	ExitApplication("Close the application");

	private String display;

	private GeneralMenuActions(String display) {
		this.display = display;
	}

	@Override
	public String toString() {
		return this.display;
	}
}