#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.constants;

/**
 * This utility class exposes authorization and authentication constants
 *
 * @author lucapompei
 */
public class AuthConstants {



	/**
	 * The authorization header
	 */
	public static final String AUTH_HEADER = "Authorization";



	/**
	 * The prefix for basic authorization value
	 */
	public static final String AUTH_BASICPREFIX = "Basic ";



	/**
	 * The prefix for bearer authorization value
	 */
	public static final String AUTH_BEARERPREFIX = "Bearer ";



	/**
	 * Private constructor for an utility class
	 */
	private AuthConstants() {
		throw new IllegalAccessError(CommonConstants.STANDARD_MESSAGE_UTILITY_CLASS);
	}

}
