package net.pdsb.nathan13888.ems.hashtable;

import java.util.ArrayList;

import net.pdsb.nathan13888.ems.types.EmployeeInfo;

public class MyHashTable {

	// ATTRIBUTES

	public ArrayList<EmployeeInfo>[] buckets;

	// CONSTRUCTOR

	@SuppressWarnings("unchecked")
	public MyHashTable(int howManyBuckets) { // assuming howManyBuckets is vali
		this.buckets = new ArrayList[howManyBuckets];

		// For each element in the array, instantiate its ArrayList.
		for (int i = 0; i < howManyBuckets; i++) {
			buckets[i] = new ArrayList<EmployeeInfo>();
		}
	}

	// METHODS

	public int calcBucket(int studentNumber) {
		return (studentNumber < 0) ? (calcBucket(studentNumber + buckets.length)) : (studentNumber % buckets.length);
	}

	public void addToTable(EmployeeInfo theStudent) { // Add the student referenced by theStudent to the hash table.
		if (theStudent != null) {
			int targetBucket = calcBucket(theStudent.empNumber);
			// Append that student to the ArrayList for that bucket.
			@SuppressWarnings("unused")
			boolean addStatus = buckets[targetBucket].add(theStudent);
		}

	}

	public EmployeeInfo removeFromTable(int studentNumber) {
		// Remove that student from the hash table and return the reference value for
		// that student.
		if (studentNumber >= 0) {
			ArrayList<EmployeeInfo> bucket = buckets[calcBucket(studentNumber)];
			for (EmployeeInfo student : bucket) {
				if (student.empNumber == studentNumber) {
					bucket.remove(student);
					return student;
				}
			}
		}
		// Return null if that student isn't in the table.
		return null;
	}

	public EmployeeInfo getFromTable(int studentNumber) {
		// Return the reference value for that student, return null if student isn't in
		// the table.
		if (studentNumber >= 0) {
			for (EmployeeInfo student : buckets[calcBucket(studentNumber)])
				if (student.empNumber == studentNumber)
					return student;
		}
		return null;
	}

	public boolean isInTable(int studentNumber) {
		// Return true if that student is in the hash table, false otherwise.
		for (EmployeeInfo student : buckets[calcBucket(studentNumber)])
			if (student.empNumber == studentNumber)
				return true;
		return false;
	}

	public void displayTable() {
		// Walk through the buckets and display the items in each bucket's ArrayList.
		EmployeeInfo currentStudent;
		for (int i = 0; i < buckets.length; i++) {
			System.out.println("Contents for Bucket #" + i);
			// Display the items in this bucket's ArrayList.
			if (buckets[i].size() == 0) {
				System.out.println("\tNo items for this bucket!");
			} else {
				// Get the info for each item in this ArrayList.
				for (int j = 0; j < buckets[i].size(); j++) {
					currentStudent = buckets[i].get(j);
					System.out.println(
							"\t" + currentStudent + " " + currentStudent.empNumber + " " + currentStudent.firstName
									+ " " + currentStudent.lastName + " --> " + currentStudent.calcAnnualGrossIncome());
				}
			}
		}
		System.out.println();
	}

}
