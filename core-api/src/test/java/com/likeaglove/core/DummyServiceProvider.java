package com.likeaglove.core;

import com.likeaglove.core.api.ParkingManager;
import com.likeaglove.core.configuration.ParkingConfiguration;
import com.likeaglove.core.spi.ParkingManagerProvider;

/**
 * Needed for the validation of the SPI mechanism
 * @author AceVentura
 *
 */
public class DummyServiceProvider implements ParkingManagerProvider {

	@Override
	public ParkingManager create(ParkingConfiguration parkingConfiguration) {
		return null;
	}

}
