#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package};

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import ${package}.model.ApplicationUser;
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

	/**
	 * Test date utils
	 */
	@DisplayName("Test utils")
	@ParameterizedTest
	@MethodSource("getUtilsData")
	public void testDateUtils(String utilData) {
		// Test data
		Assertions.assertNotNull(utilData);
	}

	/**
	 * Get a stream of arguments to test util static methods
	 * 
	 * @return
	 */
	public static Stream<Arguments> getUtilsData() {
		return Stream.of(Arguments.of(DateUtils.getCurrentWeek()), Arguments.of(DateUtils.getFormattedDate(new Date())),
				Arguments.of(DateUtils.getFormattedDate(new Date())),
				Arguments.of(LambdaUtils.sortMap(null).toString()),
				Arguments.of(LambdaUtils.sortMap(new HashMap<>()).toString()),
				Arguments.of(LambdaUtils.distinctByKey(ApplicationUser::getId).toString()),
				Arguments.of(RestUtils.getHeaders().toString()),
				Arguments.of(String.valueOf(TextUtils.isNullOrEmpty(null))), Arguments.of(JsonUtils.toJson("")),
				Arguments.of(JsonUtils.fromJson("[]", String[].class).toString()),
				Arguments.of(JsonUtils.fromInputStream(IOUtils.toInputStream("[]"), String[].class).toString()));

	}

}
