#set($symbol_pound='#')#set($symbol_dollar='$')#set($symbol_escape='\')
package ${package};

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import ${package}.constants.AuthConstants;
import ${package}.constants.CommonConstants;
import ${package}.constants.EndpointConstants;
import ${package}.utils.DateUtils;
import ${package}.utils.JsonUtils;
import ${package}.utils.RestUtils;
import ${package}.utils.TextUtils;
import ${package}.utils.LambdaUtils;

/**
 * This class is used to test the utility classes
 * 
 * @author lucapompei
 *
 */
@SpringBootTest
public class TestUtilities {

	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TestUtilities.class);

	/**
	 * Test utility classes
	 * 
	 * @param <T>
	 */
	@DisplayName("Test utilities")
	@ParameterizedTest
	@ValueSource(classes = { AuthConstants.class, CommonConstants.class, DateUtils.class, EndpointConstants.class,
			JsonUtils.class, LambdaUtils.class, RestUtils.class, TextUtils.class })
	public <T> void testConstants(Class<T> cls) throws NoSuchMethodException, SecurityException {
		// Getting class name
		String className = cls.getSimpleName();
		LOGGER.info("Testing {} constant class", className);
		// Getting class constructor
		Constructor<T> constructor = cls.getDeclaredConstructor();
		constructor.setAccessible(true);
		// Test class illegal invocation
		Assertions.assertThrows(InvocationTargetException.class, () -> constructor.newInstance(),
				"The class " + className + " is instantiable");
	}

}
