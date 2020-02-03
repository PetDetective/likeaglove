package com.likeaglove.core.configuration;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import com.likeaglove.core.model.PricingPolicy;
import com.likeaglove.core.model.SimplePricingPolicy;
import com.likeaglove.core.model.SlotType;

/**
 * This is to describe the parking configuration (number of slots; nature;
 * pricing policy)
 * 
 * @author AceVentura
 *
 */
public class ParkingConfiguration {

	private PricingPolicy pricingPolicy;
	private Map<SlotType, Integer> rangeSlotByType;

	private ParkingConfiguration() {
	}

	/**
	 * Retrieve the pricing policy.
	 * 
	 * @return the pricing policy
	 */
	public PricingPolicy getPricingPolicy() {
		return pricingPolicy;
	}

	/**
	 * Get the range discriminated by Slot type
	 * 
	 * @return the distinct slot ranges
	 */
	public Map<SlotType, Integer> getRangeSlotByType() {
		return Collections.unmodifiableMap(rangeSlotByType);
	}

	/**
	 * Utility method to provide the pricing policy
	 * 
	 * @return a builder to provide the pricing policy
	 */
	public static PricePolicyBuilder newBuilder() {
		return new Builder();
	}

	/**
	 * Describes how a builder can provide the configuration of the slots
	 * 
	 * @author AceVentura
	 *
	 */
	public interface SlotsBuilder {

		/**
		 * Add slots to the parking
		 * 
		 * @return a utility to describe the slots nature
		 */
		RangeSlotBuilder addSlots();
	}

	/**
	 * Describes how a builder can provide the configuration of the slots
	 * 
	 * @author AceVentura
	 *
	 */
	public interface PricePolicyBuilder {

		/**
		 * Provide a simple price policy: amount = initialCharge + hourlyRate*number
		 * hour
		 * 
		 * @param initialCharge the initial fixed price
		 * @param hourlyRate    the charge per hour
		 * @return a slot builders to add the slots to the parking configuration
		 */
		SlotsBuilder withSimplePricePolicy(int initialCharge, int hourlyRate);

		/**
		 * Provide your own {@link PricingPolicy}
		 * 
		 * @param pricingPolicy a custom pricing policy
		 * @return a slot builders to add the slots to the parking configuration
		 */
		SlotsBuilder withPricingPolicy(PricingPolicy pricingPolicy);
	}

	/**
	 * A utility to construct a valid {@link ParkingConfiguration}
	 * 
	 * @author AceVentura
	 *
	 */
	public static class Builder implements PricePolicyBuilder, SlotsBuilder {
		private ParkingConfiguration parkingToBuild;

		private Builder() {
			parkingToBuild = new ParkingConfiguration();
		}

		/**
		 * Finalize the construction of the parking
		 * 
		 * @return a valid {@link ParkingConfiguration}
		 */
		public ParkingConfiguration build() {
			ParkingConfiguration builtParking = parkingToBuild;
			parkingToBuild = new ParkingConfiguration();
			return builtParking;
		}

		@Override
		public SlotsBuilder withSimplePricePolicy(int initialCharge, int hourlyRate) {
			if (initialCharge < 0) {
				throw new IllegalArgumentException(
						"the initialCharge cannot be negative, value specified: " + initialCharge);
			}
			if (hourlyRate < 0) {
				throw new IllegalArgumentException(
						"the hourlyRate cannot be negative, value specified: " + initialCharge);
			}
			this.parkingToBuild.pricingPolicy = new SimplePricingPolicy(initialCharge, hourlyRate);
			return this;
		}

		@Override
		public SlotsBuilder withPricingPolicy(PricingPolicy pricingPolicy) {
			Objects.requireNonNull(pricingPolicy);
			this.parkingToBuild.pricingPolicy = pricingPolicy;
			return this;
		}

		@Override
		public RangeSlotBuilder addSlots() {
			return RangeSlotBuilder.newBuilder(this);
		}

		/**
		 * Add the slots to the parking
		 * 
		 * @param rangeSlotByType the slots configuration
		 * @return a builder to configure the parking
		 */
		public Builder withRangeSlots(Map<SlotType, Integer> rangeSlotByType) {
			this.parkingToBuild.rangeSlotByType = rangeSlotByType;
			return this;
		}

	}

}
