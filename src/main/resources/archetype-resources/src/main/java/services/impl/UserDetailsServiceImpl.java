#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.services.impl;

import ${package}.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * This class represents the implementation of the Spring User Details Service
 * interface
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

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
		return userRepository.findByUsername(username)
				.map(user -> new User(user.getUsername(), user.getPassword(), Collections.emptyList()))
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}
}
