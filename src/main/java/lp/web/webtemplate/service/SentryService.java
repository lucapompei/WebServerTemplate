package lp.web.webtemplate.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.sentry.Sentry;
import io.sentry.event.Event;

/**
 * This service is used to handle Sentry and send to it events and throwables
 * 
 * @author lucapompei
 *
 */
@Service
public class SentryService {

	/**
	 * The logger
	 */
	private static final Logger LOGGER = Logger.getLogger(SentryService.class);

	/**
	 * A boolean indicating if Sentry is enabled or not
	 */
	@Value("${sentry_enabled:false}")
	private boolean isSentryEnabled;

	/**
	 * Construct a new empty {@link SentryService}
	 */
	public SentryService() {
		// Empty implementation
	}

	/**
	 * Capture a throwable and, if Sentry is enabled, sent to it
	 * 
	 * @param throwable,
	 *            the throwable to sent to Sentry
	 */
	public void capture(Throwable throwable) {
		if (throwable != null) {
			if (isSentryEnabled) {
				LOGGER.debug("Sending throwable to Sentry: " + throwable.getMessage());
				Sentry.capture(throwable);
			} else {
				LOGGER.debug("Sentry is not enabled, so no throwable is sent to it");
			}
		}
	}

	/**
	 * Capture an event and, if Sentry is enabled, sent to it
	 * 
	 * @param event,
	 *            the event to sent to Sentry
	 */
	public void capture(Event event) {
		if (event != null) {
			if (isSentryEnabled) {
				LOGGER.debug("Sending event to Sentry: " + event.getMessage());
				Sentry.capture(event);
			} else {
				LOGGER.debug("Sentry is not enabled, so no event is sent to it");
			}
		}
	}

}
