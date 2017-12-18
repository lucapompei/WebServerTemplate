package lp.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This class is used to test sentry
 * 
 * @author lucapompei
 *
 */
@ContextConfiguration(locations = { "classpath:META-INF/spring/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestPasswordEncoder {

	/**
	 * Construct a new empty {@link TestPasswordEncoder}
	 */
	public TestPasswordEncoder() {
		// Empty implementation
	}

	/**
	 * Test the BCrypt password encoder
	 */
	@Test
	public void testBCryptPasswordEncoding() {
		String password = "password";
		String encodedPassword = new BCryptPasswordEncoder().encode(password);
		System.out.println("Original: " + password);
		System.out.println("Encoded: " + encodedPassword);
	}

}
