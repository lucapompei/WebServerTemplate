#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.services.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ${package}.repositories.UserRepository;
import ${package}.entities.ApplicationUser;

/**
 * This class represents the implementation of the Spring User Details Service
 * interface
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	/**
	 * The user repository
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * Loads user details based on the given username
	 * 
	 * @param username, the username
	 * @return the user details based on the given username
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		ApplicationUser user = this.userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				Collections.emptyList());
	}
}
