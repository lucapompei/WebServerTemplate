package lp.web.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

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
	private static final Logger LOGGER = Logger.getLogger(LambaUtils.class);

	/**
	 * This method sort the given {@link map}
	 * 
	 * @param map,
	 *            the map to sort
	 * @return a sorted map
	 */
	public static Map<String, Long> sortMap(Map<String, Long> map) {
		if (map == null) {
			LOGGER.debug("The map to sort is null");
			return map;
		}
		// prepare the sorted map
		Map<String, Long> sortedMap = new LinkedHashMap<>();
		map.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
				.forEachOrdered(e -> sortedMap.put(e.getKey(), e.getValue()));
		return sortedMap;
	}

	/**
	 * Filter a list of generic object using the given {@link predicate}
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
			return null;
		}
		return objects.parallelStream().filter(predicate).collect(Collectors.toList());
	}

}
