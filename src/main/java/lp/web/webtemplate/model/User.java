package lp.web.webtemplate.model;

/**
 * This model represents the user entity
 * 
 * @author lucapompei
 *
 */
public class User {

	/**
	 * The id
	 */
	private int id;

	/**
	 * The username
	 */
	private String username;

	/**
	 * The password
	 */
	private String password;

	/**
	 * The enabled status
	 */
	private boolean enabled;

	/**
	 * Construct a new {@link User}
	 */
	public User() {
		// Empty implementation
	}

	/**
	 * Getter method for retrieve the id
	 *
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setter method for the id
	 *
	 * @param id,
	 *            the id
	 */
	public void setId(int id) {
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
	 * @param username,
	 *            the username
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
		return this.username;
	}

	/**
	 * Setter method for the password
	 *
	 * @param password,
	 *            the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter method for retrieve the enabled status
	 *
	 * @return the enabled status
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * Setter method for the enabled status
	 *
	 * @param email,
	 *            the enabled status
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
