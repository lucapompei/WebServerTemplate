#set($symbol_pound='#')#set($symbol_dollar='$')#set($symbol_escape='\')
package ${package}.rest.api;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ${package}.constants.EndpointConstants;
import ${package}.service.InfoService;
import ${package}.utils.RestUtils;

/**
 * This rest controller exposes endpoints to handle the base requests
 * 
 * @author lucapompei
 *
 */
@RestController
public class BaseController implements ErrorController {

	/**
	 * The logger
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

	/**
	 * This method exposes API to show the application logs
	 * 
	 * @return the application logs
	 */
	@GetMapping(value = EndpointConstants.LOGS)
	public ResponseEntity<String> getLogs() {
		try {
			String response = infoService.getAppLogs();
			return RestUtils.getResponseEntity(response);
		} catch (Exception e) {
			LOGGER.error("Unable to get application logs: {}", e.getMessage());
			return RestUtils.getResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This method exposes API to show the application errors
	 * 
	 * @return the application errors
	 */
	@GetMapping(value = EndpointConstants.ERROR)
	public ResponseEntity<String> getError(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
		String response = String.format(
				"<html><body><h2>Error Page</h2><div>Status code: <b>%s</b></div>"
						+ "<div>Exception Message: <b>%s</b></div><body></html>",
				statusCode, exception == null ? "N/A" : exception.getMessage());
		return RestUtils.getResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Return the error page path
	 */
	@Override
	public String getErrorPath() {
		return EndpointConstants.ERROR;
	}

}
