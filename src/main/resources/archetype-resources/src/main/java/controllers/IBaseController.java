#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.controllers;

import org.springframework.http.ResponseEntity;

/**
 * This rest controller exposes endpoints to handle the base requests
 *
 * @author lucapompei
 *
 */
public interface IBaseController {

	/**
	 * Root Endpoint
	 *
	 * @return the root
	 */
	ResponseEntity<String> getRoot();

	/**
	 * This method exposes API to show the main application info
	 *
	 * @return the main application info
	 */
	ResponseEntity<String> getAbout();

	/**
	 * This method exposes API to show the application logs
	 *
	 * @return the application logs
	 */
	ResponseEntity<String> getLogs();
	#if (${withCache} == 'Y')
	/**
	 * Cache Endpoint
	 *
	 * @return the cache
	 */
	ResponseEntity<String> cleanCache();
	#end

}
