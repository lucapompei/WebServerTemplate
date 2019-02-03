package lp.web.webtemplate.model;

/**
 * This model represents the user entity
 * 
 * @author lucapompei
 *
 */
public class ApplicationUser {



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
	 * Construct a new {@link ApplicationUser}
	 */
	public ApplicationUser() {
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
	 * @param id, the id
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
