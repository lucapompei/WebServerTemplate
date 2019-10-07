#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.LoggerContext;
import ${package}.constants.CommonConstants;

/**
 * This service handles info requests
 * 
 * @author lucapompei
 *
 */
@Service
public class InfoService {



	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(InfoService.class);



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
	 * Retrieve then main application info
	 * 
	 * @return then main application info
	 */
	public String getAppInfo() {
		return this.appName + " - v." + this.appVersion;
	}



	/**
	 * Retrieve then application logs
	 * 
	 * @return then application logs
	 * @throws IOException
	 */
	public String getAppLogs() throws IOException {
		// Get logger context
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		// Get the logs path
		String logsPath = context.getProperty(CommonConstants.LOGS_PATH);
		LOGGER.debug("Getting logs from {}", logsPath);
		// Read logs
		return Files.readString(Path.of(logsPath), StandardCharsets.US_ASCII);
	}

}
