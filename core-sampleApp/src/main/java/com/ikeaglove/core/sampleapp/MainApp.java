package com.ikeaglove.core.sampleapp;

import java.util.Comparator;
import java.util.List;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import com.likeaglove.core.ParkingManagerServiceLoader;
import com.likeaglove.core.api.ParkingManager;
import com.likeaglove.core.configuration.ParkingConfiguration;
import com.likeaglove.core.model.ParkingSlot;
import com.likeaglove.core.model.SlotType;

/**
 * A sample application to demonstrate how to use the basic in memory
 * implementation of the ParkingManager
 * 
 * @author AceVentura
 *
 */
public class MainApp {

	/**
	 * To start the application
	 * 
	 * @param args default java signature
	 */
	public static void main(String[] args) {
		ParkingConfiguration parkingConfiguration = ParkingConfiguration.newBuilder().withSimplePricePolicy(10, 3)
				.addSlots().addRange(SlotType.Standard, 5).addRange(SlotType.Electric_20, 8)
				.addRange(SlotType.Electric_50, 8).rangeSlotsDone().build();
		ParkingManager parkingManager = ParkingManagerServiceLoader.provider().create(parkingConfiguration);
		TextIO textIO = TextIoFactory.getTextIO();
		boolean stayInApp = true;
		do {
			textIO.getTextTerminal().println("=======================================");
			switch (textIO.newEnumInputReader(GeneralMenuActions.class).read("What do you want to do?")) {
			case ExitApplication:
				textIO.getTextTerminal().println("Bye Bye !");
				stayInApp = false;
				break;
			case ParkCar:
				CarType carType = textIO.newEnumInputReader(CarType.class).read("What kind of car to park?");
				String registrationNumber = textIO.newStringInputReader().read("Registration number:");
				parkingManager.findFreeParkingSlotAndPark(carType.toSlotType(), registrationNumber).ifPresentOrElse(
						slot -> textIO.getTextTerminal().println("Car assigned to the slot: " + slot.getName()),
						() -> textIO.getTextTerminal().println(
								"There is no more slot or a car with the same registration number is already parked"));
				break;
			case UnParkCar:
				parkingManager.leaveParkingSlot(textIO.newStringInputReader().read("Registration number:"))
						.ifPresentOrElse(bill -> textIO.getTextTerminal().println("Amount to pay: " + bill.getAmount()),
								() -> textIO.getTextTerminal().println("This car does not exist in the parking"));
				break;
			case DisplaySlotUsage:
				List<ParkingSlot> allSlots = parkingManager.getAllSlots();
				textIO.getTextTerminal().println("+-----------------+------------------+--------+--------------+");
				textIO.getTextTerminal().println("|    Slot Name    |       Type       | Status | Registration |");
				textIO.getTextTerminal().println("+-----------------+------------------+--------+--------------+");
				allSlots.stream().sorted(Comparator.comparing(ParkingSlot::getName))
						.forEach(slot -> textIO.getTextTerminal().println(prettyPrint(slot)));
				textIO.getTextTerminal().println("+-----------------+------------------+--------+--------------+");
				break;

			}
		} while (stayInApp);
		textIO.dispose();
	}

	private static String prettyPrint(ParkingSlot slot) {
		// Slot Name; Slot Type; Free/Occupied; Registration
		String format = "| %-15s | %-16.16s |  %4s  | %-12.12s |";
		return String.format(format, slot.getName(), CarType.fromSlotType(slot.getType()).toString(),
				slot.isFree() ? "Free" : "Busy",
				slot.getRegistrationNumber() == null ? "-" : slot.getRegistrationNumber());

	}
}
