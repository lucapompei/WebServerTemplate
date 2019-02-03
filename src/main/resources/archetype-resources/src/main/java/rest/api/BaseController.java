#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest.api;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ${package}.utils.RestUtils;
import ${package}.constants.EndpointConstants;
import ${package}.service.InfoService;

/**
 * This rest controller exposes endpoints to handle the base requests
 * 
 * @author lucapompei
 *
 */
@RestController
public class BaseController {



	/**
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);



	/**
	 * The info service
	 */
	@Autowired
	private InfoService infoService;



	/**
	 * Root Endpoint
	 *
	 * @return the root
	 */
	@GetMapping(value = EndpointConstants.ROOT)
	public ResponseEntity<String> getHome() {
		LOGGER.debug("Requesting for {}", EndpointConstants.ROOT);
		return RestUtils.getResponseEntity("Ok");
	}



	/**
	 * This method exposes API to show the main application info
	 * 
	 * @return the main application info
	 */
	@GetMapping(value = EndpointConstants.ABOUT)
	public ResponseEntity<String> getAbout() {
		LOGGER.info("Requesting for {}", EndpointConstants.ABOUT);
		Date beginTime = new Date();
		String response = infoService.getAppInfo();
		RestUtils.logSpentTime(EndpointConstants.ABOUT, beginTime);
		return RestUtils.getResponseEntity(response);
	}

}
