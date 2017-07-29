package lp.web.webtemplate.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * This service handles info requests
 * 
 * @author lucapompei
 *
 */
@Service
public class InfoService {

	/**
	 * The application name
	 */
	@Value("${app_name}")
	private String appName;

	/**
	 * The application version
	 */
	@Value("${app_version}")
	private String appVersion;

	/**
	 * Retrieve then main application info
	 * 
	 * @return then main application info
	 */
	public String getAppInfo() {
		return this.appName + " - v." + this.appVersion;
	}

}
