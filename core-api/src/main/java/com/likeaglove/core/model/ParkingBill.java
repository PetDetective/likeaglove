package com.likeaglove.core.model;

/**
 * A parking Bill for a car leaving the parking
 * 
 * @author AceVentura
 *
 */
public class ParkingBill {

	private long amount;

	/**
	 * Amount to pay
	 * 
	 * @return the amount to pay
	 */
	public long getAmount() {
		return amount;
	}

	/**
	 * Set the amount to pay
	 * 
	 * @param amount the amount to pay
	 */
	public void setAmount(long amount) {
		this.amount = amount;
	}

}
