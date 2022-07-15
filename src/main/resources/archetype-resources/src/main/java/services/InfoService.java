#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.services;

/**
 * This service handles info requests
 */
public interface InfoService {

	/**
	 * Retrieve then main application info
	 *
	 * @return then main application info
	 */
	String getAppInfo();

}
