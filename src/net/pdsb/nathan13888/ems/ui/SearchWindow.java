package net.pdsb.nathan13888.ems.ui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
import org.eclipse.swt.widgets.Text;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import net.pdsb.nathan13888.ems.Main;
import net.pdsb.nathan13888.ems.db.DB;
import net.pdsb.nathan13888.ems.types.EmployeeInfo;

public class SearchWindow {

	private Display display;
	private Shell shell;
	private GridLayout lay;
	private GridData fullWidthData = new GridData(GridData.FILL_HORIZONTAL);

//	private ArrayList<EmployeeInfo> list;

	public SearchWindow() {
//		this.list = Main.window.table.data;
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
		shell.setSize(500, 300);
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);

		this.open();
	}

	private boolean compare(String a, String b) {
		return FuzzySearch.tokenSetPartialRatio(a, b) > 90;
	}

	public void open() {
		System.out.println("Displaying SEARCH WINDOW");
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
//			display.dispose();
	}

	Button liveUpdate, filterCurrent;

	private void loadWidgets() {
		Label label = new Label(shell, SWT.BOLD);
		label.setText("Search");
		label.setAlignment(SWT.CENTER);
		label.setLayoutData(fullWidthData);

		createLine(shell);

		Text search = new Text(shell, SWT.NONE);
		search.setToolTipText("Search");
		search.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				System.out.println("SEARCH: modified to " + search.getText());
				if (liveUpdate.getSelection())
					search(search.getText());
			}
		});
		search.addListener(SWT.Traverse, new Listener() {
			@Override
			public void handleEvent(Event event) {
				switch (event.detail) {
				case SWT.TRAVERSE_RETURN:
					event.detail = SWT.TRAVERSE_NONE;
					event.doit = false;
					search(search.getText());
					break;
				}
			}
		});
		search.setLayoutData(fullWidthData);

		liveUpdate = new Button(shell, SWT.CHECK);
		liveUpdate.setText("Live Update");
		filterCurrent = new Button(shell, SWT.CHECK);
		filterCurrent.setText("Filter Current");
	}

	@SuppressWarnings("unchecked")
	private void search(String q) {
		if (q.length() <= 0) {
//			this.list.clear();
//			this.list.addAll(DB.table.getItems());
			System.out.println("SEARCH: received string of invalid length");
			Main.window.table.reload();
			return;
		}
		System.out.println("Searching " + q);
		ArrayList<EmployeeInfo> f = null, FF = new ArrayList<EmployeeInfo>();
		if (filterCurrent.getSelection())
			f = Main.window.table.data;
//			f = (ArrayList<EmployeeInfo>) this.list.clone();
		else
			f = DB.table.getItems();
//			f = (ArrayList<EmployeeInfo>) DB.table.getItems().clone();
//		for (Iterator<EmployeeInfo> it = f.iterator(); it.hasNext();) {
//			EmployeeInfo i = it.next();
//			if (!test(q, i))
//				f.remove(i);
//		    if(...) {  
//		        //irrelevant stuff..
//		        if(element.cFlag){
//		            // mElements.add(new Element("crack",getResources(), (int)touchX,(int)touchY));
//		            thingsToBeAdd.add(new Element("crack",getResources(), (int)touchX,(int)touchY));
//		            element.cFlag = false;
//		        }           
//		    }
//		}
//		f.addAll(thingsToBeAdd);
		for (EmployeeInfo i : f) {
			if (test(q, i))
				FF.add(i);
//			if (!test(q, i))
//				f.remove(i);
		}

		Main.window.table.reload(FF);
	}

	private boolean test(String q, EmployeeInfo i) {
		String[] cases = { i.email, i.firstName, i.lastName, i.address.getAddress(), i.gender.toString(),
				i.type.toString(), String.valueOf(i.homePhone), String.valueOf(i.homePhone),
				String.valueOf(i.businessPhone), String.valueOf(i.empNumber) };

		boolean b = false;

		for (String x : cases) {
			if (compare(q, x)) {
				b = true;
				break;
			}
		}

		return b;
	}

	private void createLine(Composite parent) {
		Label line = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.BOLD);
		line.setLayoutData(fullWidthData);
	}

	public void update() {
	}

	public void close() {
		this.shell.close();
	}

}
