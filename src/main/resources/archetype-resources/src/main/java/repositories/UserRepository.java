#set($symbol_pound='#')#set($symbol_dollar='$')#set($symbol_escape='\')
package ${package}.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ${package}.entities.ApplicationUser;

/**
 * This repository handles the user entity repository
 *
 * @author lucapompei
 *
 */
@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {

	/**
	 * Finds and returns the user identified by the given username
	 *
	 * @param username, the username
	 * @return the user identified by the given username
	 */
	ApplicationUser findByUsername(String username);

}
