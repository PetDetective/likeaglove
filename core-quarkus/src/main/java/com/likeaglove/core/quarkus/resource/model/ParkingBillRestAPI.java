package com.likeaglove.core.quarkus.resource.model;

import com.likeaglove.core.model.ParkingBill;

/**
 * 
 * This is to avoid exposing the internal model or the model of the core-api for
 * potential evolutions.
 * 
 * @author AceVentura
 *
 */
public class ParkingBillRestAPI {

	private ParkingBill parkingBill;

	public ParkingBillRestAPI() {
		parkingBill = new ParkingBill();
	}

	public long getAmount() {
		return parkingBill.getAmount();
	}

	public void setAmount(long amount) {
		parkingBill.setAmount(amount);
	}

	/**
	 * Convert from the core-api datamodel to the service data model
	 * 
	 * @param parkingBill the object to convert
	 * @return a Parking Bill to send on the wire
	 */
	public static ParkingBillRestAPI convert(ParkingBill parkingBill) {
		ParkingBillRestAPI result = new ParkingBillRestAPI();
		result.setAmount(parkingBill.getAmount());
		return result;
	}

}
