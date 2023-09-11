#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.controllers;

import org.springframework.beans.factory.annotation.Autowired;
#if (${withCache} == 'Y')
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.DeleteMapping;
#end
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ${package}.constants.EndpointConstants;
import ${package}.services.InfoService;

import java.util.Objects;

/**
 * This rest controller exposes endpoints to handle the base requests
 */
@RestController
public class BaseController {

	/**
	 * The info service
	 */
	@Autowired
	private InfoService infoService;
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
	@GetMapping(value = EndpointConstants.ROOT)
	public ResponseEntity<String> getRoot() {
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
	#if (${withCache} == 'Y')
	/**
	 * Cache Endpoint
	 *
	 * @return the cache
	 */
	@DeleteMapping(value = EndpointConstants.CACHE)
	public ResponseEntity<String> cleanCache() {
		cacheManager.getCacheNames()
				.stream()
				.map(cacheManager::getCache)
				.filter(Objects::nonNull)
				.forEach(Cache::invalidate);
		return ResponseEntity.ok("Ok");
	}
	#end

}
