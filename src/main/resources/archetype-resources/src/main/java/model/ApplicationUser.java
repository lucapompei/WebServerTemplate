#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.model;

/**
 * This model represents the user entity
 * 
 * @author lucapompei
 *
 */
public class ApplicationUser {

	/**
	 * The username
	 */
	private String username;

	/**
	 * The password
	 */
	private String password;

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
