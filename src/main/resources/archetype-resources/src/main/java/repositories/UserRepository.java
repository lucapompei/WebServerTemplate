#set($symbol_pound='#')#set($symbol_dollar='$')#set($symbol_escape='\')
package ${package}.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ${package}.entities.ApplicationUser;

/**
 * This repository handles the user entity repository
 * 
 * @author lucapompei
 *
 */
@Component
public class UserRepository {

	/**
	 * The jdbc template
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Finds and returns the user identified by the given username
	 * 
	 * @param username, the username
	 * @return the user identified by the given username
	 */
	public ApplicationUser findByUsername(String username) {
		String sqlForResults = "SELECT * FROM users WHERE username = ?";
		Object[] parameters = new Object[] { username };
		List<ApplicationUser> users = this.jdbcTemplate.query(sqlForResults,
				new BeanPropertyRowMapper<ApplicationUser>(ApplicationUser.class), parameters);
		return users.isEmpty() ? null : users.get(0);
	}

}
