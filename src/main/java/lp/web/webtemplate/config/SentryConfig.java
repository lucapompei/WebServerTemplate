package lp.web.webtemplate.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import io.sentry.Sentry;
import io.sentry.event.User;
import io.sentry.event.UserBuilder;

/**
 * This config class globally configure the sentry client used to keep track of
 * all application errors
 * 
 * @author lucapompei
 *
 */
@Configuration
public class SentryConfig {

	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SentryConfig.class);

	/**
	 * A boolean indicating if Sentry is enabled or not
	 */
	@Value("${sentry_enabled:false}")
	private boolean isSentryEnabled;

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
	 * Construct a new empty {@link SentryConfig}
	 */
	public SentryConfig() {
		// Empty implementation
	}

	@PostConstruct
	private void initClient() {
		if (isSentryEnabled) {
			LOGGER.info("Enabling Sentry for errors/crashes reporting");
			// initialize sentry
			Sentry.init();
			// configure user for sentry
			User sentryUser = new UserBuilder().setUsername(appName).build();
			Sentry.getContext().setUser(sentryUser);
			// add additional information to sentry
			Sentry.getContext().addTag("tagName", appVersion);
		} else {
			LOGGER.info("Sentry is not enabled by properties, so crashes/errors are not reported on it");
		}
	}

	/**
	 * Configure a bean to record all exceptions thrown by the controllers
	 * 
	 * @return the handler exception resolver
	 */
	@Bean
	public HandlerExceptionResolver sentryExceptionResolver() {
		if (this.isSentryEnabled) {
			return new io.sentry.spring.SentryExceptionResolver();
		} else {
			// allow definitions of other handler
			return null;
		}
	}

}
