package net.pdsb.nathan13888.ems.ui.menus;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import net.pdsb.nathan13888.ems.Config;
import net.pdsb.nathan13888.ems.Main;
import net.pdsb.nathan13888.ems.db.DB;
import net.pdsb.nathan13888.ems.hashtable.MyHashTable;
import net.pdsb.nathan13888.ems.ui.forms.EmployeeInfoForm;

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
				EmployeeInfoForm wizard = new EmployeeInfoForm();

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
		newTable.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				boolean res = MessageDialog.openQuestion(Main.window.shell, "Are you sure?",
						"Do you want to create a new empty database?");
				if (res) {
					System.out.println("Creating a new database...");
					if (DB.MODIFIED)
						if (!MessageDialog.openConfirm(Main.window.shell, "Confirm",
								"Your current database has modifications, are you sure you want to proceed?"))
							return;
					DB.table = new MyHashTable(Config.BUCKETS);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}
}
