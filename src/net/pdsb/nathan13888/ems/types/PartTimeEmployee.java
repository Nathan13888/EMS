package net.pdsb.nathan13888.ems.types;

public class PartTimeEmployee extends EmployeeInfo {

	public double hourlyWage, hoursPerWeek, weeksPerYear;

	// for testing
	public PartTimeEmployee(int eN) {
		this(eN, "first", "last", Gender.OTHER);
	}

	public PartTimeEmployee(int eN, String fN, String lN, Gender g) {
		super(eN, fN, lN, g);
		this.type = EmployeeType.PARTTIME;
	}

	@Override
	public double calcAnnualGrossIncome() {
		return (hourlyWage * hoursPerWeek * weeksPerYear) * (1 - deductionsRate);
	}
}
