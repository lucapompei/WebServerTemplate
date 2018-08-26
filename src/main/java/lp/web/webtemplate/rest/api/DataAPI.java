package lp.web.webtemplate.rest.api;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lp.web.utils.RestUtils;
import lp.web.webtemplate.constants.EndpointConstants;
import lp.web.webtemplate.service.DataService;

/**
 * This class exposes api to obtain multiple data
 * 
 * @author lucapompei
 *
 */
@RestController
@RequestMapping(EndpointConstants.API_BASE)
public class DataAPI {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataAPI.class);

	/**
	 * The data service
	 */
	@Autowired
	private DataService dataService;

	/**
	 * This method exposes API to show the main application info
	 * 
	 * @return the main application info
	 */
	@GetMapping(value = EndpointConstants.DATA)
	public ResponseEntity<String> getAbout() {
		LOGGER.info("Requesting for " + EndpointConstants.DATA);
		Date beginTime = new Date();
		List<Integer> response = dataService.getFakeData();
		RestUtils.logSpentTime(EndpointConstants.DATA, beginTime);
		return RestUtils.getResponseEntity(response);
	}

}
