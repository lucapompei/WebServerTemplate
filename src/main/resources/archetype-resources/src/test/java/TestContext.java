#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This class is used to test the context loading
 * 
 * @author lucapompei
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MainApplication.class })
public class TestContext {



	/**
	 * Test the context loading
	 */
	@Test
	public void testContext() {
		System.out.println("Context loaded succesfully");
	}

}
