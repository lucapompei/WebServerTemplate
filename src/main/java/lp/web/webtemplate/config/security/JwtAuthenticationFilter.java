package lp.web.webtemplate.config.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lp.web.utils.AuthUtils;
import lp.web.utils.JsonUtils;
import lp.web.webtemplate.model.ApplicationUser;

/**
 * This class represents a custom filter used for the jwt authentication
 * 
 * @author lucapompei
 *
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/**
	 * The authentication manager
	 */
	private AuthenticationManager authenticationManager;

	/**
	 * The secret key used for the jwt auth
	 */
	private String jwtSecretKey;

	/**
	 * The expiration time used for the jwt auth
	 */
	private long jwtExpirationTime;

	/**
	 * Construct a new {@link JwtAuthenticationFilter} configuring it
	 */
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String jwtSecretKey,
			long jwtExpirationTime) {
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
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			// load the application user from the request body
			ApplicationUser creds = JsonUtils.fromInputStream(req.getInputStream(), ApplicationUser.class);
			return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
					creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Whether the request is valid, authenticate it
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		// build the jwt token
		String token = Jwts.builder().setSubject(((User) auth.getPrincipal()).getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + this.jwtExpirationTime))
				.signWith(SignatureAlgorithm.HS512, this.jwtSecretKey).compact();
		res.addHeader(AuthUtils.AUTH_HEADER, AuthUtils.AUTH_BEARERPREFIX + token);
	}
}
