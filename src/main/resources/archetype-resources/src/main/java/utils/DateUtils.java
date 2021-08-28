#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import ${package}.constants.CommonConstants;

/**
 * This class exposes utilities to handle date operations
 */
public class DateUtils {

	/**
	 * The date formatter pattern
	 */
	private static final String DATE_FORMATTER_PATTERN = "dd-MM-yyyy";

	/**
	 * Private constructor for the utility class
	 */
	private DateUtils() {
		throw new IllegalAccessError(CommonConstants.STANDARD_MESSAGE_UTILITY_CLASS);
	}

	/**
	 * Returns a formatted and more readable date
	 * 
	 * @param date, the date to format
	 * 
	 * @return a formatted date
	 */
	public static String getFormattedDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMATTER_PATTERN);
		return simpleDateFormat.format(date);
	}

}
