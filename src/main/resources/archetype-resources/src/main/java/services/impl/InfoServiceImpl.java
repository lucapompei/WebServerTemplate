#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ${package}.services.InfoService;

/**
 * This service handles info requests
 */
@Service
public class InfoServiceImpl implements InfoService {

	/**
	 * The application name
	 */
	@Value("${symbol_dollar}{application.app_name}")
	private String appName;

	/**
	 * The application version
	 */
	@Value("${symbol_dollar}{application.app_version}")
	private String appVersion;

	/**
	 * The application description
	 */
	@Value("${symbol_dollar}{application.app_description}")
	private String appDescription;

	/**
	 * The application timestamp
	 */
	@Value("${symbol_dollar}{application.app_timestamp}")
	private String appTimestamp;

	/**
	 * Retrieve then main application info
	 * 
	 * @return then main application info
	 */
	@Override
	public String getAppInfo() {
		return this.appName + " - v."
				+ this.appVersion + " "
				+ this.appTimestamp + " - "
				+ this.appDescription;
	}

}
