package com.likeaglove.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class SimplePricingPolicyTest {

	@Test
	void testComputePrice() {
		SimplePricingPolicy simplePricingPolicy = new SimplePricingPolicy(10, 24);
		ParkingBill parkingBill = simplePricingPolicy.computeBill(9);
		assertNotNull(parkingBill);
		assertEquals(226, parkingBill.getAmount());
	}

}
