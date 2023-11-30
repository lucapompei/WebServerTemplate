#set($symbol_pound='#')#set($symbol_dollar='$')#set($symbol_escape='\')
package ${package}.repositories;

import ${package}.entities.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This repository handles the user entity repository
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
	Optional<ApplicationUser> findByUsername(String username);

}
