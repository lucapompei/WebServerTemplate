#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package};

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

import ${package}.utils.JsonUtils;
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
	 * Test sorting map
	 */
	@DisplayName("Test sorting map")
	@Test
	public void testSortingMap() {
		// Prepare case for null map
		Map<String, Long> nullMap = null;
		Map<String, Long> emptyMap = new HashMap<>();
		Map<String, Long> nullMapSorted = LambdaUtils.sortMap(nullMap);
		Assertions.assertTrue(nullMapSorted != null && emptyMap.size() == nullMapSorted.size(), "Unable to sort null map");
		// Prepare case for not null map
		Map<String, Long> notEmptyMap = new LinkedHashMap<>();
		notEmptyMap.put("a", 1L);
		notEmptyMap.put("b", 2L);
		notEmptyMap.put("c", 1L);
		Map<String, Long> notEmptyMapOrdered = new LinkedHashMap<>();
		notEmptyMapOrdered.put("b", 2L);
		notEmptyMapOrdered.put("a", 1L);
		notEmptyMapOrdered.put("c", 1L);
		Map<String, Long> notEmptyMapSorted = LambdaUtils.sortMap(notEmptyMap);
	}

	/**
	 * Test distinct by key
	 */
	@DisplayName("Test distinct by key")
	@Test
	public void testDistinctByKey() {
		// Prepare cases
		ApplicationUser first = new ApplicationUser();
		first.setId(1);
		ApplicationUser second = new ApplicationUser();
		first.setId(1);
		ApplicationUser third = new ApplicationUser();
		first.setId(2);
		List<ApplicationUser> startingUsers = Arrays.asList(first, second, third);
		List<ApplicationUser> distinctUsers = Arrays.asList(first, third);
		// Filtering starting data
		List<ApplicationUser> finalUsers = startingUsers.stream()
				.filter(LambdaUtils.distinctByKey(ApplicationUser::getId)).collect(Collectors.toList());
		// Testing result
		Assertions.assertTrue(distinctUsers.size() == finalUsers.size(), "Final and filtered list are not the same size");
		boolean areEquals = true;
		int distinctUsersSize = distinctUsers.size();
		for (int i = 0; i < distinctUsersSize; i++) {
			if (distinctUsers.get(i) != null && finalUsers.get(i) != null
					&& Long.compare(distinctUsers.get(i).getId(), finalUsers.get(i).getId()) != 0) {
				areEquals = false;
			}
		}
		Assertions.assertTrue(areEquals, "Final and filtered list have not the same order");
	}

	/**
	 * Test null text verification
	 */
	@DisplayName("Test null text verification")
	@Test
	public void testNullTextVerification() {
		// Prepare cases
		String nullText = null;
		String emptyText = "";
		String notEmptyText = File.separator;
		Assertions.assertTrue(TextUtils.isNullOrEmpty(nullText), "Null text not identified");
		Assertions.assertTrue(TextUtils.isNullOrEmpty(emptyText), "Empty text not identified");
		Assertions.assertTrue(!TextUtils.isNullOrEmpty(notEmptyText), "Not empty text not identified");
	}

	/**
	 * Test base header generation
	 */
	@DisplayName("Test base header generation")
	@Test
	public void testBaseHeadersGeneration() {
		// Assert generation
		Assertions.assertNotNull(RestUtils.getHeaders(), "Base headers not properly generated");
	}

	/**
	 * Test date utilities
	 */
	@DisplayName("Test date utilities")
	@ParameterizedTest
	@MethodSource("getDataUtils")
	public void testDateUtils(String data) {
		// Assert data
		Assertions.assertNotNull(data, "Date utils not properly handled");
	}

	/**
	 * Get a stream of arguments to test date utilities static methods
	 * 
	 * @return
	 */
	public static Stream<Arguments> getDataUtils() {
		return Stream.of(Arguments.of(DateUtils.getCurrentWeek()),
				Arguments.of(DateUtils.getFormattedDate(new Date())));

	}

	/**
	 * Test JSON utilities
	 *
	 * @param <T>
	 * @param data
	 * @param expectedData
	 */
	@DisplayName("Test JSON utils")
	@ParameterizedTest
	@MethodSource("getJsonUtils")
	public <T> void testJsonUtils(T data, T expectedData) {
		// Assert data
		Assertions.assertEquals(JsonUtils.toJson(data), JsonUtils.toJson(expectedData), "JSON utils not properly handled");
	}

	/**
	 * Get a stream of arguments to test JSON utilities static methods
	 * 
	 * @return
	 */
	public static Stream<Arguments> getJsonUtils() {
		Object emptyObject = new Object();
		String emptyJson = "{}";
		return Stream.of(
				// Empty object to empty JSON
				Arguments.of(JsonUtils.toJson(emptyObject), emptyJson),
				// Empty JSON to wrong class
				Arguments.of(JsonUtils.fromJson(emptyJson, String.class), null),
				// Empty JSON to right class
				Arguments.of(JsonUtils.fromJson(emptyJson, Object.class), emptyObject),
				// Empty JSON input stream to wrong class
				Arguments.of(JsonUtils.fromInputStream(IOUtils.toInputStream(emptyJson), String.class), null),
				// Empty JSON input stream to right class
				Arguments.of(JsonUtils.fromInputStream(IOUtils.toInputStream(emptyJson), Object.class), emptyObject));

	}

}
