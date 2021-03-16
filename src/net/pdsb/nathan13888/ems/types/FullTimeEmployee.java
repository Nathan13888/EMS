package net.pdsb.nathan13888.ems.types;

public class FullTimeEmployee extends EmployeeInfo {

	protected double yearlySalary;

	// for testing
	public FullTimeEmployee(int eN) {
		this(eN, "first", "last", "email@email.com", Gender.OTHER, "unknown", 0.05, 100000.0);
	}

	public FullTimeEmployee(int eN, String fN, String lN, String email, Gender g, String wL, double dR, double sal) {
		super(eN, fN, lN, email, g, wL, dR);
		this.yearlySalary = sal;
		this.type = EmployeeType.FULLTIME;
	}

	@Override
	public double calcAnnualGrossIncome() {
		return yearlySalary * (1 - deductionsRate);
	}
}
