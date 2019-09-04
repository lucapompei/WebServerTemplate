#set($symbol_pound='#')#set($symbol_dollar='$')#set($symbol_escape='\')
package ${package}.config;

import javax.servlet.Filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class WebConfig implements Filter {



	/**
	 * The logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);



	/**
	 * Defines a common multipart resolver
	 * 
	 * @return a common multipart resolver
	 */
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}



	/**
	 * Intercepts all requests and log the elapsed time took by its execution
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		// Find servlet name
		String name = "servlet";
		if (request instanceof HttpServletRequest) {
			name = ((HttpServletRequest) request).getRequestURI();
		}
		LOGGER.debug("Requesting for {}", name);
		// Get start time
		long startTime = System.currentTimeMillis();
		// Handle request
		chain.doFilter(request, response);
		// Calculate elapsed time
		long elapsed = System.currentTimeMillis() - startTime;
		// Log result
		LOGGER.info("Returned response for {} in {} ms", name, elapsed);
	}

}
