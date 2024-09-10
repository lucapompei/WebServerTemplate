#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package};

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

#if (${withSecurity} == 'Y')
import io.jsonwebtoken.security.Keys;
import ${package}.constants.AuthConstants;
#end
import ${package}.constants.CommonConstants;
import ${package}.constants.EndpointConstants;
import ${package}.utils.DateUtils;
import ${package}.utils.JsonUtils;
#if (${withSecurity} == 'Y')
import ${package}.utils.JwtUtils;
#end
import ${package}.utils.TextUtils;

/**
 * This class is used to test the utility classes
 */
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
class TestUtilities {

	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TestUtilities.class);

	#if (${withSecurity} == 'Y')
	/**
	 * The secret key used for the jwt auth
	 */
	@Value("${security.security_jwtauth_secretkey}")
    private String jwtSecretKey;
	#end
	/**
	 * Test utility classes
	 * 
	 * @param <T>, the type of the class to be analyzed
	 */
	@DisplayName("Test utilities")
	@ParameterizedTest
	#if (${withSecurity} == 'Y')
	@ValueSource(classes = { 
		AuthConstants.class, 
		CommonConstants.class, 
		DateUtils.class, 
		EndpointConstants.class,
		JsonUtils.class, 
		JwtUtils.class, 
		TextUtils.class 
	})
	#end
	#if (${withSecurity} != 'Y')
	@ValueSource(classes = { 
		CommonConstants.class, 
		DateUtils.class, 
		EndpointConstants.class,
		JsonUtils.class, 
		TextUtils.class 
	})
	#end
	<T> void testConstants(Class<T> cls) throws NoSuchMethodException, SecurityException {
		// Getting class name
		String className = cls.getSimpleName();
		LOGGER.info("Testing {} constant class", className);
		// Getting class constructor
		Constructor<T> constructor = cls.getDeclaredConstructor();
		constructor.setAccessible(true);
		// Test class illegal invocation
		Assertions.assertThrows(InvocationTargetException.class, constructor::newInstance,
				"The class " + className + " is instantiable");
	}

	/**
	 * Test null text verification
	 */
	@DisplayName("Test null text verification")
	@Test
	void testNullTextVerification() {
		// Prepare cases
		String nullText = null;
		String emptyText = "";
		String notEmptyText = File.separator;
		Assertions.assertTrue(TextUtils.isNullOrEmpty(nullText), "Null text not identified");
		Assertions.assertTrue(TextUtils.isNullOrEmpty(emptyText), "Empty text not identified");
		Assertions.assertFalse(TextUtils.isNullOrEmpty(notEmptyText), "Not empty text not identified");
	}

	/**
	 * Test date utilities
	 */
	@DisplayName("Test date formatting")
	@Test
	void testDateUtils() {
		String data = DateUtils.getFormattedDate(new Date());
		// Assert data
		Assertions.assertNotNull(data, "Date utils not properly handled");
	}

	/**
	 * Test JSON utilities
	 *
	 * @param <T>, the type of the data to be analyzed
     * @param data, the data to be analyzed
     * @param expectedData, the expected data to get
	 */
	@DisplayName("Test JSON utils")
	@ParameterizedTest
	@MethodSource("getJsonUtils")
	<T> void testJsonUtils(T data, T expectedData) {
		// Assert data
		Assertions.assertEquals(JsonUtils.toJson(data), JsonUtils.toJson(expectedData),
				"JSON utils not properly handled");
	}

	/**
	 * Get a stream of arguments to test JSON utilities static methods
	 * 
	 * @return the arguments for testing json utility
	 */
	static Stream<Arguments> getJsonUtils() {
		Object emptyObject = new Object();
		String emptyJson = "{}";
		String emptyListJson = "[]";
		return Stream.of(
				// Empty object to empty JSON
				Arguments.of(JsonUtils.toJson(emptyObject), emptyJson),
				// Empty JSON to wrong class
				Arguments.of(JsonUtils.fromJson(emptyJson, String.class), null),
				// Empty JSON to right class
				Arguments.of(JsonUtils.fromJson(emptyJson, Object.class), emptyObject),
				// Empty JSON to type reference class
				Arguments.of(JsonUtils.fromJson(emptyJson, new TypeReference<List<String>>() {}), new ArrayList<>()),
				// Empty JSON to type reference class
				Arguments.of(JsonUtils.fromJson(emptyListJson, new TypeReference<List<String>>() {}), new ArrayList<>()),
				// Empty JSON input stream to wrong class
				Arguments.of(JsonUtils.fromInputStream(IOUtils.toInputStream(emptyJson, Charset.defaultCharset()), String.class), null),
				// Empty JSON input stream to right class
				Arguments.of(JsonUtils.fromInputStream(IOUtils.toInputStream(emptyJson, Charset.defaultCharset()), Object.class), emptyObject));
	}

	#if (${withSecurity} == 'Y')
	/**
	 * Test generate JWT
	 */
	@DisplayName("Test JWT generate and parse")
	@Test
	void testJWTGenerateAndParse() {
		String subject = "username";
		SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
		long jwtExpirationTime = 1000 * 60 * 24;
		String token = JwtUtils.getToken(subject, key, jwtExpirationTime);
		String user = JwtUtils.parseToken(token, key);
		Assertions.assertNotNull(token);
		Assertions.assertEquals(subject, user);
	}

	/**
	 * Test expired JWT
	 */
	@DisplayName("Test expired JWT")
	@Test
	void testExpiredJwt() {
		String subject = "username";
		SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
		long jwtExpirationTime = 0;
		String expiredToken = JwtUtils.getToken(subject, key, jwtExpirationTime);
		String user = JwtUtils.parseToken(expiredToken, key);
		Assertions.assertNotNull(expiredToken);
		Assertions.assertNull(user);
	}
	#end

}
