#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.controllers.impl;

import java.util.Objects;

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
import ${package}.controllers.BaseController;
import ${package}.services.InfoService;

/**
 * This rest controller exposes endpoints to handle the base requests
 * 
 * @author lucapompei
 *
 */
@RestController
public class BaseControllerImpl implements BaseController {

	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseControllerImpl.class);

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
	@Override
	@GetMapping(value = EndpointConstants.ROOT)
	public ResponseEntity<String> getRoot() {
		return ResponseEntity.ok("Ok");
	}

	/**
	 * This method exposes API to show the main application info
	 * 
	 * @return the main application info
	 */
	@Override
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
	@Override
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
	@Override
	@DeleteMapping(value = EndpointConstants.CACHE)
	public ResponseEntity<String> cleanCache() {
		cacheManager.getCacheNames().forEach(e -> Objects.requireNonNull(cacheManager.getCache(e)));
		return ResponseEntity.ok("Ok");
	}

}
