package lp.web.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lp.web.webtemplate.constants.CommonConstants;

/**
 * This class exposes utilities to handle Lambda expressions
 * 
 * @author lucapompei
 *
 */
public class LambaUtils {



	/**
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LambaUtils.class);



	/**
	 * Private constructor for an utility class
	 */
	private LambaUtils() {
		throw new IllegalAccessError(CommonConstants.STANDARD_MESSAGE_UTILITY_CLASS);
	}



	/**
	 * This method sort the given map
	 * 
	 * @param map, the map to sort
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
	 * Filter by the given function
	 * 
	 * @param key, the function used to filter
	 * @return a filter by the given function
	 */
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> key) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(key.apply(t), Boolean.TRUE) == null;
	}

}
