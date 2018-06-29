package lp.web.webtemplate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Endpoint for HomePage
	 *
	 * @return the home page
	 */
	@RequestMapping(value = Endpoints.HOME, method = RequestMethod.GET)
	public ModelAndView getHome() {
		LOGGER.debug("Requesting for homepage");
		return new ModelAndView("index");
	}

	/**
	 * Endpoint for LoginPage
	 *
	 * @return the login page
	 */
	@RequestMapping(value = Endpoints.LOGIN, method = RequestMethod.GET)
	public ModelAndView getLogin() {
		LOGGER.debug("Requesting for login page");
		return new ModelAndView("login");
	}

}