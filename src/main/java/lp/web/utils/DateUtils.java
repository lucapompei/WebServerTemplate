package lp.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * This class exposes utils to handle date operation
 * 
 * @author lucapompei
 * 
 */
public class DateUtils {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(DateUtils.class);

	/**
	 * Date formatter
	 */
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final String SQL_DATE_TYPE = "yyyy-MM-dd";
	private static final SimpleDateFormat SQL_FORMATTER = new SimpleDateFormat(SQL_DATE_TYPE);

	/**
	 * Parse a string and convert it to date
	 * 
	 * @param stringDate,
	 *            the string to parse
	 * @return the parsed string
	 */
	public static Date stringToDate(String stringDate) {
		try {
			return DATE_FORMATTER.parse(stringDate);
		} catch (Exception e) {
			LOGGER.warn("No valid date to parse: " + stringDate);
			return null;
		}
	}

	/**
	 * Format a date into a string
	 * 
	 * @param date,
	 *            the date to format
	 * @return a formatted string
	 */
	public static String dateToString(Date date) {
		try {
			return DATE_FORMATTER.format(date);
		} catch (Exception e) {
			LOGGER.warn("No valid date to format");
			return null;
		}
	}
	
	public static boolean isValidDateForSql(String dateToTest) {
		try {
			SQL_FORMATTER.parse(dateToTest);
			return true;
		} catch (Exception e) {
			LOGGER.warn(dateToTest + " is not a valid date for sql");
			return false;
		}
	}

}