package lp.web.template.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lp.web.utils.RestUtils;

/**
 * This rest controller exposes endpoints to handle homepage requests
 * 
 * @author lucapompei
 *
 */
@RestController
@RequestMapping("/")
public class HomeController {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LogManager.getFormatterLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getHome(HttpServletRequest request) {
		LOGGER.debug("Requesting for homepage");
		return new ModelAndView("index");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<String> getLogin(HttpServletRequest request) {
		LOGGER.debug("Requesting for login");
		return RestUtils.getResponseEntity("TODO");
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<String> getLogout(HttpServletRequest request) {
		LOGGER.debug("Requesting for logout");
		return RestUtils.getResponseEntity("TODO");
	}

}