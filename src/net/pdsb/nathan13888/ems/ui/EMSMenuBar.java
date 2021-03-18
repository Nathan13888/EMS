package net.pdsb.nathan13888.ems.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

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

		shell.setMenuBar(bar);
	}
}
