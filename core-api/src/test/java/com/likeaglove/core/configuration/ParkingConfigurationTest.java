package com.likeaglove.core.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.likeaglove.core.model.PricingPolicy;
import com.likeaglove.core.model.SlotType;

@SuppressWarnings("javadoc")
public class ParkingConfigurationTest {

	@DisplayName("Test a valid parking configuration with a custom pricing policy")
	@Test
	void testValidConfiguration() {
		PricingPolicy myPricingPolicy = Mockito.mock(PricingPolicy.class);
		ParkingConfiguration parkingConfiguration = ParkingConfiguration.newBuilder().withPricingPolicy(myPricingPolicy)
				.addSlots().addRange(SlotType.Standard, 28).addRange(SlotType.Electric_20, 10)
				.addRange(SlotType.Electric_50, 5).rangeSlotsDone().build();
		assertNotNull(parkingConfiguration.getPricingPolicy());
		assertEquals(myPricingPolicy, parkingConfiguration.getPricingPolicy());
		assertNotNull(parkingConfiguration.getRangeSlotByType());
		assertNotNull(parkingConfiguration.getRangeSlotByType().get(SlotType.Standard));
		assertEquals(28, parkingConfiguration.getRangeSlotByType().get(SlotType.Standard).intValue());
	}

	@DisplayName("Test a valid parking configuration with a simple pricing policy")
	@Test
	void testValidConfigurationWithSimplePolicy() {
		ParkingConfiguration parking = ParkingConfiguration.newBuilder().withSimplePricePolicy(0, 0).addSlots()
				.addRange(SlotType.Standard, 28).rangeSlotsDone().build();
		assertNotNull(parking.getPricingPolicy());
	}

	@DisplayName("Test invalid parking configurations.")
	@Test
	void testInValidConfigurations() {
		assertThrows(IllegalArgumentException.class, () -> {
			ParkingConfiguration.newBuilder().withSimplePricePolicy(-1, 0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			ParkingConfiguration.newBuilder().withSimplePricePolicy(0, -1);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			ParkingConfiguration.newBuilder().withSimplePricePolicy(10, 10).addSlots().addRange(SlotType.Standard, -1);
		});
	}
}
