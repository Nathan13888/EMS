package net.pdsb.nathan13888.ems.db;

import java.util.Random;

import net.pdsb.nathan13888.ems.Config;
import net.pdsb.nathan13888.ems.hashtable.MyHashTable;
import net.pdsb.nathan13888.ems.types.Address;
import net.pdsb.nathan13888.ems.types.EmployeeInfo;
import net.pdsb.nathan13888.ems.types.FullTimeEmployee;
import net.pdsb.nathan13888.ems.types.Gender;
import net.pdsb.nathan13888.ems.types.PartTimeEmployee;

public class DB {

	public static MyHashTable table = new MyHashTable(Config.BUCKETS);

	public static void generateRandomData() {
		String[] first = { "John", "Bob", "Daniel", "Tom", "Jack" };

		String[] last = { "Green", "White", "Black", "Light", "Roger" };

		Random r = new Random();
		for (int i = 0; i < r.nextInt(100) + 50; i++) {
			int num = r.nextInt(900000) + 100000;
			String f = first[r.nextInt(first.length)];
			String l = last[r.nextInt(last.length)];
			String email = (f + l).toLowerCase() + "@email.com";
			Gender g = r.nextInt(2) == 0 ? Gender.MALE : Gender.FEMALE;
			String loc = "some location";
			double dR = r.nextDouble();
			double wage = (r.nextInt(50) + 50) * 1000;
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
		System.out.println("Adding employee " + info.empNumber + " " + info.firstName + " " + info.lastName);
		table.add(info);
	}

}
