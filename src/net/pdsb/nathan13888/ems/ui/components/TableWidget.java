package net.pdsb.nathan13888.ems.ui.components;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.pdsb.nathan13888.ems.Utils;
import net.pdsb.nathan13888.ems.db.DB;
import net.pdsb.nathan13888.ems.types.EmployeeInfo;
import net.pdsb.nathan13888.ems.ui.InfoWindow;

public class TableWidget {

	private Table table;
	private String[] titles = { "Employee #", "Name", "Email", "Gender", "Location" };
	private ArrayList<EmployeeInfo> data;

	public InfoWindow cur = null;

	public TableWidget(Shell shell) {
		table = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
		}
		table.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				String string = event.detail == SWT.CHECK ? "Checked" : "Selected";
				System.out.println(event.item + " " + string);
				if (event.item instanceof TableItem) {
					TableItem item = (TableItem) event.item;
					int empNum = Integer.parseInt(item.getText(0));
					System.out.println("Querying data for " + empNum);
					EmployeeInfo qry = DB.query(empNum);

					System.out.println(cur);

					if (cur != null) {
						System.out.println("Closing current info window " + cur);
						cur.close();
					}
					cur = new InfoWindow(qry);
					if (qry != null)
						cur.open();
					else
						System.out.println("TableWidget: QRY resulted in null");
				} else {
					System.err.println("UNEXPECTED: " + event.item + " is not of type TableItem...");
				}
			}
		});

		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.minimumHeight = 225;
		layoutData.minimumWidth = 225;
		layoutData.grabExcessVerticalSpace = true;
		table.setLayoutData(layoutData);
		table.setSize(table.computeSize(SWT.FILL, SWT.FILL));

		this.load(DB.table.getItems());
	}

	private void load(ArrayList<EmployeeInfo> employees) {
		for (EmployeeInfo info : employees) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, String.valueOf(info.empNumber));
			item.setText(1, info.firstName + " " + info.lastName);
			item.setText(2, info.email);
			item.setText(3, Utils.toTitleCase(info.gender.name()));
			item.setText(4, info.address.getAddress());
		}
		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
		this.data = employees;
	}

	public void reload() {
		this.reload(DB.table.getItems());
	}

	public void reload(ArrayList<EmployeeInfo> employees) {
		System.out.println("Reloading TableWidget...");
		this.table.removeAll();
		this.load(employees);

		EmployeeInfo i = null;
		if (cur != null)
			i = DB.query(cur.info.empNumber);
		if (i != null) {
			this.cur.close();
			cur = new InfoWindow(i);
			cur.open();
		} else {
			System.out.println("COULD NOT FIND THE EMPLOYEE WITH THE SAME INFO ANYMORE! (prob changed emp number)");
		}
	}

	public ArrayList<EmployeeInfo> getData() {
		return this.data;
	}
}
