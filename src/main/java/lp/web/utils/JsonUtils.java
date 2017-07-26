package lp.web.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * This class exposes utils to handle json string
 * 
 * @author lucapompei
 * 
 */
public class JsonUtils {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LogManager.getFormatterLogger(JsonUtils.class);

	/**
	 * Jackson instance
	 */
	private static ObjectMapper INSTANCE;

	/**
	 * Static method used to inizialize and retrive the {@code ObjectMapper}
	 * instance
	 * 
	 * @return a valid {@code ObjectMapper} instance
	 */
	private static ObjectMapper getInstance() {
		if (INSTANCE != null) {
			return INSTANCE;
		} else {
			INSTANCE = new ObjectMapper();
			INSTANCE.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			return INSTANCE;
		}
	}

	/**
	 * Convert the given {@link object} to a json string
	 * 
	 * @param object,
	 *            the object to be converted into a json string
	 * @return a json string representation of the given {@link object} or
	 *         {@code null} if some exception occurs during conversion
	 */
	public static String toJson(Object object) {
		try {
			return getInstance().writeValueAsString(object);
		} catch (Exception e) {
			LOGGER.error("Error during converting to json", e);
			return null;
		}
	}

	/**
	 * Convert the given {@link json} string to its represented object
	 * 
	 * @param json,
	 *            the json string from which re-create the represented object
	 * @param cls,
	 *            the class of the represented object used to re-create it
	 * @return the object represented by the json string or {@code null} if some
	 *         exception occurs during conversion
	 */
	public static <T> T fromJson(String json, Class<T> cls) {
		try {
			return getInstance().readValue(json, cls);
		} catch (Exception e) {
			LOGGER.error("Error during reading json", e);
			return null;
		}
	}

}