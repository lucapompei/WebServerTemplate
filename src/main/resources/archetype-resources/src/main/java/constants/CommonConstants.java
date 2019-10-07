#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.constants;

/**
 * This utility class exposes common constants
 *
 * @author lucapompei
 */
public class CommonConstants {



	/**
	 * The standard message for the private constructor of an utility class
	 */
	public static final String STANDARD_MESSAGE_UTILITY_CLASS = "Utility class";



	/**
	 * The log property used to indicate the logs path
	 */
	public static final String LOGS_PATH = "LOG_PATH";



	/**
	 * Private constructor for an utility class
	 */
	private CommonConstants() {
		throw new IllegalAccessError(STANDARD_MESSAGE_UTILITY_CLASS);
	}

}
