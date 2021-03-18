package net.pdsb.nathan13888.ems.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import net.pdsb.nathan13888.ems.ui.components.TableWidget;

public class EMSWindow {

	static final String NAME = "Employee Management System";

	public Shell shell;

	public EMSWindow(Display display) {
		shell = new Shell(display);

		Display.setAppName(NAME);
		Display.setAppVersion("0.0.1");
		shell.setText(NAME);

		GridLayout lay = new GridLayout(2, false);
		lay.marginTop = 0;
		lay.marginLeft = 0;
		lay.marginBottom = 0;
		shell.setLayout(lay);
		shell.setSize(1920, 1080);
		shell.setBackground(new Color(display, 6, 9, 6));
//		shell.setImage(null);
		this.loadWidgets();

		this.shell.setBounds(display.getBounds());

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private void loadWidgets() {
		new EMSMenuBar(this.shell);

		new TableWidget(this.shell);
	}
}
