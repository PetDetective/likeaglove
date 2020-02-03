package com.likeaglove.core.model;

/**
 * Describe a pricing policy
 * 
 * @author AceVentura
 *
 */
public interface PricingPolicy {

	/**
	 * Define how to bill the customer
	 * 
	 * @param durationtInHours time spent in the parking in hours
	 * @return a bill for the customer
	 */
	ParkingBill computeBill(int durationtInHours);

}
