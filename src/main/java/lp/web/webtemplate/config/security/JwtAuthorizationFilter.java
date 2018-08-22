package lp.web.webtemplate.config.security;

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

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lp.web.webtemplate.constants.AuthConstants;

/**
 * This class reprensent a custom filter used for the jwt authorization
 * 
 * @author lucapompei
 *
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	/**
	 * The secret key used for the jwt auth
	 */
	private String jwtSecretKey;

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
	 * @param request,
	 *            the request to authorize
	 * @return the authentication token
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(AuthConstants.AUTH_HEADER);
		if (token == null) {
			return null;
		}
		// parse the token
		try {
			String user = Jwts.parser().setSigningKey(this.jwtSecretKey)
					.parseClaimsJws(token.replace(AuthConstants.AUTH_BEARERPREFIX, "")).getBody().getSubject();
			return user == null ? null : new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException e) {
			return null;
		}
	}
}