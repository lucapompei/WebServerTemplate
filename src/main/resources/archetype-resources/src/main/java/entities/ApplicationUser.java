#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class represents the user entity
 */
@Entity
@Table(name = "users")
public class ApplicationUser {

	/**
	 * The user identifier
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * The username
	 */
	@Column(name = "username")
	private String username;

	/**
	 * The password
	 */
	@Column(name = "password")
	private String password;

	/**
	 * Getter method for retrieve the identifier
	 *
	 * @return the identifier
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter method for the identifier
	 *
	 * @param username, the identifier
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter method for retrieve the username
	 *
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Setter method for the username
	 *
	 * @param username, the username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter method for retrieve the password
	 *
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Setter method for the password
	 *
	 * @param password, the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
