package com.likeaglove.core.model;

/**
 * This is a simple linear pricing policy
 * 
 * @author AceVentura
 *
 */
public class SimplePricingPolicy implements PricingPolicy {

	private int initialCharge;
	private int hourlyRate;

	/**
	 * Construct a policy that will follow the rule: amount = initialCharge +
	 * hourlyRate*number of hours
	 * 
	 * @param initialCharge the initial price
	 * @param hourlyRate    the price to pay per hour spent in the parking
	 */
	public SimplePricingPolicy(int initialCharge, int hourlyRate) {
		this.initialCharge = initialCharge;
		this.hourlyRate = hourlyRate;
	}

	@Override
	public ParkingBill computeBill(int timeSpent) {
		ParkingBill bill = new ParkingBill();
		bill.setAmount((long) this.hourlyRate * timeSpent + this.initialCharge);
		return bill;
	}

}
