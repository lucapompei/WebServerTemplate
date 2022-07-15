#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.config.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import ${package}.constants.AuthConstants;
import ${package}.utils.JwtUtils;

/**
 * This class represents a custom filter used for the jwt authorization
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	/**
	 * The secret key used for the jwt auth
	 */
	private final String jwtSecretKey;

	/**
	 * Construct a new {@link JwtAuthorizationFilter} configuring it
	 */
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, String jwtSecretKey) {
		super(authenticationManager);
		this.jwtSecretKey = jwtSecretKey;
	}

	/**
	 * Configure the custom filters for the jwt authorization
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// giving the header from the request, check it to validate the request
		String header = req.getHeader(AuthConstants.AUTH_HEADER);
		if (header == null || !header.startsWith(AuthConstants.AUTH_BEARERPREFIX)) {
			// the request does not contain the authorization
			chain.doFilter(req, res);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	/**
	 * It tries to get and validate the authorization token used by the request
	 * 
	 * @param request, the request to authorize
	 * @return the authentication token
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(AuthConstants.AUTH_HEADER);
		// parse the token
		String user = JwtUtils.parseToken(token, this.jwtSecretKey);
		return user == null ? null : new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
	}
}
