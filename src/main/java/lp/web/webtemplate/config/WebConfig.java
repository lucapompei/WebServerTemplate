package lp.web.webtemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * This config class globally configure the web requests
 * 
 * @author lucapompei
 *
 */
@Configuration
public class WebConfig {

	/**
	 * Defines a common multipart resolver
	 * 
	 * @return a common multipart resolver
	 */
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}

}
