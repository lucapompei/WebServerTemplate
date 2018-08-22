package lp.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import lp.web.webtemplate.MainApplication;

/**
 * This class is used to test sentry
 * 
 * @author lucapompei
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MainApplication.class })
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
