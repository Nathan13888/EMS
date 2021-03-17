package net.pdsb.nathan13888.ems.ui.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import net.pdsb.nathan13888.ems.Main;

public class ExitButtonListener implements SelectionListener {
	@Override
	public void widgetSelected(SelectionEvent event) {
		this.exitEMS();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		this.exitEMS();
	}

	private void exitEMS() {
		Main.window.shell.close();
		Main.display.dispose();
	}
}
