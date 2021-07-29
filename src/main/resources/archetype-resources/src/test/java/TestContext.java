#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package};

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import ${package}.config.WebConfig;

/**
 * This class is used to test the context loading
 */
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
public class TestContext {

	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TestContext.class);

	/**
	 * Test the context loading
	 */
	@DisplayName("Load context")
	@Test
	public void testContext() {
		LOGGER.info("Context loaded successfully");
		Assertions.assertTrue(true);
	}

	/**
	 * Test the request filtering
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@DisplayName("Test request filtering")
	@Test
	public void testRequestFiltering() throws ServletException, IOException {
		LOGGER.info("Testing request filtering");
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("Origin", "junit-test");
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		new WebConfig().doFilter(request, response, chain);
		Assertions.assertTrue(true);
	}

}
