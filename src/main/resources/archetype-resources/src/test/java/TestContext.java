#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class is used to test the context loading
 * 
 * @author lucapompei
 *
 */
@SpringBootTest
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
		LOGGER.info("Context loaded succesfully");
		Assertions.assertTrue(true);
	}

}
