#set($symbol_pound='#')#set($symbol_dollar='$')#set($symbol_escape='\')
package ${package};

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import ${package}.rest.api.BaseController;

/**
 * This class is used to test the application controllers
 * 
 * @author lucapompei
 *
 */
@SpringBootTest
public class TestControllers {

	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TestControllers.class);

	/**
	 * The base controller
	 */
	@Autowired
	private BaseController baseController;

	/**
	 * Test home
	 */
	@DisplayName("Test home")
	@Test
	public void testHome() {
		// Getting home response
		ResponseEntity<String> home = baseController.getHome();
		LOGGER.info("Retrieved home response {}", home.getBody());
		// Test data
		Assertions.assertNotNull(home, "Home response not retrieved");
	}

	/**
	 * Test about
	 */
	@DisplayName("Test about")
	@Test
	public void testAbout() {
		// Getting about response
		ResponseEntity<String> about = baseController.getAbout();
		LOGGER.info("Retrieved about response {}", about.getBody());
		// Test data
		Assertions.assertNotNull(about, "About response not retrieved");
	}

	/**
	 * Test about
	 */
	@DisplayName("Test logs")
	@Test
	public void testLogs() {
		// Getting logs response
		ResponseEntity<String> logs = baseController.getLogs();
		LOGGER.info("Retrieved logs response {}", logs.getBody());
		// Test data
		Assertions.assertNotNull(logs, "logs response not retrieved");
	}

	/**
	 * Test errors
	 */
	@DisplayName("Test errors")
	@Test
	public void testErrors() {
		String errorPath = baseController.getErrorPath();
		LOGGER.info("Retrieved error path {}", errorPath);
		// Getting errors response
		MockHttpServletRequest request = new MockHttpServletRequest();
		ResponseEntity<String> errors = baseController.getError(request);
		LOGGER.info("Retrieved errors response {}", errors.getBody());
		// Test data
		Assertions.assertNotNull(errors, "Error response not retrieved");
	}

	/**
	 * Test errors with details
	 */
	@DisplayName("Test errors with details")
	@Test
	public void testErrorsWithDetails() {
		// Getting errors response
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setAttribute("javax.servlet.error.status_code", 500);
		request.setAttribute("javax.servlet.error.exception", new IllegalStateException("Illegal State"));
		ResponseEntity<Map<String, String>> errors = baseController.getError(request);
		LOGGER.info("Retrieved errors response {}", errors.getBody());
		// Test data
		Assertions.assertNotNull(errors, "Error response not retrieved");
	}

}
