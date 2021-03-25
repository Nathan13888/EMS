package net.pdsb.nathan13888.ems.types;

public class FullTimeEmployee extends EmployeeInfo {

	public double yearlySalary;

	// for testing
	public FullTimeEmployee(int eN) {
		this(eN, "first", "last", Gender.OTHER);
	}

	public FullTimeEmployee(int eN, String fN, String lN, Gender g) {
		super(eN, fN, lN, g);
		this.type = EmployeeType.FULLTIME;
	}

	@Override
	public double calcAnnualGrossIncome() {
		return yearlySalary * (1 - deductionsRate);
	}
}
