#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import ${package}.constants.CommonConstants;

/**
 * This class exposes utilities to handle rest operations
 * 
 * @author lucapompei
 *
 */
public class RestUtils {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RestUtils.class);

	/**
	 * Cached object
	 */
	private static HttpHeaders cachedHeaders;

	/**
	 * Private constructor for an utility class
	 */
	private RestUtils() {
		throw new IllegalAccessError(CommonConstants.STANDARD_MESSAGE_UTILITY_CLASS);
	}

	/**
	 * Get a generic http headers
	 * 
	 * @return a generic http headers
	 */
	public static HttpHeaders getHeaders() {
		if (cachedHeaders != null) {
			LOGGER.debug("Using cached HttpHeader");
			return cachedHeaders;
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// update cache
		cachedHeaders = headers;
		return headers;
	}

	/**
	 * Get a response entity with the given httpStatus using object
	 * 
	 * @param <T>
	 * 
	 * @param object,     an optional param to indicate the entity to return
	 * @param httpStatus, the httpsStatus to return
	 * @return response entity with the given httpStatus using object
	 */
	public static <T> ResponseEntity<T> getResponseEntity(T object, HttpStatus httpStatus) {
		return new ResponseEntity<>(object, httpStatus);
	}

	/**
	 * Get a response entity with 200 OK HTTP status using object
	 * 
	 * @param <T>
	 * 
	 * @param object, an optional param to indicate the entity to return
	 * @return response entity with 200 OK HTTP status using object
	 */
	public static <T> ResponseEntity<T> getResponseEntity(T object) {
		return getResponseEntity(object, HttpStatus.OK);
	}

}
