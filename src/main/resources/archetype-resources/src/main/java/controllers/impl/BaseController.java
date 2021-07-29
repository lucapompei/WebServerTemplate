#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
#if (${withCache} == 'Y')
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.DeleteMapping;
#end
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ${package}.constants.EndpointConstants;
import ${package}.controllers.IBaseController;
import ${package}.services.IInfoService;

/**
 * This rest controller exposes endpoints to handle the base requests
 */
@RestController
public class BaseController implements IBaseController {

	/**
	 * The info service
	 */
	@Autowired
	private IInfoService infoService;
	#if (${withCache} == 'Y')
	/**
	 * The cache manager
	 */
	@Autowired
	private CacheManager cacheManager;
	#end

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
		String response = infoService.getAppLogs();
		return ResponseEntity.ok(response);
	}
	#if (${withCache} == 'Y')
	/**
	 * Cache Endpoint
	 *
	 * @return the cache
	 */
	@Override
	@DeleteMapping(value = EndpointConstants.CACHE)
	public ResponseEntity<String> cleanCache() {
		cacheManager.getCacheNames().forEach(cacheManager::getCache);
		return ResponseEntity.ok("Ok");
	}
	#end

}
