package net.pdsb.nathan13888.ems.ui.forms;

import java.util.Random;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.pdsb.nathan13888.ems.Main;
import net.pdsb.nathan13888.ems.db.DB;
import net.pdsb.nathan13888.ems.types.Address;
import net.pdsb.nathan13888.ems.types.EmployeeInfo;
import net.pdsb.nathan13888.ems.types.FullTimeEmployee;
import net.pdsb.nathan13888.ems.types.Gender;
import net.pdsb.nathan13888.ems.types.PartTimeEmployee;

public class EmployeeInfoForm extends Wizard {

	private ISelection selection;
	private InfoPage infoPage;
	private SalaryPage salaryPage;

	public EmployeeInfoForm() {
		super();
		setNeedsProgressMonitor(true);
	}

	private boolean editMode = false;
	private EmployeeInfo info;

	public EmployeeInfoForm(EmployeeInfo info) {
		this();
		this.setWindowTitle("Edit employee " + info.empNumber);
		this.info = info;
		this.editMode = true;
	}

	@Override
	public void addPages() {
		if (this.editMode) {
			infoPage = new InfoPage(selection, info);
			salaryPage = new SalaryPage(selection, info);
		} else {
			infoPage = new InfoPage(selection);
			salaryPage = new SalaryPage(selection);
		}
		this.addPage(infoPage);
		this.addPage(salaryPage);
	}

	@Override
	public boolean performFinish() {
		System.out.println("Creating New Employee...");

		EmployeeInfo info = null;
		int empNumber = infoPage.randomEmpNumber.getSelection() ? (new Random().nextInt(900000) + 100000)
				: Integer.parseInt(infoPage.empNumberText.getText());
		String firstName = infoPage.firstNameText.getText();
		String lastName = infoPage.lastNameText.getText();
		String email = infoPage.emailText.getText();
		Gender gender;
		if (infoPage.maleButton.getSelection())
			gender = Gender.MALE;
		else if (infoPage.femaleButton.getSelection())
			gender = Gender.FEMALE;
		else
			gender = Gender.OTHER;
		Address address = new Address(infoPage.homeAddressText.getText());
		long homePhone = Long.parseLong(infoPage.homePhoneText.getText());
		long businessPhone = Long.parseLong(infoPage.businessPhoneText.getText());
		double deductionsRate = Double.parseDouble(salaryPage.deductionsText.getText());
		String notes = infoPage.notesText.getText();

		if (salaryPage.fteButton.getSelection()) {
			info = new FullTimeEmployee(empNumber, firstName, lastName, gender);
			((FullTimeEmployee) info).yearlySalary = Double.parseDouble(salaryPage.annualSalaryText.getText());
		} else if (salaryPage.pteButton.getSelection()) {
			info = new PartTimeEmployee(empNumber, firstName, lastName, gender);
			((PartTimeEmployee) info).hourlyWage = Double.parseDouble(salaryPage.hourlyWageText.getText());
			((PartTimeEmployee) info).hoursPerWeek = Double.parseDouble(salaryPage.hpwText.getText());
			((PartTimeEmployee) info).weeksPerYear = Double.parseDouble(salaryPage.wpyText.getText());
		} else {
			System.err.println(new Error("WHAT ON EARTH IS WRONG WITH YOUR SANITIZING"));
			return false;
		}

		info.email = email;
		info.address = address;
		info.homePhone = homePhone;
		info.businessPhone = businessPhone;
		info.deductionsRate = deductionsRate;
		info.notes = notes;

		if (this.editMode)
			DB.update(this.info.empNumber, info);
		else
			DB.add(info);
		Main.window.table.reload();

		return true;
	}
}

class InfoPage extends WizardPage {

	private ISelection selection;

	private Label label;
	private GridData gd;
	private Composite box;
	public Text empNumberText;
	public Button randomEmpNumber;
	public Text firstNameText;
	public Text lastNameText;
	public Text emailText;
	public Button maleButton, femaleButton, otherButton;
	public Text homeAddressText;
	public Text homePhoneText;
	public Text businessPhoneText;
	public Text notesText;

	private boolean editMode = false;
	private EmployeeInfo info;

