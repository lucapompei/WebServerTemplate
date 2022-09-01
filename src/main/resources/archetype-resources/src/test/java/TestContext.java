#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package};

import java.io.IOException;

import javax.servlet.ServletException;

import ${package}.constants.EndpointConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import ${package}.configs.WebConfig;

/**
 * This class is used to test the context loading
 */
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
class TestContext {

	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TestContext.class);

	/**
	 * Test the context loading
	 */
	@DisplayName("Load context")
	@Test
	void testContext() {
		LOGGER.info("Context loaded successfully");
		Assertions.assertTrue(true);
	}

	/**
	 * Test the request filtering
	 * 
	 * @throws IOException, if IO exception occurs
	 * @throws ServletException, if servet exception occurs
	 */
	@DisplayName("Test request filtering")
	@ParameterizedTest
	@ValueSource(strings = {EndpointConstants.ROOT, EndpointConstants.ABOUT})
	void testRequestFiltering(String uri) throws ServletException, IOException {
		LOGGER.info("Testing request filtering");
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("Origin", "junit-test");
		request.setRequestURI(uri);
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		new WebConfig().doFilter(request, response, chain);
		Assertions.assertTrue(true);
	}

}
