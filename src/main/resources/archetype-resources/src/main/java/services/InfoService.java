#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.services;

import java.io.IOException;

/**
 * This service handles info requests
 * 
 * @author lucapompei
 *
 */
public interface InfoService {

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
	 * @throws IOException
	 */
	String getAppLogs() throws IOException;

}
