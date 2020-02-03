package com.likeaglove.core.api;

import java.util.List;
import java.util.Optional;

import com.likeaglove.core.model.ParkingBill;
import com.likeaglove.core.model.ParkingSlot;
import com.likeaglove.core.model.SlotType;

/**
 * A ParkingManager is an engine to manage a Tool Parking.
 * 
 * @author AceVentura
 *
 */
public interface ParkingManager {

	/**
	 * Find a free parking slot of a given {@link SlotType} and assign the slot to
	 * the given registration number.
	 * 
	 * @param slotType          the type of the slot
	 * @param registrationPlate the registration number of the car
	 * @return an assigned {@link ParkingSlot}
	 */
	Optional<ParkingSlot> findFreeParkingSlotAndPark(SlotType slotType, String registrationPlate);

	/**
	 * Unassigns a parking slot of a car, and return a Parking Bill for payment
	 * 
	 * @param registrationPlate the car leaving the parking
	 * @return a bill
	 */
	Optional<ParkingBill> leaveParkingSlot(String registrationPlate);

	/**
	 * Retrieve all the slots of the Parking and their usage
	 * 
	 * @return a list of Parking Slots
	 */
	List<ParkingSlot> getAllSlots();

}
