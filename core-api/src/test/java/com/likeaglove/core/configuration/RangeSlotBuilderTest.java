package com.likeaglove.core.configuration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.likeaglove.core.configuration.ParkingConfiguration.Builder;
import com.likeaglove.core.model.SlotType;

class RangeSlotBuilderTest {

	@Test
	void testNewBuilder() {
		assertNotNull(RangeSlotBuilder.newBuilder(Mockito.mock(ParkingConfiguration.Builder.class)));
	}

	@Test
	void testRange() {
		Builder mock = Mockito.mock(ParkingConfiguration.Builder.class);
		RangeSlotBuilder builderToTest = RangeSlotBuilder.newBuilder(mock);
		assertThrows(RuntimeException.class, () -> {
			builderToTest.rangeSlotsDone();
		});
		assertThrows(IllegalArgumentException.class, () -> {
			builderToTest.addRange(SlotType.Electric_20, -1);
		});
		builderToTest.addRange(SlotType.Electric_20, 1).rangeSlotsDone();
		Mockito.verify(mock).withRangeSlots(Mockito.any());
	}

}
