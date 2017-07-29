package lp.web.webtemplate.controller;

import lp.web.webtemplate.controller.api.InfoAPI;

/**
 * This class contains the list of endpoints used by the application
 * 
 * @author lucapompei
 *
 */
public class Endpoints {

	/**
	 * Endpoints handled by {@link HomeController}
	 */
	public static final String HOME = "/";
	public static final String LOGIN = "/login";
	public static final String LOGOUT = "/logout";

	/**
	 * Basepath for APIs endpoint
	 */
	public static final String API_BASE = "/api";

	/**
	 * Endpoints handled by {@link InfoAPI}
	 */
	public static final String IS_ALIVE = "/isAlive";
	public static final String ABOUT = "/about";

	/**
	 * Private constructor for {@code Endpoints}
	 */
	private Endpoints() {
		// Empty implementation
	}

}
