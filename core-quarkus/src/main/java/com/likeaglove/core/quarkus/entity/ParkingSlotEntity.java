package com.likeaglove.core.quarkus.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.likeaglove.core.model.ParkingSlot;
import com.likeaglove.core.model.SlotType;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * DB representation of a Parking Slot. Trying to decouple the datamodel in DB
 * from the core-api one for future evolutions
 * 
 * @author AceVentura
 *
 */
@Entity
public class ParkingSlotEntity extends PanacheEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(updatable = false, nullable = false)
	private String name;

	@Column
	private boolean isFree;

	@Column(updatable = false, nullable = false)
	private SlotType type;

	@Column(unique = true)
	private String registrationNumber;

	@Column
	private Date startDate;

	/**
	 * Name of the slot
	 * 
	 * @return the name of the slot
	 */
	public String getName() {
		return name;
	}

	/**
	 * Define the name of the slot
	 * 
	 * @param name of the slot
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Primary key of the slot
	 * 
	 * @return the primary key in DB for this slot
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Define the primary key of this slot
	 * 
	 * @param id a unique identifier
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Is this slot empty ?
	 * 
	 * @return true if it is empty
	 */
	public boolean isFree() {
		return isFree;
	}

	/**
	 * Define the actual usage of the slot.
	 * 
	 * @param isFree true if empty
	 */
	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	/**
	 * Get the type of vehicule that can be parked on such slot.
	 * 
	 * @return the type of the slot
	 */
	public SlotType getType() {
		return type;
	}

	/**
	 * Define the type of the slot
	 * 
	 * @param type of the slot
	 */
	public void setType(SlotType type) {
		this.type = type;
	}

	/**
	 * What is the registration number of the car parked on this slot (if slot is
	 * not empty).
	 * 
	 * @return registration number of the car
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	/**
	 * Define the registration number of the car parked on this slot
	 * 
	 * @param registrationNumber the registration number
	 */
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	/**
	 * When did the car parked?
	 * 
	 * @return the date when the car parked
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Define when the car parked
	 * 
	 * @param startDate the date when the car parked
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Utility method to convert back to the core-api model.
	 * 
	 * @return a converted version of the Parking slot entity
	 */
	public ParkingSlot toParkingSlot() {
		ParkingSlot parkingSlot = new ParkingSlot();
		parkingSlot.setName(this.name);
		parkingSlot.setFree(this.isFree);
		parkingSlot.setRegistrationNumber(this.registrationNumber);
		parkingSlot.setStartDate(this.startDate);
		parkingSlot.setType(this.type);
		return parkingSlot;
	}

	/**
	 * find the first available slot of a given type
	 * 
	 * @param slotType the type of slot searched.
	 * @return a free slot or null if there is exhaustion of such slot type
	 */
	public static ParkingSlotEntity findFreeSlotByType(SlotType slotType) {
		return find("type =?1 and isFree=true", slotType).firstResult();

	}

}
