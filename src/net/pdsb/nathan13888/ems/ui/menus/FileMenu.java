package net.pdsb.nathan13888.ems.ui.menus;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import net.pdsb.nathan13888.ems.Main;
import net.pdsb.nathan13888.ems.db.DB;

public class FileMenu {

	public Menu menu;
	public MenuItem saveDB;

	public FileMenu(Decorations parent) {
		menu = new Menu(parent, SWT.DROP_DOWN);

		MenuItem newMenuItem = new MenuItem(menu, SWT.CASCADE);
		newMenuItem.setText("&New");
		NewMenu newMenu = new NewMenu(menu);
		newMenuItem.setMenu(newMenu.menu);

		MenuItem openDB = new MenuItem(menu, SWT.PUSH);
		openDB.setText("Open Database");
		openDB.setToolTipText("Load the data from a database");
		openDB.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DB.openDB();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		saveDB = new MenuItem(menu, SWT.PUSH);
		saveDB.setText("Save Database");
		saveDB.setToolTipText("Save changes to database");
		saveDB.setEnabled(false);
		saveDB.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DB.saveDB();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		MenuItem saveAsDB = new MenuItem(menu, SWT.PUSH);
		saveAsDB.setText("Save As Database");
		saveAsDB.setToolTipText("Save changes to a new database");
		saveAsDB.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DB.saveAsDB();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		MenuItem populateDBItem = new MenuItem(menu, SWT.PUSH);
		populateDBItem.setText("&Generate Random Data");
		populateDBItem.setToolTipText("Populate current database with random data");
		populateDBItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DB.generateRandomData();
				DB.updateStatus();
				Main.window.table.reload();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

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
