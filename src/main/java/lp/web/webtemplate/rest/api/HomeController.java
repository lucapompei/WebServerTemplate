package lp.web.webtemplate.rest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lp.web.utils.RestUtils;
import lp.web.webtemplate.constants.EndpointConstants;

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
	 * Endpoint for Home page
	 *
	 * @return the home page
	 */
	@GetMapping(value = EndpointConstants.HOME)
	public ResponseEntity<String> getHome() {
		LOGGER.debug("Requesting for " + EndpointConstants.HOME);
		return RestUtils.getResponseEntity("Ok");
	}

}