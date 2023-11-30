#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.utils;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.*;
import ${package}.constants.AuthConstants;
import ${package}.constants.CommonConstants;

import javax.crypto.SecretKey;

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
	public static String getToken(String subject, Key jwtSecretKey, long jwtExpirationTime) {
		return Jwts.builder()
				.subject(subject)
				.expiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
				.signWith(jwtSecretKey)
				.compact();
	}

	/**
	 * Parses the JWT and returns its subject using the given secret key
	 * 
	 * @param token,        the JWT
	 * @param jwtSecretKey, the secret key used to decode the JWT
	 * @return the parsed JWT
	 */
	public static String parseToken(String token, SecretKey jwtSecretKey) {
		try {
			return Jwts.parser()
					.verifyWith(jwtSecretKey)
					.build()
					.parseSignedClaims(token.replace(AuthConstants.AUTH_BEARER_PREFIX, ""))
					.getPayload()
					.getSubject();
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SecurityException
				| IllegalArgumentException e) {
			return null;
		}
	}

}
