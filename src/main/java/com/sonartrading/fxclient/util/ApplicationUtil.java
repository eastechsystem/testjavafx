package com.sonartrading.fxclient.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationUtil {
	
	/**
	 * Read the URL and return the json data
	 * 
	 * @param urlString
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private static String readUrl(String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			System.out.println(buffer.toString());

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	 /**
     * The isNotNullAndEmpty() method returns true if the given string is not null and not empty. Otherwise it returns
     * false.
     * 
     * @param string
     *            Specifies the element that is to be checked for null and empty state
     * @return Returns true if the given string is not null and not empty. Otherwise it returns false.
     */
    public static boolean isNotNullAndEmpty(String string) {
        return (((string != null) && (string.trim().length() > 0)) ? true : false);
    }
    
	
	/**
	 * This method formats date in given format.
	 * 
	 * @param datePattern
	 *            Specifies date format.
	 * @param date
	 *            Specifies date.
	 * @return Formatted date.
	 */
	public static String formatDate(String datePattern, Date date) {
		SimpleDateFormat customFormat = new SimpleDateFormat(datePattern);
		customFormat.setLenient(false);
		return customFormat.format(date);
	}
	
	/**
	 * This method creates date from its string form in given format.
	 * 
	 * @param datePattern
	 *            Specifies date format.
	 * @param dateString
	 *            Specifies date in its string form
	 * @return Date created from its string form in given format.
	 * @throws ParseException
	 *             Errors occurred while parsing date.
	 */
	public static Date parseDate(String datePattern, String dateString)
			throws ParseException {
		SimpleDateFormat customFormat = new SimpleDateFormat(datePattern);
		customFormat.setLenient(false);
		return customFormat.parse(dateString);
	}
}
