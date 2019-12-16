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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import ${package}.model.ApplicationUser;

/**
 * This class is used to test the data source
 * 
 * @author lucapompei
 *
 */
@SpringBootTest
public class TestDataSource {

	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TestDataSource.class);

	/**
	 * The Jdbc template
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Test a simple query on db
	 */
	@DisplayName("Find user by username")
	@Test
	public void testFindByUsername() {
		LOGGER.info("Starting test for datasource");
		String username = "user";
		String sql = "SELECT id, username FROM users WHERE username = ?";
		Object[] args = { username };
		ApplicationUser user = jdbcTemplate.queryForObject(sql, args,
				new BeanPropertyRowMapper<>(ApplicationUser.class));
		LOGGER.info("Retrieved user data from db");
		Assertions.assertNotNull(user, "User not found");
		Assertions.assertNotNull(user.getId(), "User ID not found");
		Assertions.assertNotNull(user.getUsername(), "User name not found");
		LOGGER.info("Test for datasource is completed");
	}

}
