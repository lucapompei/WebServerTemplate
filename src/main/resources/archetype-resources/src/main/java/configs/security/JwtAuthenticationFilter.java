#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.configs.security;

import java.io.InputStream;
import java.util.ArrayList;

import javax.crypto.SecretKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ${package}.utils.JwtUtils;
import ${package}.utils.JsonUtils;
import ${package}.constants.AuthConstants;
import ${package}.entities.ApplicationUser;

/**
 * This class represents a custom filter used for the jwt authentication
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/**
	 * The authentication manager
	 */
	private final AuthenticationManager authenticationManager;

	/**
	 * The secret key used for the jwt auth
	 */
	private final SecretKey jwtSecretKey;

	/**
	 * The expiration time used for the jwt auth
	 */
	private final long jwtExpirationTime;

	/**
	 * Construct a new {@link JwtAuthenticationFilter} configuring it
	 */
	public JwtAuthenticationFilter(
			AuthenticationManager authenticationManager, SecretKey jwtSecretKey, long jwtExpirationTime
	) {
		super.setAuthenticationManager(authenticationManager);
		this.authenticationManager = authenticationManager;
		this.jwtSecretKey = jwtSecretKey;
		this.jwtExpirationTime = jwtExpirationTime;
	}

	/**
	 * It tries to authenticate the request
	 * 
	 * @return the authentication
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
		try (InputStream is = req.getInputStream()) {
			// load the application user from the request body
			ApplicationUser user = JsonUtils.fromInputStream(is, ApplicationUser.class);
			if (user == null) {
				return null;
			}
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>())
			);
		} catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}

	/**
	 * Whether the request is valid, authenticate it
	 */
	@Override
	protected void successfulAuthentication(
			HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth
	) {
		// build the jwt token
		String token = JwtUtils.getToken(
				((User) auth.getPrincipal()).getUsername(), jwtSecretKey, jwtExpirationTime
		);
		res.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.AUTH_BEARER_PREFIX + token);
	}
}
