package lp.web.webtemplate.model;

/**
 * This model represents the user entity
 * 
 * @author lucapompei
 *
 */
public class User {

	/**
	 * The username
	 */
	private String username;

	/**
	 * The email
	 */
	private String email;

	/**
	 * Construct a new {@link User}
	 */
	public User() {
		// Empty implementation
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
	 * @param username,
	 *            the username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter method for retrieve the email
	 *
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Setter method for the email
	 *
	 * @param email,
	 *            the email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
