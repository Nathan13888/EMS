package net.pdsb.nathan13888.ems.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import net.pdsb.nathan13888.ems.Main;
import net.pdsb.nathan13888.ems.Utils;
import net.pdsb.nathan13888.ems.db.DB;
import net.pdsb.nathan13888.ems.types.EmployeeInfo;
import net.pdsb.nathan13888.ems.ui.forms.EmployeeInfoForm;

public class InfoWindow {

	private Display display;
	private Shell shell;
	private EmployeeInfo info;
	private GridLayout lay;
	private GridData fullWidthData = new GridData(GridData.FILL_HORIZONTAL);

	public InfoWindow(EmployeeInfo info) {
		if (info == null) {
			System.err.println("InfoWindow was given NULL info to display");
			return;
		}

		this.info = info;

		display = Display.getCurrent();
		shell = new Shell(display, SWT.ON_TOP);
		shell.setText("Employee " + info.empNumber);
		lay = new GridLayout(2, false);
		lay.marginTop = 10;
		lay.marginLeft = 30;
		lay.marginBottom = 30;
		lay.verticalSpacing = 10;
		fullWidthData.horizontalSpan = 2;
		shell.setLayout(lay);
		shell.forceActive();
		shell.forceFocus();
		shell.addListener(SWT.Traverse, new Listener() {
			@Override
			public void handleEvent(Event event) {
				switch (event.detail) {
				case SWT.TRAVERSE_ESCAPE:
					shell.close();
					event.detail = SWT.TRAVERSE_NONE;
					event.doit = false;
					break;
				}
			}
		});
		this.loadWidgets();
//		shell.pack();

//		shell.setBounds(display.getBounds());
		shell.setSize(1200, 900);
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
	}

	public void open() {
		System.out.println("Displaying Information about " + info.empNumber);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
//		display.dispose();
	}

	private void loadWidgets() {
		Label label = new Label(shell, SWT.BOLD);
		label.setText("Employee " + info.empNumber);
		label.setAlignment(SWT.CENTER);
		label.setLayoutData(fullWidthData);

		createLine(shell);

		this.loadInfo();

		createLine(shell);

		Composite buttons = new Composite(shell, SWT.NONE);
		buttons.setLayoutData(fullWidthData);
		FillLayout buttonsLayout = new FillLayout(SWT.HORIZONTAL);
		buttonsLayout.spacing = 10;
		buttons.setLayout(buttonsLayout);
		Button b = new Button(buttons, SWT.PUSH);
		b.setText("Edit Info");
		b.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				EmployeeInfoForm wizard = new EmployeeInfoForm(info);
				WizardDialog dialog = new WizardDialog(Main.window.shell, wizard);
				dialog.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		b = new Button(buttons, SWT.PUSH);
		b.setText("View Address");
		b.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Utils.openWebpage("https://www.google.ca/maps/search/" + Utils.encodeURI(info.address.getAddress()));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		b = new Button(buttons, SWT.PUSH);
		b.setText("Remove Employee");
		b.addSelectionListener(new SelectionListener() {
			@SuppressWarnings("null")
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (!MessageDialog.openConfirm(shell, "Confirm",
						"Are you sure you want to remove employee " + info.empNumber))
					return;
				EmployeeInfo res = DB.remove(info.empNumber);
				if (res == null)
					MessageDialog.openError(shell, "Error", "Problem occured removing employee " + res.empNumber);
				else {
					close();
					MessageDialog.openInformation(shell, "Info", "Successfully removed employee " + res.empNumber);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		buttons.pack();
	}

	private void loadInfo() {
		class Info {
			String key, value;

			public Info(String key, String value) {
				this.key = key;
				this.value = value;
			}
		}
		Info[] i = { new Info("Name", info.firstName + " " + info.lastName),
				new Info("Type", Utils.toTitleCase(info.type.toString())), new Info("Email", info.email),
				new Info("Gender", Utils.toTitleCase(info.gender.toString())),
				new Info("Address", info.address.getAddress()), new Info("Home Phone", String.valueOf(info.homePhone)),
				new Info("Business Phone", String.valueOf(info.businessPhone)),
				new Info("Annual Salary", "$" + String.valueOf(info.calcAnnualGrossIncome())),
				new Info("Deductions Rate", String.valueOf(info.deductionsRate)) };

		Label l;
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		for (Info d : i) {
			l = new Label(shell, SWT.NONE);
			l.setText(d.key);
			l.setLayoutData(data);
			l = new Label(shell, SWT.NONE);
			l.setText(d.value);
			l.setLayoutData(data);
			l.setAlignment(SWT.RIGHT);
		}
		l = new Label(shell, SWT.NONE);
		l.setText("Notes");
		l.setLayoutData(new GridData(SWT.DEFAULT, SWT.TOP, false, false));
		Text t = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		data = new GridData(GridData.FILL_BOTH);
		data.heightHint = 200;
		t.setLayoutData(data);
		t.setText(info.notes);
		t.setEnabled(false);

//		l = new Label(shell, SWT.WRAP | SWT.BORDER);
//		l.setText(info.notes);
//		l.setLayoutData(data);
//		l.setBackground(new Color(Display.getDefault(), 255, 255, 255));
//		l.setForeground(new Color(Display.getDefault(), 10, 10, 10));
	}

	private void createLine(Composite parent) {
		Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.BOLD);
		line.setLayoutData(fullWidthData);
	}

	public void close() {
		if (!this.shell.isDisposed())
			this.shell.close();
	}
}