	public InfoPage(ISelection selection, EmployeeInfo info) {
		this(selection);
		setTitle("Edit Employee " + info.empNumber);
		this.editMode = true;
		this.info = info;
	}

	public InfoPage(ISelection selection) {
		super("newEmployee");
		setTitle("New Employee");
		setDescription("Enter employee info");
		this.selection = selection;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		container.setLayout(layout);

		label = new Label(container, SWT.NULL);
		label.setText("&Employee Number: ");
		box = new Composite(container, SWT.NULL);
		layout = new GridLayout(2, true);
		layout.marginWidth = 0;
		box.setLayout(layout);
		box.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		empNumberText = new Text(box, SWT.BORDER | SWT.SINGLE);
		empNumberText.setLayoutData(gd);
		randomEmpNumber = new Button(box, SWT.CHECK);
		randomEmpNumber.setText("Random");
		randomEmpNumber.addSelectionListener(new SelectionButtonEvent());
		randomEmpNumber.setSelection(true);

		label = new Label(container, SWT.NULL);
		label.setText("&First Name: ");
		firstNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		firstNameText.setLayoutData(gd);
		firstNameText.addModifyListener(new ModifyDialogEvent());

		label = new Label(container, SWT.NULL);
		label.setText("&Last Name: ");
		lastNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		lastNameText.setLayoutData(gd);
		lastNameText.addModifyListener(new ModifyDialogEvent());

		label = new Label(container, SWT.NULL);
		label.setText("&Email: ");
		emailText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		emailText.setLayoutData(gd);
		emailText.addModifyListener(new ModifyDialogEvent());

		label = new Label(container, SWT.NULL);
		label.setText("&Gender: ");
		box = new Composite(container, SWT.NULL);
		layout = new GridLayout(3, true);
		layout.marginWidth = 0;
		box.setLayout(layout);
		box.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		maleButton = new Button(box, SWT.RADIO);
		maleButton.setText("Male");
		femaleButton = new Button(box, SWT.RADIO);
		femaleButton.setText("Female");
		otherButton = new Button(box, SWT.RADIO);
		otherButton.setText("Other");

		createLine(container, layout.numColumns);

		label = new Label(container, SWT.NULL);
		label.setText("&Home Address: ");
		homeAddressText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		homeAddressText.setLayoutData(gd);

		label = new Label(container, SWT.NULL);
		label.setText("&Home Phone: ");
		homePhoneText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		homePhoneText.setLayoutData(gd);

		label = new Label(container, SWT.NULL);
		label.setText("&Business Phone: ");
		businessPhoneText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		businessPhoneText.setLayoutData(gd);

		createLine(container, layout.numColumns);

		label = new Label(container, SWT.NULL);
		label.setText("&Notes: ");
		label.setLayoutData(new GridData(SWT.DEFAULT, SWT.TOP, false, false));
		notesText = new Text(container, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 200;
		notesText.setLayoutData(gd);

		if (this.editMode) {
			empNumberText.setText(String.valueOf(info.empNumber));
			randomEmpNumber.setSelection(false);
			randomEmpNumber.setEnabled(false);
			firstNameText.setText(info.firstName);
			lastNameText.setText(info.lastName);
			emailText.setText(info.email);
			switch (info.gender) {
			case MALE:
				maleButton.setSelection(true);
				break;
			case FEMALE:
				femaleButton.setSelection(true);
				break;
			default:
				otherButton.setSelection(true);
			}
			homeAddressText.setText(info.address.getAddress());
			homePhoneText.setText(String.valueOf(info.homePhone));
			businessPhoneText.setText(String.valueOf(info.businessPhone));
			notesText.setText(info.notes);
		}

		dialogChanged();
		setControl(container);
	}

	private class ModifyDialogEvent implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent arg0) {
			dialogChanged();
		}
	}

	private class SelectionButtonEvent implements SelectionListener {
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			this.widgetSelected(arg0);
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			dialogChanged();
		}
	}

	private void dialogChanged() { // TODO: sanitize input: if input in class is invalid, then update status
		if (randomEmpNumber.getSelection())
			empNumberText.setEnabled(false);
		else
			empNumberText.setEnabled(true);
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	private void createLine(Composite parent, int ncol) {
		Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.BOLD);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = ncol;
		line.setLayoutData(gridData);
	}

}

