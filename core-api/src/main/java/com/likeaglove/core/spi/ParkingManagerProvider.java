package com.likeaglove.core.spi;

import com.likeaglove.core.api.ParkingManager;
import com.likeaglove.core.configuration.ParkingConfiguration;

/**
 * Description of a Service Provider of {@link ParkingManager}
 * 
 * @author AceVentura
 *
 */
public interface ParkingManagerProvider {

	/**
	 * Generate a Parking Manager from a parking configuration
	 * @param parkingConfiguration description of the parking structure
	 * @return a Parking Manager
	 */
	ParkingManager create(ParkingConfiguration parkingConfiguration);

}
