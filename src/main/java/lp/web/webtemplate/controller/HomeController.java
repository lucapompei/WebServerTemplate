package lp.web.webtemplate.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * This rest controller exposes endpoints to handle homepage requests
 * 
 * @author lucapompei
 *
 */
@RestController
public class HomeController {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LogManager.getFormatterLogger(HomeController.class);

	/**
	 * Endpoint for HomePage
	 * 
	 * @param request,
	 *            the http servlet request
	 * @return the home page
	 */
	@RequestMapping(value = Endpoints.HOME, method = RequestMethod.GET)
	public ModelAndView getHome(HttpServletRequest request) {
		LOGGER.debug("Requesting for homepage");
		return new ModelAndView("index");
	}

	/**
	 * Endpoint for LoginPage
	 * 
	 * @param request,
	 *            the http servlet request
	 * @return the login page
	 */
	@RequestMapping(value = Endpoints.LOGIN, method = RequestMethod.GET)
	public ModelAndView getLogin(HttpServletRequest request) {
		LOGGER.debug("Requesting for login page");
		return new ModelAndView("login");
	}

}