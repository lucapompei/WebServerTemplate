#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
	 * @param id, the identifier
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
