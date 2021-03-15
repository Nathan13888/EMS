package net.pdsb.nathan13888.types;

public class PartTimeEmployee extends EmployeeInfo {

	private double hourlyWage, hoursPerWeek, weeksPerYear;

	// for testing
	public PartTimeEmployee(int eN) {
		this(eN, "first", "last", "email@email.com", Gender.OTHER, "unknown", 0.05, 20.0, 45.0, 50.0);
	}

	public PartTimeEmployee(int eN, String fN, String lN, String email, Gender g, String wL, double dR, double wage,
			double hpw, double wpy) {
		super(eN, fN, lN, email, g, wL, dR);
		this.hourlyWage = wage;
		this.hoursPerWeek = hpw;
		this.weeksPerYear = wpy;
		this.type = EmployeeType.PARTTIME;
	}

	@Override
	public double calcAnnualGrossIncome() {
		return (hourlyWage * hoursPerWeek * weeksPerYear) * (1 - deductionsRate);
	}
}
