package net.pdsb.nathan13888.ems.ui.forms;

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

import net.pdsb.nathan13888.ems.db.DB;
import net.pdsb.nathan13888.ems.types.EmployeeInfo;

public class EmployeeInfoForm extends Wizard {

	private ISelection selection;

	public EmployeeInfoForm() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		this.addPage(new InfoPage(selection));
		this.addPage(new SalaryPage(selection));
	}

	@Override
	public boolean performFinish() {
		EmployeeInfo info = null;

		DB.add(info);

		return true;
	}
}

class InfoPage extends WizardPage {

	private ISelection selection;

	private Label label;
	private GridData gd;
	private Text firstNameText;
	private Text lastNameText;
	private Text emailText;
	private Text homeAddressText;
	private Text homePhoneText;
	private Text businessPhoneText;
	private Text notesText;

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

		// dialogChanged();
		setControl(container);
	}

	private class ModifyDialogEvent implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent arg0) {
			dialogChanged();
		}
	}

	private void dialogChanged() { // TODO: sanitize input: if input in class is invalid, then update status
//		updateStatus("");

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

	private Button fteButton, pteButton;
	private Text annualSalaryText;
	private Text hourlyWageText;
	private Text hpwText;
	private Text wpyText;
	private Text deductionsText;

	public SalaryPage(ISelection selection) { // TODO: employee image
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
