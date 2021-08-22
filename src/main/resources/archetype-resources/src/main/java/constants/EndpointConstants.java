#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.constants;

/**
 * This utility class expose endpoint constants
 */
public class EndpointConstants {

	/**
	 * The root endpoint
	 */
	public static final String ROOT = "/";
	#if (${withSecurity} == 'Y')
	/**
	 * The swagger endpoint
	 */
	public static final String SWAGGER = "/swagger*/**";

	/**
	 * The swagger json endpoint
	 */
	public static final String SWAGGER_JSON = "/v3/**";

	/**
	 * The login endpoint
	 */
	public static final String LOGIN = "/login";
	#end
	/**
	 * The about endpoint
	 */
	public static final String ABOUT = "/about";

	/**
	 * The logs endpoint
	 */
	public static final String LOGS = "/logs";
	#if (${withCache} == 'Y')
	/**
	 * The cache endpoint
	 */
	public static final String CACHE = "/cache";
	#end

	/**
	 * Private constructor for the utility class
	 */
	private EndpointConstants() {
		throw new IllegalAccessError(CommonConstants.STANDARD_MESSAGE_UTILITY_CLASS);
	}

}
