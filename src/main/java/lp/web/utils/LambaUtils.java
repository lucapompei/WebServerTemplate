package lp.web.utils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class exposes utils to handle Lambda expressions
 * 
 * @author lucapompei
 *
 */
public class LambaUtils {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LogManager.getFormatterLogger(LambaUtils.class);

	/**
	 * Private constructor for a new {@code LambaUtils}
	 */
	private LambaUtils() {
		// Empty implementation
	}

	/**
	 * This method sort the given map
	 * 
	 * @param map,
	 *            the map to sort
	 * @return a sorted map
	 */
	public static Map<String, Long> sortMap(Map<String, Long> map) {
		if (map == null) {
			LOGGER.debug("The map to sort is null");
			return new HashMap<>();
		}
		// prepare the sorted map
		Map<String, Long> sortedMap = new LinkedHashMap<>();
		map.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
				.forEachOrdered(e -> sortedMap.put(e.getKey(), e.getValue()));
		return sortedMap;
	}

	/**
	 * Filter a list of generic object using the given predicate
	 * 
	 * @param objects,
	 *            the list of objects to filter
	 * @param predicate,
	 *            the predicate used to filter
	 * @return a filter list of generic objects
	 */
	public static <T> List<T> filterObjects(List<T> objects, Predicate<? super T> predicate) {
		if (objects == null) {
			LOGGER.debug("The list of objects is null");
			return new ArrayList<>();
		}
		return objects.parallelStream().filter(predicate).collect(Collectors.toList());
	}

}
