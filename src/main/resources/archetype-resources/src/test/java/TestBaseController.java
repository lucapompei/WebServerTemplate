#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package};

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import ${package}.controllers.BaseController;

/**
 * This class is used to test the base controller
 * 
 * @author lucapompei
 *
 */
@SpringBootTest
@DirtiesContext
public class TestBaseController {

	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TestBaseController.class);

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
	 * Test clean cache
	 */
	@DisplayName("Test clean cache")
	@Test
	public void testCleanCache() {
		// Getting clean cache response
		ResponseEntity<String> response = baseController.cleanCache();
		// Test data
		Assertions.assertNotNull(response, "Clean cache response not retrieved");
	}

}
