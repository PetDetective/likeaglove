package com.likeaglove.core.impl;

import com.likeaglove.core.api.ParkingManager;
import com.likeaglove.core.configuration.ParkingConfiguration;
import com.likeaglove.core.spi.ParkingManagerProvider;

/**
 * Allow the service creation for the SPI mechanism.
 * 
 * @author AceVentura
 *
 */
public class InMemoryParkingProvider implements ParkingManagerProvider {

	@Override
	public ParkingManager create(ParkingConfiguration parkingConfiguration) {
		return new InMemoryParkingManagerImpl(parkingConfiguration);
	}

}
