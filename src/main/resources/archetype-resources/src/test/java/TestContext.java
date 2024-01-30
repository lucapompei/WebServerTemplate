#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package};

import ${package}.configs.WebConfig;
import ${package}.constants.EndpointConstants;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.ServletException;
import java.io.IOException;

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
     * The rest template
     */
    @Autowired
    private RestTemplate restTemplate;

    public static MockWebServer wsClient;

    @BeforeAll
    static void setUp() throws IOException {
        wsClient = new MockWebServer();
        wsClient.start(8091);
    }

    @AfterAll
    static void tearDown() throws IOException {
        wsClient.shutdown();
    }

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
		request.setMethod("POST");
		request.setRequestURI(uri);
		request.setQueryString("a=b");
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		new WebConfig().doFilter(request, response, chain);
		Assertions.assertTrue(true);
	}

	/**
     * Test client interceptor
     */
    @DisplayName("Test client interceptor")
    @Test
    void testClientInterceptor() {
        wsClient.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody(""));
        wsClient.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody(""));
        restTemplate.exchange("http://localhost:8091", HttpMethod.PUT, new HttpEntity<>("TEST", new HttpHeaders()), Void.class);
        restTemplate.exchange("http://localhost:8091", HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), Void.class);
		Assertions.assertTrue(true);
    }

}
