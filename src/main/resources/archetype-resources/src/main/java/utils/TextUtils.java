#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.utils;

import ${package}.constants.CommonConstants;

/**
 * This class exposes utilities to handle generic text operations
 */
public class TextUtils {

	/**
	 * Private constructor for the utility class
	 */
	private TextUtils() {
		throw new IllegalAccessError(CommonConstants.STANDARD_MESSAGE_UTILITY_CLASS);
	}

	/**
	 * This method checks if the passed value is {@code null} or empty
	 *
	 * @param value, the string value to check
	 * @return a {@code boolean} indicating if the checked string value is
	 *         {@code null} or empty or not
	 */
	public static boolean isNullOrEmpty(String value) {
		return value == null || value.isEmpty();
	}

}
