package com.likeaglove.core.quarkus.controller;

import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.joda.time.DateTime;
import org.joda.time.Hours;

import com.likeaglove.core.api.ParkingManager;
import com.likeaglove.core.configuration.ParkingConfiguration;
import com.likeaglove.core.model.ParkingBill;
import com.likeaglove.core.model.ParkingSlot;
import com.likeaglove.core.model.PricingPolicy;
import com.likeaglove.core.model.SlotType;
import com.likeaglove.core.quarkus.entity.ParkingSlotEntity;

/**
 * A hibernate implementation of a ParkingManager
 * @author AceVentura
 *
 */
@ApplicationScoped
public class ParkingController implements ParkingManager {

	private PricingPolicy pricingPolicy;

	@ConfigProperty(name = "slot.standard.size", defaultValue = "5")
	public int standardSlotSize;

	@ConfigProperty(name = "slot.electric20.size", defaultValue = "7")
	public int electric20SlotSize;

	@ConfigProperty(name = "slot.electric50.size", defaultValue = "9")
	public int electric50SlotSize;

	@ConfigProperty(name = "policy.fixedAmount", defaultValue = "10")
	public int fixedAmount;

	@ConfigProperty(name = "policy.rateByHour", defaultValue = "10")
	public int rateByHour;

	/**
	 * Initialize the database. The way to construct a brand new parking should be
	 * enhanced (generated slot names are not convenient, we should let the parking
	 * owner put names to ease people find their slots).
	 */
	@PostConstruct
	@Transactional
	public void prepareParking() {
		ParkingConfiguration configuration = ParkingConfiguration.newBuilder()
				.withSimplePricePolicy(fixedAmount, rateByHour).addSlots()
				.addRange(SlotType.Standard, standardSlotSize).addRange(SlotType.Electric_20, electric20SlotSize)
				.addRange(SlotType.Electric_50, electric50SlotSize).rangeSlotsDone().build();
		this.pricingPolicy = configuration.getPricingPolicy();
		// Part to rewrite using streams
		for (Entry<SlotType, Integer> entry : configuration.getRangeSlotByType().entrySet()) {
			for (int i = 0; i < entry.getValue(); i++) {
				ParkingSlotEntity parkingSlot = new ParkingSlotEntity();
				parkingSlot.setName(entry.getKey().toString() + ": " + (i + 1));
				parkingSlot.setFree(true);
				parkingSlot.setType(entry.getKey());
				ParkingSlotEntity.persist(parkingSlot);
			}
		}
	}

	@Override
	@Transactional
	public Optional<ParkingSlot> findFreeParkingSlotAndPark(SlotType slotType, String registrationPlate) {
		ParkingSlotEntity firstResult = ParkingSlotEntity.find("registrationNumber", registrationPlate).firstResult();
		if (firstResult != null) {
			throw ParkingSlotException.carAlreadyParked(firstResult.getName());
		} else {
			ParkingSlotEntity parkingSlotEntity = ParkingSlotEntity.findFreeSlotByType(slotType);
			if (parkingSlotEntity != null) {
				parkingSlotEntity.setRegistrationNumber(registrationPlate);
				parkingSlotEntity.setFree(false);
				parkingSlotEntity.setStartDate(new Date());
				ParkingSlotEntity.persist(parkingSlotEntity);
				return Optional.of(parkingSlotEntity.toParkingSlot());
			}
		}
		return Optional.empty();
	}

	@Override
	@Transactional
	public Optional<ParkingBill> leaveParkingSlot(String registrationPlate) {
		ParkingSlotEntity parkingSlot = ParkingSlotEntity.find("registrationNumber", registrationPlate).firstResult();
		if (parkingSlot != null) {
			parkingSlot.setFree(true);
			parkingSlot.setRegistrationNumber(null);
			ParkingBill bill = this.pricingPolicy.computeBill(
					Hours.hoursBetween(new DateTime(parkingSlot.getStartDate()), new DateTime()).getHours());
			parkingSlot.setStartDate(null);
			ParkingSlotEntity.persist(parkingSlot);
			return Optional.of(bill);
		}
		return Optional.empty();
	}

	@Override
	public List<ParkingSlot> getAllSlots() {
		return ParkingSlotEntity.listAll().stream().map(item -> ((ParkingSlotEntity) item).toParkingSlot())
				.collect(Collectors.toList());
	}

}
