package lp.web.webtemplate.controller;

import java.awt.image.SampleModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lp.web.utils.JsonUtils;
import lp.web.utils.RestUtils;
import lp.web.webtemplate.constants.EndpointConstants;

/**
 * This rest controller exposes endpoints to handle some example requests
 * 
 * @author lucapompei
 *
 */
@RestController
public class ExampleController {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleController.class);

	/**
	 * Endpoint for link A
	 *
	 * @return the response for link A
	 */
	@RequestMapping(value = EndpointConstants.LINK_A, method = { RequestMethod.GET,
			RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<String> getHome(@RequestBody(required = false) SampleModel sampleModel,
			@RequestParam(value = "param", required = false) String param) {
		LOGGER.debug("Requesting for link A");
		if (sampleModel != null) {
			LOGGER.debug("Request body: " + JsonUtils.toJson(sampleModel));
		}
		if (param != null) {
			LOGGER.debug("Param in query string: param=" + param);
		}
		return RestUtils.getResponseEntity(true);
	}

}