#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
	 * Test the context loading
	 */
	@DisplayName("Load context")
	@Test
	public void testContext() {
		System.out.println("Context loaded succesfully");
		Assertions.assertTrue(true);
	}

}
