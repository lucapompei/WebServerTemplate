package lp.web.webtemplate.constants;

/**
 * This utility class exposes authorization and authentication constants
 *
 * @author lucapompei
 */
public class AuthConstants {



	/**
	 * Private constructor for an utility class
	 */
	private AuthConstants() {
		throw new IllegalAccessError(CommonConstants.STANDARD_MESSAGE_UTILITY_CLASS);
	}



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

}
