package com.likeaglove.core.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import com.likeaglove.core.model.ParkingSlot;
import com.likeaglove.core.model.SlotType;

class RangeParkingSlotTest {

	@Test
	void testHasFreeSlot() {
		RangeParkingSlot underTestRangeParkingSlot = new RangeParkingSlot(SlotType.Electric_20, 1);
		assertTrue(underTestRangeParkingSlot.hasFreeSlot());
		underTestRangeParkingSlot.parkCar("123");
		assertFalse(underTestRangeParkingSlot.hasFreeSlot());
		underTestRangeParkingSlot.unparkCar("123");
		assertTrue(underTestRangeParkingSlot.hasFreeSlot());
	}

	@Test
	void testHasCar() {
		RangeParkingSlot underTestRangeParkingSlot = new RangeParkingSlot(SlotType.Electric_20, 1);
		assertFalse(underTestRangeParkingSlot.hasCar("123"));
		underTestRangeParkingSlot.parkCar("123");
		assertTrue(underTestRangeParkingSlot.hasCar("123"));
	}

	@Test
	void testParkCar() {
		RangeParkingSlot underTestRangeParkingSlot = new RangeParkingSlot(SlotType.Electric_20, 2);
		ParkingSlot slot = underTestRangeParkingSlot.parkCar("123");
		assertFalse(slot.isFree());
		assertEquals(SlotType.Electric_20, slot.getType());
		assertEquals("123", slot.getRegistrationNumber());
		assertNotNull(slot.getStartDate());
		assertNull(underTestRangeParkingSlot.parkCar("123"));
		assertNotNull(underTestRangeParkingSlot.parkCar("456"));
		// At this stage there is no more slot
		assertNull(underTestRangeParkingSlot.parkCar("789"));
	}

	@Test
	void testUnparkCar() {
		RangeParkingSlot underTestRangeParkingSlot = new RangeParkingSlot(SlotType.Electric_20, 2);
		ParkingSlot slot = underTestRangeParkingSlot.parkCar("123");
		assertFalse(slot.isFree());
		underTestRangeParkingSlot.parkCar("456");
		assertFalse(underTestRangeParkingSlot.hasFreeSlot());
		Integer bill = underTestRangeParkingSlot.unparkCar("123");
		assertNotNull(bill);
		assertTrue(slot.isFree());
		assertTrue(underTestRangeParkingSlot.hasFreeSlot());
		assertNull(underTestRangeParkingSlot.unparkCar("789"));
	}

	@Test
	void testGetSlots() {
		RangeParkingSlot underTestRangeParkingSlot = new RangeParkingSlot(SlotType.Electric_20, 2);
		assertEquals(2, underTestRangeParkingSlot.getSlots().size());
		underTestRangeParkingSlot.parkCar("123");
		assertEquals(2, underTestRangeParkingSlot.getSlots().size());
	}

	/**
	 * This is to stress test. Inspired from
	 * https://www.yegor256.com/2018/03/27/how-to-test-thread-safety.html
	 */
	@Test
	void testThreadSafety() throws InterruptedException, ExecutionException {
		RangeParkingSlot underTestRangeParkingSlot = new RangeParkingSlot(SlotType.Electric_20, 5);
		int threads = 1000;
		// Latch made to increase the chance to have parallel run of the threads
		CountDownLatch latchPark = new CountDownLatch(1);
		CountDownLatch latchLeave = new CountDownLatch(1);
		AtomicBoolean runningPark = new AtomicBoolean();
		AtomicBoolean runningLeave = new AtomicBoolean();
		AtomicInteger overlapsPark = new AtomicInteger(0);
		AtomicInteger overlapsLeave = new AtomicInteger(0);
		Collection<Future<ParkingSlot>> futuresForPark = new ArrayList<>(threads);
		Collection<Future<Integer>> futuresForLeave = new ArrayList<>(threads);
		ExecutorService serviceToPark = Executors.newFixedThreadPool(threads);
		ExecutorService serviceToLeave = Executors.newFixedThreadPool(threads);
		for (int t = 0; t < 1000 * threads; ++t) {
			// Only 2 cars will try to use the parking
			final String registration = String.format("Registration #%d", t % 2);
			futuresForPark.add(serviceToPark.submit(() -> {
				// Await for the release of the latch
				latchPark.await();
				if (runningPark.get()) {
					overlapsPark.incrementAndGet();
				}
				runningPark.set(true);
				ParkingSlot slot = underTestRangeParkingSlot.parkCar(registration);
				runningPark.set(false);
				return slot;
			}));
			futuresForLeave.add(serviceToLeave.submit(() -> {
				// Await for the release of the latch
				latchLeave.await();
				if (runningLeave.get()) {
					overlapsLeave.incrementAndGet();
				}
				runningLeave.set(true);
				Integer amount = underTestRangeParkingSlot.unparkCar(registration);
				runningLeave.set(false);
				return amount;
			}));
		}
		// release the pack !
		latchPark.countDown();
		latchLeave.countDown();
		// Ensure the completion of the future by calling the get on all of them
		Set<ParkingSlot> slots = new HashSet<>();
		for (Future<ParkingSlot> f : futuresForPark) {
			slots.add(f.get());
		}
		Set<Integer> amounts = new HashSet<>();
		for (Future<Integer> f : futuresForLeave) {
			amounts.add(f.get());
		}
		// Validate there was a bit of concurrency
		assertTrue(overlapsPark.get() > 0, "No overlaps or car park: " + overlapsPark.get());
		assertTrue(overlapsLeave.get() > 0, "No overlaps or car unpark: " + overlapsLeave.get());
	}

}
