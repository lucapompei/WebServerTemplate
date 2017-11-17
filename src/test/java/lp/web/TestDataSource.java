package lp.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lp.web.utils.JsonUtils;
import lp.web.webtemplate.model.User;

/**
 * This class is used to test the data source
 * 
 * @author lucapompei
 *
 */
@ContextConfiguration(locations = { "classpath:META-INF/spring/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
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
		String username = "user";
		String sql = "SELECT username, email FROM users WHERE username = ?";
		Object[] args = { username };
		User result = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<>(User.class));
		System.out.println("Query result: " + JsonUtils.toJson(result));
	}

}
