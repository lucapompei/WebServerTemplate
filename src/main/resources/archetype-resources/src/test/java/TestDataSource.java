#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import ${package}.utils.JsonUtils;
import ${package}.model.ApplicationUser;

/**
 * This class is used to test the data source
 * 
 * @author lucapompei
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MainApplication.class })
public class TestDataSource {



	/**
	 * The Jdbc template
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;



	/**
	 * Test a simple query on db
	 */
	@Test
	public void testFindByUsername() {
		System.out.println("Starting test for datasource");
		String username = "user";
		String sql = "SELECT id, username FROM users WHERE username = ?";
		Object[] args = { username };
		ApplicationUser user = jdbcTemplate.queryForObject(sql, args,
				new BeanPropertyRowMapper<>(ApplicationUser.class));
		System.out.println("Query result: " + JsonUtils.toJson(user));
		Assert.assertNotNull(user);
		System.out.println("Test for datasource is completed");
	}

}
