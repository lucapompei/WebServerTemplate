#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ${package}.constants.CommonConstants;

/**
 * This class exposes utilities to handle json string
 *
 * @author lucapompei
 */
public class JsonUtils {



	/**
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);



	/**
	 * Jackson instance
	 */
	private static ObjectMapper instance;



	/**
	 * Private constructor for an utility class
	 */
	private JsonUtils() {
		throw new IllegalAccessError(CommonConstants.STANDARD_MESSAGE_UTILITY_CLASS);
	}



	/**
	 * Initialized the static instance
	 */
	private static synchronized void initializeMapper() {
		instance = new ObjectMapper();
		instance.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}



	/**
	 * Static method used to initialize and retrieve the {@link ObjectMapper}
	 * instance
	 *
	 * @return a valid {@link ObjectMapper} instance
	 */
	private static ObjectMapper getInstance() {
		if (instance == null) {
			// lazy initialization
			initializeMapper();
		}
		return instance;
	}



	/**
	 * Converts the given object to a json string
	 *
	 * @param object, the object to be converted into a json string
	 * @return a json string representation of the given object or {@code null} if
	 *         some exception occurs during conversion
	 */
	public static String toJson(Object object) {
		try {
			return getInstance().writeValueAsString(object);
		} catch (Exception e) {
			LOGGER.error("Error during converting to json: {}", e.getMessage());
			return null;
		}
	}



	/**
	 * Converts the given json string to its represented object
	 *
	 * @param <T>, generic type
	 * @param json, the json string from which re-create the represented object
	 * @param cls, the class of the represented object used to re-create it
	 * @return the object represented by the json string or {@code null} if some
	 *         exception occurs during conversion
	 */
	public static <T> T fromJson(String json, Class<T> cls) {
		try {
			return getInstance().readValue(json, cls);
		} catch (Exception e) {
			LOGGER.error("Error during reading json: {}", e.getMessage());
			return null;
		}
	}



	/**
	 * Converts the given input stream string to its represented object
	 *
	 * @param <T>, generic type
	 * @param inputStream, the input stream from which re-create the represented
	 *        object
	 * @param cls, the class of the represented object used to re-create it
	 * @return the object represented by the json string or {@code null} if some
	 *         exception occurs during conversion
	 */
	public static <T> T fromInputStream(InputStream inputStream, Class<T> cls) {
		try {
			return getInstance().readValue(inputStream, cls);
		} catch (Exception e) {
			LOGGER.error("Error during reading json: {}", e.getMessage());
			return null;
		}
	}

}
