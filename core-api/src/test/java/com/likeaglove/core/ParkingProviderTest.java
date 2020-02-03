package com.likeaglove.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.likeaglove.core.spi.ParkingManagerProvider;

class ParkingProviderTest {

	@Test
	void testProviders() {
		List<ParkingManagerProvider> providers = ParkingManagerServiceLoader.providers();
		assertNotNull(providers);
		assertFalse(providers.isEmpty());
		ParkingManagerProvider dummyProvider = providers.get(0);
		assertEquals(DummyServiceProvider.class.toString(), dummyProvider.getClass().toString());
	}

	@Test
	void testProviderString() {
		ParkingManagerProvider dummyProvider = ParkingManagerServiceLoader
				.provider(DummyServiceProvider.class.getName());
		assertNotNull(dummyProvider);
		assertEquals(DummyServiceProvider.class.toString(), dummyProvider.getClass().toString());
	}

}
