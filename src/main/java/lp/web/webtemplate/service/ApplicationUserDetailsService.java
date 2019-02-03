package lp.web.webtemplate.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lp.web.webtemplate.dao.UserRepository;
import lp.web.webtemplate.model.ApplicationUser;

/**
 * This class represents the implementation of the Spring User Details Service
 * interface
 * 
 * @author lucapompei
 *
 */
@Service
public class ApplicationUserDetailsService implements UserDetailsService {



	/**
	 * The user repository
	 */
	@Autowired
	private UserRepository userRepository;



	/**
	 * Create a new empty {@link ApplicationUserDetailsService}
	 */
	public ApplicationUserDetailsService() {
		// Empty implementation
	}



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
