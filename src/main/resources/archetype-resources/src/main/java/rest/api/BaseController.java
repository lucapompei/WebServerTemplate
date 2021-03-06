#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.rest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ${package}.constants.EndpointConstants;
import ${package}.service.InfoService;

import java.util.Objects;

/**
 * This rest controller exposes endpoints to handle the base requests
 * 
 * @author lucapompei
 *
 */
@RestController
public class BaseController {

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
	 * The cache manager
	 */
	@Autowired
	private CacheManager cacheManager;

	/**
	 * Root Endpoint
	 *
	 * @return the root
	 */
	@GetMapping(value = EndpointConstants.ROOT)
	public ResponseEntity<String> getHome() {
		return ResponseEntity.ok("Ok");
	}

	/**
	 * This method exposes API to show the main application info
	 * 
	 * @return the main application info
	 */
	@GetMapping(value = EndpointConstants.ABOUT)
	public ResponseEntity<String> getAbout() {
		String response = infoService.getAppInfo();
		return ResponseEntity.ok(response);
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
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			LOGGER.error("Unable to get application logs: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Cache Endpoint
	 *
	 * @return the cache
	 */
	@DeleteMapping(value = EndpointConstants.CACHE)
	public ResponseEntity<String> cleanCache() {
		cacheManager.getCacheNames().forEach(e -> Objects.requireNonNull(cacheManager.getCache(e)));
		return ResponseEntity.ok("Ok");
	}

}
