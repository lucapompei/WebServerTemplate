#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.services;

/**
 * This service handles info requests
 */
public interface IInfoService {

	/**
	 * Retrieve then main application info
	 *
	 * @return then main application info
	 */
	String getAppInfo();

	/**
	 * Retrieve then application logs
	 * 
	 * @return then application logs
	 */
	String getAppLogs();

}
