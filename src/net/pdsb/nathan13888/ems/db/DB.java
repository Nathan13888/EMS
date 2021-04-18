package net.pdsb.nathan13888.ems.db;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.eclipse.jface.dialogs.MessageDialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.pdsb.nathan13888.ems.Config;
import net.pdsb.nathan13888.ems.Main;
import net.pdsb.nathan13888.ems.hashtable.MyHashTable;
import net.pdsb.nathan13888.ems.types.Address;
import net.pdsb.nathan13888.ems.types.EmployeeInfo;
import net.pdsb.nathan13888.ems.types.FullTimeEmployee;
import net.pdsb.nathan13888.ems.types.Gender;
import net.pdsb.nathan13888.ems.types.PartTimeEmployee;

public class DB {

	public static MyHashTable table = new MyHashTable(Config.BUCKETS);

	public static boolean MODIFIED = false;
	public static File DB_FILE = null;

	public static void openDB() {
		File res = browseFile("Open DB");
		if (res != null) {
			MessageDialog.openInformation(Main.window.shell, "Info", "Selected file: " + res.getAbsolutePath());
			DB_FILE = res;
			loadCurrent();
		} else {
			System.err.println("ERROR RETRIEVING FILE... (potentially because user did not choose file)");
			MessageDialog.openError(Main.window.shell, "Error", "You must select a valid archive file");
		}
	}

	public static void saveDB() {
		if (!MODIFIED) {
			System.out.println("DB: is unmodified and doesn't need to be saved");
			return;
		}
		if (DB_FILE == null) {
			System.out.println("DB: no file to save to...");
			return;
		}
		System.out.println("Saving DB...");
		saveCurrent();
		System.out.println("Saved DB");
	}

	public static void saveAsDB() {
		File res = browseFile("");
		if (res != null) {
			DB_FILE = res;
			saveCurrent();
		} else {
			System.err.println("Some problems with choosing file during SAVE AS");
		}
	}

