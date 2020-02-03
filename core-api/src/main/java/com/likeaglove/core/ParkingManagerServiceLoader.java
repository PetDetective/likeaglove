package com.likeaglove.core;

import java.nio.file.ProviderNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import com.likeaglove.core.spi.ParkingManagerProvider;

/**
 * This class in aimed to load the implementations of
 * {@link ParkingManagerProvider}.
 * 
 * This is inspired by the following tutorial: https://www.baeldung.com/java-spi
 * 
 * @author AceVentura
 *
 */
public class ParkingManagerServiceLoader {

	private static final String DEFAULT_PROVIDER = "com.likeaglove.core.impl.InMemoryParkingProvider";

	/**
	 * List all the services of type {@link ParkingManagerProvider}.
	 * 
	 * @return a list of services implementing {@link ParkingManagerProvider}
	 */
	public static List<ParkingManagerProvider> providers() {
		List<ParkingManagerProvider> services = new ArrayList<>();
		ServiceLoader<ParkingManagerProvider> loader = ServiceLoader.load(ParkingManagerProvider.class);
		loader.forEach(provider -> {
			services.add(provider);
		});
		return services;
	}

	/**
	 * Get a basic service
	 * 
	 * @return a default service implementing {@link ParkingManagerProvider}
	 */
	public static ParkingManagerProvider provider() {
		return provider(DEFAULT_PROVIDER);
	}

	/**
	 * Perform a lookup to load a specific {@link ParkingManagerProvider}.
	 * 
	 * @param providerName the full qualified name of the service to load
	 * @return a {@link ParkingManagerProvider} matching the class name.
	 */
	public static ParkingManagerProvider provider(String providerName) {
		ServiceLoader<ParkingManagerProvider> loader = ServiceLoader.load(ParkingManagerProvider.class);
		Iterator<ParkingManagerProvider> it = loader.iterator();
		while (it.hasNext()) {
			ParkingManagerProvider provider = it.next();
			if (providerName.equals(provider.getClass().getName())) {
				return provider;
			}
		}
		throw new ProviderNotFoundException("Parking Manager provider " + providerName + " not found");
	}
}
