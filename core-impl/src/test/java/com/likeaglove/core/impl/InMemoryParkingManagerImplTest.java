package com.likeaglove.core.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.likeaglove.core.configuration.ParkingConfiguration;
import com.likeaglove.core.model.ParkingBill;
import com.likeaglove.core.model.PricingPolicy;
import com.likeaglove.core.model.SlotType;

@SuppressWarnings("javadoc")
public class InMemoryParkingManagerImplTest {

	@Test
	void testInMemoryParkingManagerImpl() {
		InMemoryParkingManagerImpl managerToTest = new InMemoryParkingManagerImpl(ParkingConfiguration.newBuilder()
				.withSimplePricePolicy(10, 10).addSlots().addRange(SlotType.Standard, 1)
				.addRange(SlotType.Electric_20, 1).addRange(SlotType.Electric_50, 1).rangeSlotsDone().build());
		assertTrue(managerToTest.findFreeParkingSlotAndPark(SlotType.Standard, "123").isPresent());
		assertTrue(managerToTest.findFreeParkingSlotAndPark(SlotType.Standard, "456").isEmpty());

		assertTrue(managerToTest.leaveParkingSlot("456").isEmpty());
		assertTrue(managerToTest.leaveParkingSlot("123").isPresent());
	}

	class DummyPolicy implements PricingPolicy {

		protected boolean called = false;

		@Override
		public ParkingBill computeBill(int durationtInHours) {
			called = true;
			return null;
		}

	}

	@DisplayName("Check the custom policy is called")
	@Test
	void testInMemoryParkingManagerPricingPolicy() {
		// This part should be replaced by proper Mockito usage...
		DummyPolicy mockPricingPolicy = new DummyPolicy();
		InMemoryParkingManagerImpl managerToTest = new InMemoryParkingManagerImpl(
				ParkingConfiguration.newBuilder().withPricingPolicy(mockPricingPolicy).addSlots()
						.addRange(SlotType.Standard, 1).rangeSlotsDone().build());
		managerToTest.findFreeParkingSlotAndPark(SlotType.Standard, "123");
		managerToTest.leaveParkingSlot("123");
		assertTrue(mockPricingPolicy.called);
	}

	@DisplayName("Check the getSlots")
	@Test
	void testGetSlots() {
		InMemoryParkingManagerImpl managerToTest = new InMemoryParkingManagerImpl(ParkingConfiguration.newBuilder()
				.withSimplePricePolicy(10, 10).addSlots().addRange(SlotType.Standard, 10)
				.addRange(SlotType.Electric_20, 20).addRange(SlotType.Electric_50, 30).rangeSlotsDone().build());
		assertEquals(60,managerToTest.getAllSlots().size());
	}

}
