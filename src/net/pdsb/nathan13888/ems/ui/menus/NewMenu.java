package net.pdsb.nathan13888.ems.ui.menus;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import net.pdsb.nathan13888.ems.Main;
import net.pdsb.nathan13888.ems.ui.forms.NewContactForm;

public class NewMenu {

	public Menu menu;

	public NewMenu(Menu parent) {
		menu = new Menu(parent);
		MenuItem newEmployee = new MenuItem(this.menu, SWT.PUSH);
		newEmployee.setText("&Employee");
		newEmployee.setToolTipText("New Employee");
		newEmployee.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				NewContactForm wizard = new NewContactForm();

				WizardDialog dialog = new WizardDialog(Main.window.shell, wizard);
				dialog.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		MenuItem newTable = new MenuItem(this.menu, SWT.PUSH);
		newTable.setText("&Table");
		newTable.setToolTipText("New Table");
	}
}
