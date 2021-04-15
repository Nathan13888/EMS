package net.pdsb.nathan13888.ems;

import java.awt.Desktop;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class Utils {

	public static String toTitleCase(String s) {
		if (s.isBlank() || s.length() == 0) {
			return "";
		} else {
			return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
		}
	}

	public static boolean openWebpage(String s) {
		try {
			return openWebpage(new URI(s));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static String encodeURI(String s) {
		String result;
		try {
			result = URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20").replaceAll("\\%21", "!")
					.replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")")
					.replaceAll("\\%7E", "~");
		} catch (UnsupportedEncodingException e) {
			result = s;
		}
		return result;
	}

	public static boolean openWebpage(URI uri) {
		System.out.println("Opening webpage " + uri.toString());
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
