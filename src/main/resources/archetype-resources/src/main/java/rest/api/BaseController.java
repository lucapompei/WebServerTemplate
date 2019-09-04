#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest.api;


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
		return RestUtils.getResponseEntity("Ok");
	}



	/**
	 * This method exposes API to show the main application info
	 * 
	 * @return the main application info
	 */
	@GetMapping(value = EndpointConstants.ABOUT)
	public ResponseEntity<String> getAbout() {
		String response = infoService.getAppInfo();
		return RestUtils.getResponseEntity(response);
	}

}
