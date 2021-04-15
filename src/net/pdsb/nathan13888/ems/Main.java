package net.pdsb.nathan13888.ems;

import org.eclipse.swt.widgets.Display;

import net.pdsb.nathan13888.ems.db.DB;
import net.pdsb.nathan13888.ems.ui.EMSWindow;

public class Main {

	public static Display display;
	public static EMSWindow window;

	public static void main(String[] args) {
		System.out.println("Starting EMS");

		DB.generateRandomData();

		display = new Display();

		new EMSWindow(display);
	}

	public static void exit() {
		window.shell.close();
	}

}
