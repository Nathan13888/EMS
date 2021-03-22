package net.pdsb.nathan13888.ems.ui.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.pdsb.nathan13888.ems.Utils;
import net.pdsb.nathan13888.ems.db.DB;
import net.pdsb.nathan13888.ems.types.EmployeeInfo;

public class TableWidget {

	private Table table;
	private String[] titles = { "Employee #", "Name", "Email", "Gender", "Location" };

	public TableWidget(Shell shell) {
		table = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(titles[i]);
		}

		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.minimumHeight = 225;
		layoutData.minimumWidth = 225;
		layoutData.grabExcessVerticalSpace = true;
		table.setLayoutData(layoutData);
		table.setSize(table.computeSize(SWT.FILL, SWT.FILL));

		this.load();
	}

	private void load() {
		for (EmployeeInfo info : DB.table.getItems()) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, String.valueOf(info.empNumber));
			item.setText(1, info.firstName + " " + info.lastName);
			item.setText(2, info.email);
			item.setText(3, Utils.toTitleCase(info.gender.name()));
			item.setText(4, info.location);
		}
		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
	}

	public void reload() {
		System.out.println("Reloading TableWidget...");
		this.table.removeAll();
		this.load();
	}
}