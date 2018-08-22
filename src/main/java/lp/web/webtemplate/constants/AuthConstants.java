package lp.web.webtemplate.constants;

/**
 * This class exposes utils to handle generic auth operations
 *
 * @author lucapompei
 */
public class AuthConstants {

	/**
	 * Private constructor for an utility class, construct a new {@code AuthUtils}
	 */
	private AuthConstants() {
		throw new IllegalAccessError();
	}

	/**
	 * Auth public constants
	 */
	public static final String AUTH_HEADER = "Authorization";
	public static final String AUTH_BASICPREFIX = "Basic ";
	public static final String AUTH_BEARERPREFIX = "Bearer ";

}
