package lp.web.webtemplate.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * This service handles data requests
 * 
 * @author lucapompei
 *
 */
@Service
public class DataService {

	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataService.class);

	/**
	 * Retrieve some fake data
	 * 
	 * @return some data
	 */
	public List<Integer> getFakeData() {
		List<Integer> data = new ArrayList<>();
		long beginTime = new Date().getTime();
		List<CompletableFuture<Void>> futureRequests = new ArrayList<>();
		int dataSize = 10;
		for (int i = 0; i < dataSize; i++) {
			CompletableFuture<Void> futureRequest = CompletableFuture.supplyAsync(() -> {
				int randomNumber = new Random().nextInt(5000);
				try {
					Thread.sleep(randomNumber);
					LOGGER.info("Computation: {}", String.valueOf(randomNumber));
					return randomNumber;
				} catch (Exception e) {
					LOGGER.warn("Unexpected error, a default value will be used");
					return randomNumber;
				}
			}).thenAccept(response -> data.add(response));
			futureRequests.add(futureRequest);
		}
		try {
			CompletableFuture.allOf(futureRequests.toArray(new CompletableFuture[futureRequests.size()])).get();
			long endTime = new Date().getTime();
			LOGGER.info("EndTime: {}", String.valueOf(endTime - beginTime));
		} catch (Exception e) {
			LOGGER.error("Unable to supply multiple parallel requests");
		}
		return data;
	}

}
