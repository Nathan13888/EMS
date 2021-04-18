package net.pdsb.nathan13888.ems.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import net.pdsb.nathan13888.ems.Main;
import net.pdsb.nathan13888.ems.ui.components.TableWidget;

public class EMSWindow {

	static final String NAME = "Employee Management System";

	public Shell shell;
	public TableWidget table;
	public EMSMenuBar menubar;
	public DBInfoWindow infowindow;

	public EMSWindow(Display display) {
		Main.window = this;
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
//		shell.setBackground(new Color(display, 6, 9, 6));
//		shell.setImage(null);
		this.loadWidgets();

		this.shell.setBounds(display.getBounds());
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
				boolean res = MessageDialog.openConfirm(shell, "Confirm", "Are you sure that you want to close EMS?");
				System.out.println("Confirmation to close EMS: " + res);
				if (!res) {
					event.doit = false;
				} else {
					System.exit(0);
				}
			}
		});

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private void loadWidgets() {
		menubar = new EMSMenuBar(this.shell);

		this.table = new TableWidget(this.shell);
		this.infowindow = new DBInfoWindow(this.table);
		infowindow.open();
//		shell.addk
//		shell.addFocusListener(new FocusListener() {
//
//			@Override
//			public void focusLost(FocusEvent arg0) {
//			}
//
//			@Override
//			public void focusGained(FocusEvent arg0) {
//				if (infowindow != null && !infowindow.shell.isEnabled())
//					infowindow.open();
//			}
//		});
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				infowindow.open();
//			}
//		}).start();
	}

//
	public void close() {
		this.shell.dispose();
	}
}
