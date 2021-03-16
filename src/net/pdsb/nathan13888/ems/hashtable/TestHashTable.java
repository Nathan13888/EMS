package net.pdsb.nathan13888.hashtable;

import java.util.Random;

import net.pdsb.nathan13888.types.EmployeeInfo;
import net.pdsb.nathan13888.types.FullTimeEmployee;
import net.pdsb.nathan13888.types.PartTimeEmployee;

public class TestHashTable {

	public static void main(String[] args) {
		new TestHashTable();
	}

	public TestHashTable() {
		MyHashTable ht = null;

		System.out.println("***Testing different numbers of buckets and verifying...");
		for (int i = 0; i <= 10; ++i) {
			ht = new MyHashTable(i);
			if (ht.buckets.length != i)
				System.out.println("ERROR: Problems with creating hash table with " + i + " buckets!");
			else
				System.out.println("No problems with creating hash table with " + i + " buckets :)");
		}

		System.out.println("***Testing adding students");
		for (int i = 0; i <= 100; i++) {
			testAdd(ht, i);
			if (!testExists(ht, i))
				System.out.println("ERROR: Why isn't student with student number " + i + " in the table???");
			else
				System.out.println("Student with student number " + i + " was successfully added :)");
		}

		System.out.println("***Testing removing students");
		for (int i = 49; i >= 0; i--) {
			System.out.println(testRemove(ht, i).empNumber);
			if (testExists(ht, i))
				System.out.println("ERROR: Why is student with student number " + i + " still in the table???");
			else
				System.out.println("Student with student number " + i + " was successfully removed :)");
		}

		System.out.println("***Randomly reading information about students");
		for (int i = 0; i < 25; ++i) {
			int rand = new Random().nextInt(50) + 50;
			System.out.println("Finding student number " + rand);

			EmployeeInfo s = testGet(ht, rand);
			if (s.empNumber == rand)
				System.out.println("Successfully found student with student number " + rand);
			else
				System.out.println("ERRROR: Can not find student with student number " + rand);
		}

		ht.displayTable();
	}

	void testAdd(MyHashTable ht, int i) {
		ht.addToTable(newEmployee(i));
	}

	EmployeeInfo testRemove(MyHashTable ht, int i) {
		return ht.removeFromTable(i);
	}

	EmployeeInfo testGet(MyHashTable ht, int i) {
		return ht.getFromTable(i);
	}

	boolean testExists(MyHashTable ht, int i) {
		return ht.isInTable(i);
	}

	EmployeeInfo newEmployee(int i) {
		boolean rand = new Random().nextBoolean();
		return rand ? new PartTimeEmployee(i) : new FullTimeEmployee(i);
	}

}