class SalaryPage extends WizardPage {

	private ISelection selection;

	private Label label;
	private GridData gd;

	public Button fteButton, pteButton;
	public Text annualSalaryText;
	public Text hourlyWageText;
	public Text hpwText;
	public Text wpyText;
	public Text deductionsText;

	private boolean editMode = false;
	private EmployeeInfo info;

	public SalaryPage(ISelection selection, EmployeeInfo info) {
		this(selection);
		setTitle("Edit Employee " + info.empNumber);
		this.editMode = true;
		this.info = info;
	}

	public SalaryPage(ISelection selection) { // TODO: employee image
		super("newEmployee");
		setTitle("New Employee");
		setDescription("Enter salary info");
		this.selection = selection;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		container.setLayout(layout);

		label = new Label(container, SWT.NULL);
		label.setText("&Type: ");
		Composite box = new Composite(container, SWT.NULL);
		layout = new GridLayout(2, true);
		layout.marginWidth = 0;
		box.setLayout(layout);
		box.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fteButton = new Button(box, SWT.RADIO);
		fteButton.setText("FTE");
		fteButton.addSelectionListener(new SelectionButtonEvent());
		pteButton = new Button(box, SWT.RADIO);
		pteButton.setText("PTE");
		pteButton.addSelectionListener(new SelectionButtonEvent());

		label = new Label(container, SWT.NULL);
		label.setText("&Annual Salary: ");
		annualSalaryText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		annualSalaryText.setLayoutData(gd);
		annualSalaryText.addModifyListener(new ModifyDialogEvent());

		label = new Label(container, SWT.NULL);
		label.setText("&Hourly Wage: ");
		hourlyWageText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		hourlyWageText.setLayoutData(gd);
		hourlyWageText.addModifyListener(new ModifyDialogEvent());

		label = new Label(container, SWT.NULL);
		label.setText("&Hours Per Week: ");
		hpwText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		hpwText.setLayoutData(gd);

		label = new Label(container, SWT.NULL);
		label.setText("&Weeks Per Year: ");
		wpyText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		wpyText.setLayoutData(gd);

		createLine(container, layout.numColumns);

		label = new Label(container, SWT.NULL);
		label.setText("&Deductions Rate: ");
		deductionsText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		deductionsText.setLayoutData(gd);
		deductionsText.addModifyListener(new ModifyDialogEvent());

		if (this.editMode) {
			if (info instanceof FullTimeEmployee) {
				fteButton.setSelection(true);
				annualSalaryText.setText(String.valueOf(((FullTimeEmployee) info).yearlySalary));
			} else if (info instanceof PartTimeEmployee) {
				pteButton.setSelection(true);
				hourlyWageText.setText(String.valueOf(((PartTimeEmployee) info).hourlyWage));
				hpwText.setText(String.valueOf(((PartTimeEmployee) info).hoursPerWeek));
				wpyText.setText(String.valueOf(((PartTimeEmployee) info).weeksPerYear));
			}
			deductionsText.setText(String.valueOf(info.deductionsRate));
		}

		dialogChanged();
		setControl(container);
	}

	private class ModifyDialogEvent implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent arg0) {
			dialogChanged();
		}
	}

	private class SelectionButtonEvent implements SelectionListener {
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			this.widgetSelected(arg0);
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			dialogChanged();
		}
	}

	private void dialogChanged() { // TODO: sanitize input: if input in class is invalid, then update status
		if (fteButton.getSelection()) {
			annualSalaryText.setEnabled(true);
			hourlyWageText.setEnabled(false);
			hpwText.setEnabled(false);
			wpyText.setEnabled(false);
		} else if (pteButton.getSelection()) {
			annualSalaryText.setEnabled(false);
			hourlyWageText.setEnabled(true);
			hpwText.setEnabled(true);
			wpyText.setEnabled(true);
		} else {
			annualSalaryText.setEnabled(false);
			hourlyWageText.setEnabled(false);
			hpwText.setEnabled(false);
			wpyText.setEnabled(false);
		}

		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	private void createLine(Composite parent, int ncol) {
		Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.BOLD);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = ncol;
		line.setLayoutData(gridData);
	}

}
