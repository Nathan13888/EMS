package net.pdsb.nathan13888.ems.ui.menus;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class NewMenu {

	public Menu menu;

	public NewMenu(Menu parent) {
		menu = new Menu(parent);
		MenuItem newEmployee = new MenuItem(this.menu, SWT.PUSH);
		newEmployee.setText("&Employee");

		MenuItem newTable = new MenuItem(this.menu, SWT.PUSH);
		newTable.setText("&Table");
	}
}
