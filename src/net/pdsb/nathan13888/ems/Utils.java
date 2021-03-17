package net.pdsb.nathan13888.ems;

public class Utils {

	public static String toTitleCase(String s) {
		if (s.isBlank() || s.length() == 0) {
			return "";
		} else {
			return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
		}
	}
}
