package lp.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lp.web.webtemplate.service.SentryService;

/**
 * This class is used to test sentry
 * 
 * @author lucapompei
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MainApplication.class })
public class TestSentryReport {

	/**
	 * The Sentry service
	 */
	@Autowired
	private SentryService sentryService;

	/**
	 * A simple method used to force errors and report them to Sentry
	 */
	@Test
	public void testErrorToReport() {
		System.out.println("Starting test for Sentry");
		forceHandledError();
		System.out.println("Sentry test completed");
	}

	/**
	 * A simple method used to force an handled {@link RuntimeException}
	 */
	private void forceHandledError() {
		try {
			throw new RuntimeException("Handled RuntimeException");
		} catch (RuntimeException ex) {
			System.out.println("New error: " + ex.getMessage());
			// capture exception through Sentry
			sentryService.capture(ex);
		}
	}

}
