package com.likeaglove.core.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.likeaglove.core.configuration.ParkingConfiguration;

class InMemoryParkingProviderTest {

	@Test
	void testCreate() {
		// This test is Weak
		ParkingConfiguration mockConfig = Mockito.mock(ParkingConfiguration.class);
		assertNotNull(new InMemoryParkingProvider().create(mockConfig));
		Mockito.verify(mockConfig, Mockito.atLeastOnce());
	}

}
