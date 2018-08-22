package lp.web.utils;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * This class exposes utils to handle json string
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
	 * Private constructor for an utility class, construct a new {@code JsonUtils}
	 */
	private JsonUtils() {
		throw new IllegalAccessError();
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
			instance = new ObjectMapper();
			instance.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		}
		return instance;
	}

	/**
	 * Create a Jackson converter instance using an instance of {@link ObjectMapper}
	 * for json conversion
	 *
	 * @return an instance of {@link JacksonConverterFactory}
	 */
	public static JacksonConverterFactory getConverterInstance() {
		return JacksonConverterFactory.create(getInstance());
	}

	/**
	 * Convert the given object to a json string
	 *
	 * @param object,
	 *            the object to be converted into a json string
	 * @return a json string representation of the given object or {@code null} if
	 *         some exception occurs during conversion
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
	 * Convert the given json string to its represented object
	 *
	 * @param <T>,
	 *            generic type
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

	/**
	 * Convert the given input stream string to its represented object
	 *
	 * @param <T>,
	 *            generic type
	 * @param inputStream,
	 *            the input stream from which re-create the represented object
	 * @param cls,
	 *            the class of the represented object used to re-create it
	 * @return the object represented by the json string or {@code null} if some
	 *         exception occurs during conversion
	 */
	public static <T> T fromInputStream(InputStream inputStream, Class<T> cls) {
		try {
			return getInstance().readValue(inputStream, cls);
		} catch (Exception e) {
			LOGGER.error("Error during reading json", e);
			return null;
		}
	}

}