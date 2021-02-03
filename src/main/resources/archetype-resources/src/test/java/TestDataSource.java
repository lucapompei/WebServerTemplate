#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package};

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.beans.factory.annotation.Autowired;

import ${package}.service.ApplicationUserDetailsService;

/**
 * This class is used to test the data source
 * 
 * @author lucapompei
 *
 */
@SpringBootTest
@DirtiesContext
public class TestDataSource {

	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TestDataSource.class);

	/**
	 * The application user details service
	 */
	@Autowired
	private ApplicationUserDetailsService applicationUserDetailsService;

	/**
	 * Test a simple query to find an existing user on db
	 */
	@DisplayName("Find user by existing username")
	@Test
	public void testFindByExistingUsername() {
		LOGGER.info("Starting test for finding existing user on db");
		String username = "user";
		UserDetails user = applicationUserDetailsService.loadUserByUsername(username);
		LOGGER.info("Retrieved user data from db");
		Assertions.assertNotNull(user, "User not found");
		Assertions.assertNotNull(user.getUsername(), "User name not found");
		Assertions.assertNotNull(user.getPassword(), "User password not found");
		LOGGER.info("Test for finding an existing user is completed");
	}

	/**
	 * Test a simple query to find a not existing user on db
	 */
	@DisplayName("Find user by not existing username")
	@Test
	public void testFindByNotExistingUsername() {
		LOGGER.info("Starting test for finding not existing user on db");
		String username = "newUser";
		Assertions.assertThrows(UsernameNotFoundException.class,
				() -> applicationUserDetailsService.loadUserByUsername(username));
		LOGGER.info("Test for finding a not existing user completed");
	}

}
