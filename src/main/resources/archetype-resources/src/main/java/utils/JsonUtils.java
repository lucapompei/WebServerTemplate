{package}.constants.CommonConstants;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class exposes utilities to handle json string
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
     * Private constructor for the utility class
     */
    private JsonUtils() {
        throw new IllegalAccessError(CommonConstants.STANDARD_MESSAGE_UTILITY_CLASS);
    }

    /**
     * Initialized the static instance
     */
    private static synchronized void initializeMapper() {
        instance = new ObjectMapper();
        instance.registerModule(new JavaTimeModule());
        instance.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // Performance optimizations
		instance.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		// Disable features that slow down processing
		instance.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
     * some exception occurs during conversion
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
     * @param <T>,  generic type
     * @param json, the json string from which re-create the represented object
     * @param cls,  the class of the represented object used to re-create it
     * @return the object represented by the json string or {@code null} if some
     * exception occurs during conversion
     */
    public static <T> T fromJson(String json, Class<T> cls) {
        try {
            return getInstance().readValue(json, cls);
        } catch (Exception e) {
            LOGGER.error("Error during reading plain json: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Converts the given json string to its represented array object
     *
     * @param <T>,  generic type
     * @param json, the json string from which re-create the represented object
     * @param c,    the class of the represented object used to re-create it
     * @return the array object represented by the json string or {@code null} if
     * some exception occurs during conversion
     */
    public static <T> List<T> fromJson(String json, TypeReference<List<T>> c) {
        try {
            return getInstance().readValue(json, c);
        } catch (Exception e) {
            LOGGER.error("Error during reading type referenced json: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Converts the given input stream string to its represented object
     *
     * @param <T>,         generic type
     * @param inputStream, the input stream from which re-create the represented
     *                     object
     * @param cls,         the class of the represented object used to re-create it
     * @return the object represented by the json string or {@code null} if some
     * exception occurs during conversion
     */
    public static <T> T fromInputStream(InputStream inputStream, Class<T> cls) {
        try {
            return getInstance().readValue(inputStream, cls);
        } catch (Exception e) {
            LOGGER.error("Error during reading streamed json: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Validates the given data
     *
     * @param validator, the validator to use to validate the data
     * @param cls,       the data to validate
     * @param <T>,       the data class
     */
    public static <T> void validate(Validator validator, T cls) {
        // Applica le validazioni
        Set<ConstraintViolation<T>> violations = validator.validate(cls);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("No valid data: ");
            for (ConstraintViolation<T> violation : violations) {
                errorMessage.append(violation.getPropertyPath())
                        .append(" ")
                        .append(violation.getMessage())
                        .append("; ");
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage.toString());
        }
    }

}
