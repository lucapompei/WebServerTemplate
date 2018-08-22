package lp.web.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

/**
 * This class exposes utils to handle date operations
 *
 * @author lucapompei
 */
public class DateUtils {

	/**
	 * The date formatter
	 */
	private static final String DATE_FORMATTER_PATTERN = "dd-MM-yyyy";

	/**
	 * Private constructor for an utility class, construct a new {@code DateUtils}
	 */
	private DateUtils() {
		throw new IllegalAccessError();
	}

	/**
	 * Calculates and returns the current week
	 * 
	 * @return the current week
	 */
	public static String getCurrentWeek() {
		LocalDate date = LocalDate.now();
		TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
		return "W" + date.get(woy);
	}

	/**
	 * Returns a formatted and more readable date
	 * 
	 * @param date,
	 *            the date to format
	 * 
	 * @return a formatted date
	 */
	public static String getFormattedDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMATTER_PATTERN);
		return simpleDateFormat.format(date);
	}

}
