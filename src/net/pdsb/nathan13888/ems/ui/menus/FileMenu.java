package net.pdsb.nathan13888.ems.ui.menus;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import net.pdsb.nathan13888.ems.Main;

public class FileMenu {

	public Menu menu;

	public FileMenu(Decorations parent) {
		menu = new Menu(parent, SWT.DROP_DOWN);

		MenuItem newMenuItem = new MenuItem(menu, SWT.CASCADE);
		newMenuItem.setText("&New");
		NewMenu newMenu = new NewMenu(menu);
		newMenuItem.setMenu(newMenu.menu);

		MenuItem reloadItem = new MenuItem(menu, SWT.PUSH);
		reloadItem.setText("&Reload");
		reloadItem.setToolTipText("Reload the EMS table");
		reloadItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Main.window.table.reload();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		MenuItem exitItem = new MenuItem(this.menu, SWT.PUSH);
		exitItem.setText("&Exit");
		exitItem.setToolTipText("Exit EMS");
		exitItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Main.exit();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}
}
