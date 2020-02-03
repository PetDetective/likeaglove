package com.likeaglove.core.impl;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.likeaglove.core.api.ParkingManager;
import com.likeaglove.core.configuration.ParkingConfiguration;
import com.likeaglove.core.model.ParkingBill;
import com.likeaglove.core.model.ParkingSlot;
import com.likeaglove.core.model.PricingPolicy;
import com.likeaglove.core.model.SlotType;

/**
 * This is a basic implementation of a {@link ParkingManager} The data are in
 * memory and not persisted.
 * 
 * @author AceVentura
 *
 */
public class InMemoryParkingManagerImpl implements ParkingManager {

	private PricingPolicy pricingPolicy;
	private EnumMap<SlotType, RangeParkingSlot> parkingSlots;

	/**
	 * Initialize a new parking
	 * 
	 * @param parkingConfiguration the configuration of the parking
	 */
	public InMemoryParkingManagerImpl(ParkingConfiguration parkingConfiguration) {
		Objects.requireNonNull(parkingConfiguration);
		this.pricingPolicy = parkingConfiguration.getPricingPolicy();
		// Initialization of the parking
		this.parkingSlots = parkingConfiguration.getRangeSlotByType().entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> new RangeParkingSlot(entry.getKey(), entry.getValue()),
						(a, b) -> a, () -> new EnumMap<>(SlotType.class)));
	}

	@Override
	public Optional<ParkingSlot> findFreeParkingSlotAndPark(SlotType slotType, String registrationPlate) {
		Objects.requireNonNull(slotType);
		Objects.requireNonNull(registrationPlate);
		// Find the first free slot matching the car type and park the car on it
		return this.parkingSlots.entrySet().stream()
				.filter(ps -> ps.getKey().equals(slotType) && ps.getValue().hasFreeSlot()).findFirst()
				.map(a -> a.getValue().parkCar(registrationPlate));
	}

	@Override
	public Optional<ParkingBill> leaveParkingSlot(String registrationPlate) {
		Objects.requireNonNull(registrationPlate);
		// Find on with Range the car is parked; un-park it and bill the customer
		return this.parkingSlots.values().stream().filter(parkingSlot -> parkingSlot.hasCar(registrationPlate)).findFirst()
				.map(parkingSlot -> parkingSlot.unparkCar(registrationPlate))
				.map(duration -> this.pricingPolicy.computeBill(duration));
	}

	@Override
	public List<ParkingSlot> getAllSlots() {
		return this.parkingSlots.values().stream().flatMap(rangeParkingSlot -> rangeParkingSlot.getSlots().stream())
				.collect(Collectors.toList());
	}

}
