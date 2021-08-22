#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.utils;

import java.util.Date;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import ${package}.constants.AuthConstants;
import ${package}.constants.CommonConstants;

/**
 * This class exposes utilities to handle JWT
 */
public class JwtUtils {

	/**
	 * Private constructor for the utility class
	 */
	private JwtUtils() {
		throw new IllegalAccessError(CommonConstants.STANDARD_MESSAGE_UTILITY_CLASS);
	}

	/**
	 * Generates and returns a JWT based on the given parameters
	 * 
	 * @param subject,           the JWT subject
	 * @param jwtSecretKey,      the secret used to secure the JWT
	 * @param jwtExpirationTime, the JWT expiration time
	 * @return a JWT based on the given parameters
	 */
	public static String getToken(String subject, String jwtSecretKey, long jwtExpirationTime) {
		return Jwts.builder().setSubject(subject)
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
				.signWith(SignatureAlgorithm.HS512, jwtSecretKey).compact();
	}

	/**
	 * Parses the JWT and returns its subject using the given secret key
	 * 
	 * @param token,        the JWT
	 * @param jwtSecretKey, the secret key used to decode the JWT
	 * @return the parsed JWT
	 */
	public static String parseToken(String token, String jwtSecretKey) {
		try {
			return Jwts.parser().setSigningKey(jwtSecretKey)
					.parseClaimsJws(token.replace(AuthConstants.AUTH_BEARERPREFIX, "")).getBody().getSubject();
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException e) {
			return null;
		}
	}

}
