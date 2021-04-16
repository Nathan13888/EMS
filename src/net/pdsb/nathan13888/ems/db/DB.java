package net.pdsb.nathan13888.ems.db;

import java.util.Random;

import org.eclipse.jface.dialogs.MessageDialog;

import net.pdsb.nathan13888.ems.Config;
import net.pdsb.nathan13888.ems.Main;
import net.pdsb.nathan13888.ems.hashtable.MyHashTable;
import net.pdsb.nathan13888.ems.types.Address;
import net.pdsb.nathan13888.ems.types.EmployeeInfo;
import net.pdsb.nathan13888.ems.types.FullTimeEmployee;
import net.pdsb.nathan13888.ems.types.Gender;
import net.pdsb.nathan13888.ems.types.PartTimeEmployee;

public class DB {

	public static MyHashTable table = new MyHashTable(Config.BUCKETS);

	public static boolean MODIFIED = false;

	public static void generateRandomData() {
		String[] first = { "John", "Bob", "Daniel", "Tom", "Jack", "Robert", "Michael", "William", "Nathan", "David",
				"Christopher", "Mark", "Kevin", "Eric", "Lisa", "Linda", "Elizabeth", "Karen", "Helen", "Sharon",
				"Jessica", "Angela", "Anna", "Christine", "Alice", "Frances" };

		String[] last = { "Green", "White", "Black", "Brown", "Lee", "Smith", "Allen", "Morris", "Taylor", "Bell",
				"Smith", "Light", "Roger", "Ford", "Scott", "Parker" };

		String[] addresses = { "The Statue of Liberty in New York, USA", "The Eiffel Tower in Paris, France",
				"Louvre Museum, Rue de Rivoli, Paris, France", "St. Basil’s Cathedral in Moscow, Russia",
				"Blue Domed Church in Santorini, Greece", "CHJC+Q4 Bohaizhen, Huairou District, Beijing, China",
				"The Great Sphinx at Giza, Egypt", "Palace of Versailles",
				"1600 Pennsylvania Avenue NW, Washington, DC 20500, United States", "The Taj Mahal in Agra, India",
				"3225 Erindale Station Rd, Mississauga, ON L5C 1Y5" };

		int[] phoneCodes = { 647, 416, 905, 226, 249, 289, 705, 907 };

		Random r = new Random();
		for (int i = 0; i < r.nextInt(100) + 50; i++) {
			int num = r.nextInt(900000) + 100000;
			String f = first[r.nextInt(first.length)];
			String l = last[r.nextInt(last.length)];
			String email = (f + l).toLowerCase() + "@email.com";
			Gender g = r.nextInt(2) == 0 ? Gender.MALE : Gender.FEMALE;
			String loc = addresses[r.nextInt(addresses.length)];
			long hp = phoneCodes[r.nextInt(phoneCodes.length)] * 1000000 + r.nextInt(10000000);
			long bp = phoneCodes[r.nextInt(phoneCodes.length)] * 1000000 + r.nextInt(10000000);
			double dR = Math.round(r.nextDouble() * 100.0) / 100.0;
			double wage = Math.round(((r.nextInt(50) + 50) * 1000) * 10000.0) / 10000.0;
			double hourlyWage = r.nextInt(20) + 14;
			double hoursPerWeek = r.nextInt(16) + 30;
			double weeksPerYear = 52 - r.nextInt(5);
			FullTimeEmployee fte = new FullTimeEmployee(num, f, l, g);
			fte.yearlySalary = wage;
			PartTimeEmployee pte = new PartTimeEmployee(num, f, l, g);
			pte.hourlyWage = hourlyWage;
			pte.hoursPerWeek = hoursPerWeek;
			pte.weeksPerYear = weeksPerYear;
			EmployeeInfo info = r.nextInt(2) == 0 ? fte : pte;
			info.email = email;
			info.address = new Address(loc);
			info.deductionsRate = dR;
			info.homePhone = hp;
			info.businessPhone = bp;
			info.notes = "Some information about " + f + " " + l + "...\n\n NEW LINES WORK TOO!";

			table.add(info);
		}
	}

	public static void add(EmployeeInfo info) {
		if (info == null) {
			System.err.println("NULL was attempted to be added to the database ;(");
			return;
		} else if (table.isInTable(info.empNumber)) {
			System.out.println("Removing employee " + info.empNumber + " " + info.firstName + " " + info.lastName);
			EmployeeInfo ret = table.pop(info.empNumber);
			if (ret != null && ret.empNumber == info.empNumber) {
				//
			} else {
				System.err.println("PROBLEM WHEN REMOVING EMPLOYEE " + info);
			}
		}
		MODIFIED = true;
		System.out.println("Adding employee " + info.empNumber + " " + info.firstName + " " + info.lastName);
		table.add(info);
		if (table.isInTable(info.empNumber))
			MessageDialog.openInformation(Main.window.shell, "Information",
					"Employee " + info.empNumber + " (" + info.firstName + " " + info.lastName + ") was changed.");
		else
			MessageDialog.openError(Main.window.shell, "Error", "Employee " + info.empNumber + " could not be added.");
	}

	public static void update(int num, EmployeeInfo info) {
		if (remove(num) == null)
			System.err.println("There has been a problem removing employee " + num);
		add(info);
	}

	public static EmployeeInfo remove(int num) {
		System.out.println("Attempting to remove employee " + num);
		EmployeeInfo res = table.pop(num);
		if (res != null)
			MODIFIED = true;
		return res;
	}

	public static EmployeeInfo query(int num) {
		return table.get(num);
	}

}
