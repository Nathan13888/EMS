package net.pdsb.nathan13888.types;

public class EmployeeInfo {

	public int empNumber;
	public String firstName;
	public String lastName;
	public String email;
	public Gender gender;
	public String location;
	public double deductionsRate; // decimal percentage

	public EmployeeType type;

	public EmployeeInfo(int eN, String fN, String lN, String email, Gender g, String wL, double dR) {
		this.empNumber = eN;
		this.firstName = fN;
		this.lastName = lN;
		this.email = email;
		this.gender = g;
		this.location = wL;
		this.deductionsRate = dR;
	}

	public double calcAnnualGrossIncome() {
		return 0;
	}
}