	private static void saveCurrent() {
		DB_FILE.delete();
		Gson gson = new Gson();
		String serialized = gson.toJson(table.buckets);
		System.out.println("*** SERIALIZED DATABASE ***");
		System.out.println(serialized);

		try (FileOutputStream fos = new FileOutputStream(DB_FILE);
				BufferedOutputStream bos = new BufferedOutputStream(fos)) {
			byte[] bytes = serialized.getBytes();
			bos.write(bytes);
			bos.close();
			fos.close();
			System.out.print("Data written to '" + DB_FILE.getAbsolutePath() + "' successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void loadCurrent() {
		if (DB_FILE == null) {
			System.out.println("DB: no file has been loaded yet");
			return;
		}
		MODIFIED = false;
		System.out.println("LOADING CURRENT DATABASE FILE: " + DB_FILE.getAbsolutePath());

		String output = "";
		try {
			String s;
			BufferedReader br = null;
			FileReader freader;
			freader = new FileReader(DB_FILE);
			br = new BufferedReader(freader);
			while ((s = br.readLine()) != null) {
//				System.out.println(s);
				output += s;
			}
			freader.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("READ FROM FILE:");
		System.out.println(output);
		EmployeeDeserializer des = new EmployeeDeserializer();
		Gson gson = new GsonBuilder().registerTypeAdapter(EmployeeInfo.class, des).create();

		ArrayList<EmployeeInfo>[] buckets = gson.fromJson(output, new TypeToken<ArrayList<EmployeeInfo>[]>() {
		}.getType());
		System.out.println(buckets);
		if (buckets == null)
			table = new MyHashTable(Config.BUCKETS);
		else
			table = new MyHashTable(buckets);
		Main.window.table.reload();
	}

	private static File browseFile(String name) { // expects .emsdb files only
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileHidingEnabled(false);
		chooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "EMS Database Archives (.emsdb)";
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory())
					return true;
				if (f.isFile() && f.getAbsolutePath().endsWith(".emsdb"))
					return true;
				return false;
			}
		});
		chooser.setToolTipText(name);
		int result = chooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selected = chooser.getSelectedFile();
			System.out.println("Selected file: " + selected.getAbsolutePath());
			if (!selected.exists())
				try {
					System.out.println("File with path " + selected.getAbsolutePath()
							+ " was selected but does not exist! CREATING FILE NOW.");
					selected.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (/* selected.canRead() && selected.canWrite() && */selected.isFile()
					&& selected.getPath().endsWith(".emsdb")) {
				return selected;
			} else {
				System.out.println("INVALID FILE SELECTED...");
			}
		}
		return null;
	}

	public static int size() {
		return table.size();
	}

	public static void generateRandomData() {
		String[] first = { "John", "Bob", "Daniel", "Tom", "Jack", "Robert", "Michael", "William", "Nathan", "David",
				"Christopher", "Mark", "Kevin", "Eric", "Lisa", "Linda", "Elizabeth", "Karen", "Helen", "Sharon",
				"Jessica", "Angela", "Anna", "Christine", "Alice", "Frances" };

		String[] last = { "Green", "White", "Black", "Brown", "Lee", "Smith", "Allen", "Morris", "Taylor", "Bell",
				"Smith", "Light", "Roger", "Ford", "Scott", "Parker" };

		String[] addresses = { "The Statue of Liberty in New York, USA", "The Eiffel Tower in Paris, France",
				"Louvre Museum, Rue de Rivoli, Paris, France", "St. Basil’s Cathedral in Moscow, Russia",
				"Blue Domed Church in Santorini, Greece", "CHJC+Q4 Bohaizhen, Huairou District, Beijing, China",
				"The Great Sphinx at Giza, Egypt", "Palace of Versailles",
				"1600 Pennsylvania Avenue NW, Washington, DC 20500, United States", "The Taj Mahal in Agra, India",
				"3225 Erindale Station Rd, Mississauga, ON L5C 1Y5" };

		int[] phoneCodes = { 647, 416, 905, 226, 249, 289, 705, 907 };

		Random r = new Random();
		for (int i = 0; i < r.nextInt(100) + 50; i++) {
			int num = r.nextInt(900000) + 100000;
			String f = first[r.nextInt(first.length)];
			String l = last[r.nextInt(last.length)];
			String email = (f + l).toLowerCase() + "@email.com";
			Gender g = r.nextInt(2) == 0 ? Gender.MALE : Gender.FEMALE;
			String loc = addresses[r.nextInt(addresses.length)];
			long hp = phoneCodes[r.nextInt(phoneCodes.length)] * 1000000 + r.nextInt(10000000);
			long bp = phoneCodes[r.nextInt(phoneCodes.length)] * 1000000 + r.nextInt(10000000);
			double dR = Math.round(r.nextDouble() * 100.0) / 100.0;
			double wage = Math.round(((r.nextInt(50) + 50) * 1000) * 10000.0) / 10000.0;
			double hourlyWage = r.nextInt(20) + 14;
			double hoursPerWeek = r.nextInt(16) + 30;
			double weeksPerYear = 52 - r.nextInt(5);
			FullTimeEmployee fte = new FullTimeEmployee(num, f, l, g);
			fte.yearlySalary = wage;
			PartTimeEmployee pte = new PartTimeEmployee(num, f, l, g);
			pte.hourlyWage = hourlyWage;
			pte.hoursPerWeek = hoursPerWeek;
			pte.weeksPerYear = weeksPerYear;
			EmployeeInfo info = r.nextInt(2) == 0 ? fte : pte;
			info.email = email;
			info.address = new Address(loc);
			info.deductionsRate = dR;
			info.homePhone = hp;
			info.businessPhone = bp;
			info.notes = "Some information about " + f + " " + l + "...\n\n NEW LINES WORK TOO!";

			table.add(info);
		}
	}

	public static void add(EmployeeInfo info) {
		if (info == null) {
			System.err.println("NULL was attempted to be added to the database ;(");
			return;
		} else if (table.isInTable(info.empNumber)) {
			System.out.println("Removing employee " + info.empNumber + " " + info.firstName + " " + info.lastName);
			EmployeeInfo ret = table.pop(info.empNumber);
			if (ret != null && ret.empNumber == info.empNumber) {
				//
			} else {
				System.err.println("PROBLEM WHEN REMOVING EMPLOYEE " + info);
			}
		}
		MODIFIED = true;
		System.out.println("Adding employee " + info.empNumber + " " + info.firstName + " " + info.lastName);
		table.add(info);
		if (table.isInTable(info.empNumber))
			MessageDialog.openInformation(Main.window.shell, "Information",
					"Employee " + info.empNumber + " (" + info.firstName + " " + info.lastName + ") was changed.");
		else
			MessageDialog.openError(Main.window.shell, "Error", "Employee " + info.empNumber + " could not be added.");
	}

	public static void update(int num, EmployeeInfo info) {
		if (remove(num) == null)
			System.err.println("There has been a problem removing employee " + num);
		add(info);
	}

	public static EmployeeInfo remove(int num) {
		System.out.println("Attempting to remove employee " + num);
		EmployeeInfo res = table.pop(num);
		if (res != null)
			MODIFIED = true;
		return res;
	}

	public static EmployeeInfo query(int num) {
		return table.get(num);
	}

	public static void updateStatus() {
		MODIFIED = true;
		Main.window.menubar.fileMenu.saveDB.setEnabled((DB_FILE != null) && MODIFIED);
	}

}
