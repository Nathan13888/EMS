package net.pdsb.nathan13888.ems.types;

public class EmployeeInfo {

	public int empNumber;
	public String firstName;
	public String lastName;
	public String email;
	public Gender gender;
	public Address address;
	public long homePhone, businessPhone;
	public double deductionsRate; // decimal percentage
	public String notes = "";

	public EmployeeType type;

	public EmployeeInfo(int eN, String fN, String lN, Gender g) {
		this.empNumber = eN;
		this.firstName = fN;
		this.lastName = lN;
		this.gender = g;
	}

	public double calcAnnualGrossIncome() {
		return 0;
	}
}
