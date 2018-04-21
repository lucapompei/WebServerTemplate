package lp.web;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lp.web.utils.JsonUtils;
import lp.web.webtemplate.model.ApplicationUser;

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
		System.out.println("Starting test for datasource");
		String username = "user";
		String sql = "SELECT id, username, enabled FROM users WHERE username = ?";
		Object[] args = { username };
		ApplicationUser user = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<>(ApplicationUser.class));
		System.out.println("Query result: " + JsonUtils.toJson(user));
		Assert.assertNotNull(user);
		System.out.println("Test for datasource is completed");
	}

}
