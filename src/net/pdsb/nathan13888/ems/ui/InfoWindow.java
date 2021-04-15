package net.pdsb.nathan13888.ems.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import net.pdsb.nathan13888.ems.types.EmployeeInfo;

public class InfoWindow {

	private Display display;
	private Shell shell;
	private EmployeeInfo info;

	public InfoWindow(EmployeeInfo info) {
		if (info == null) {
			System.err.println("InfoWindow was given NULL info to display");
			return;
		}

		this.info = info;

		display = Display.getCurrent();
		shell = new Shell(display);

		shell.setText("Employee " + info.empNumber);

		FillLayout lay = new FillLayout(SWT.VERTICAL);
		shell.setLayout(lay);
		shell.forceActive();
		shell.forceFocus();
//		shell.setSize(1920, 1080);
//		shell.setBackground(new Color(display, 6, 9, 6));
		this.loadWidgets();

		shell.setBounds(display.getBounds());
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
		new Button(shell, SWT.PUSH);
	}

	public void close() {
		if (!this.shell.isDisposed())
			this.shell.close();
	}
}
