package net.pdsb.nathan13888.ems.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import net.pdsb.nathan13888.ems.Utils;
import net.pdsb.nathan13888.ems.ui.menus.FileMenu;

public class EMSMenuBar {

	public EMSMenuBar(Shell shell) {
		Menu bar = new Menu(shell, SWT.BAR);

		MenuItem fileMenuHeader = new MenuItem(bar, SWT.CASCADE);
		fileMenuHeader.setText("&File");
		FileMenu fileMenu = new FileMenu(shell);
		fileMenuHeader.setMenu(fileMenu.menu);

		MenuItem searchHeader = new MenuItem(bar, SWT.PUSH);
		searchHeader.setText("&Search");

		MenuItem helpMenuHeader = new MenuItem(bar, SWT.CASCADE);
		helpMenuHeader.setText("&Help");
		helpMenuHeader.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Utils.openWebpage("https://github.com/Nathan13888/EMS");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		shell.setMenuBar(bar);
	}
}
