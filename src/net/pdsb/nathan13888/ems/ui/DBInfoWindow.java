package net.pdsb.nathan13888.ems.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import net.pdsb.nathan13888.ems.Main;
import net.pdsb.nathan13888.ems.Utils;
import net.pdsb.nathan13888.ems.db.DB;
import net.pdsb.nathan13888.ems.ui.components.TableWidget;

public class DBInfoWindow {

	private TableWidget tw;
	private Display display;
	public Shell shell;
	private GridLayout lay;
	private GridData fullWidthData = new GridData(GridData.FILL_HORIZONTAL);

	public DBInfoWindow(TableWidget tw) {
		this.tw = tw;
		display = Display.getCurrent();
		shell = new Shell(display, SWT.ON_TOP);
		lay = new GridLayout(2, false);
		lay.marginTop = 10;
		lay.marginLeft = 30;
		lay.marginRight = 30;
		lay.marginBottom = 30;
		lay.verticalSpacing = 10;
		fullWidthData.horizontalSpan = 2;
		shell.setLayout(lay);
		shell.forceActive();
//		shell.forceFocus();
		this.loadWidgets();
		shell.setSize(1000, 500);
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
				event.doit = false;
				Main.exit();
			}
		});

	}

	private void createLine(Composite parent) {
		Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.BOLD);
		line.setLayoutData(fullWidthData);
	}

	class Info {
		String key, value;

		public Info(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}

	Info[] i = new Info[] { new Info("Displayed", ""), new Info("Modified?", ""), new Info("DB Size", ""),
			new Info("DB Created", ""), new Info("Last Saved", ""), new Info("DB File", "") };
	Label[] lls = new Label[6];

	private void loadWidgets() {
		Label label = new Label(shell, SWT.BOLD);
		label.setText("Database Information");
		label.setAlignment(SWT.CENTER);
		label.setLayoutData(fullWidthData);

		createLine(shell);

		Label l;
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		int cnt = 0;
		for (Info d : i) {
			l = new Label(shell, SWT.NONE);
			l.setText(d.key);
			l.setLayoutData(data);
			lls[cnt] = new Label(shell, SWT.NONE);
//			lls[cnt].setText(d.value);
			lls[cnt].setLayoutData(data);
			lls[cnt].setAlignment(SWT.RIGHT);
			cnt++;
		}

		Button reload = new Button(shell, SWT.PUSH);
		reload.setText("Reload Data");
		reload.setLayoutData(fullWidthData);
		reload.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				update();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		update();
	}

	public void update() {
		lls[0].setText(tw.data.size() + " of " + DB.size() + " employees");
		lls[1].setText(DB.MODIFIED ? "YES" : "NO");
		lls[2].setText(DB.DB_FILE == null ? "UNKNOWN"
				: String.valueOf(DB.DB_FILE.getTotalSpace() / 8 / Math.pow(1024, 3)) + "KB");
		lls[3].setText(DB.DB_FILE == null ? "UNKNOWN" : Utils.getFileCreation(DB.DB_FILE));
		lls[4].setText(DB.DB_FILE == null ? "UNKNOWN" : Utils.getFileModified(DB.DB_FILE));
		lls[5].setText(DB.DB_FILE == null ? "UNSAVED" : DB.DB_FILE.getAbsolutePath());
	}

	public void open() {
		System.out.println("Opening DB INFO WINDOW");
		shell.open();

//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch())
//				display.sleep();
//		}
//			display.dispose();
	}
}
