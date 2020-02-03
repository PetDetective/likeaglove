package com.likeaglove.core.configuration;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import com.likeaglove.core.model.SlotType;

/**
 * A utility class aimed to construct the range slots of a
 * {@link ParkingConfiguration}
 * 
 * @author AceVentura
 *
 */
public class RangeSlotBuilder {

	private Map<SlotType, Integer> rangeSlotByType;

	private ParkingConfiguration.Builder parentParkingBuilder;

	private RangeSlotBuilder(ParkingConfiguration.Builder parentParkingBuilder) {
		this.parentParkingBuilder = parentParkingBuilder;
		rangeSlotByType = new EnumMap<SlotType, Integer>(SlotType.class);
	}

	/**
	 * instantiate a new slot builder
	 * 
	 * @param parentParkingBuilder reference to the parent builder
	 * @return a Builder to add the slots to the parking
	 */
	public static RangeSlotBuilder newBuilder(ParkingConfiguration.Builder parentParkingBuilder) {
		Objects.requireNonNull(parentParkingBuilder);
		return new RangeSlotBuilder(parentParkingBuilder);
	}

	/**
	 * Add a new type of range slots
	 * 
	 * @param slotType the type of car that can be parked on this slot
	 * @param size     the number of total slots. Must be strictly positive
	 * @return a builder to provide additionnal slots for the parking
	 */
	public RangeSlotBuilder addRange(SlotType slotType, int size) {
		Objects.requireNonNull(slotType);
		if (size <= 0) {
			throw new IllegalArgumentException(
					"The size of a range of parking slots must be strictly positive: invalid value: " + size);
		}
		this.rangeSlotByType.put(slotType, size);
		return this;
	}

	/**
	 * Terminate the construction of the slots and validate them.
	 * 
	 * @return can raise {@link RuntimeException} if the slot construction is
	 *         incoherent
	 */
	public ParkingConfiguration.Builder rangeSlotsDone() {
		if (rangeSlotByType.isEmpty()) {
			throw new RuntimeException("There must be at least one range slot in a Parking");
		}
		return this.parentParkingBuilder.withRangeSlots(rangeSlotByType);
	}

}
