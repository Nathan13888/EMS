package net.pdsb.nathan13888.ems.ui.menus;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import net.pdsb.nathan13888.ems.ui.listeners.ExitButtonListener;

public class FileMenu {

	public Menu menu;

	public FileMenu(Decorations parent) {
		menu = new Menu(parent, SWT.DROP_DOWN);

		MenuItem exitItem = new MenuItem(this.menu, SWT.PUSH);
		exitItem.setText("&Exit");
		exitItem.addSelectionListener(new ExitButtonListener());
	}
}
