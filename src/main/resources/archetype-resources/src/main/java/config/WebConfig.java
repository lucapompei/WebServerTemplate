#set($symbol_pound='#')#set($symbol_dollar='$')#set($symbol_escape='\')
package ${package}.config;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * This config class globally configure the web requests
 * 
 * @author lucapompei
 *
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
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
	 * Defines the eTag header filter bean
	 * 
	 * @return the eTag header filter bean
	 */
	@Bean
	public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}

	/**
	 * Handle CORS configuring response
	 * 
	 * @param req
	 * @param res
	 */
	private void handleCORS(ServletRequest req, ServletResponse res) {
		if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Allow-Methods", "POST, PUT, PATCH, GET, OPTIONS, DELETE");
			response.setHeader("Access-Control-Max-Age", "3600");
			String headers = "Content-Type, Accept, X-Requested-With, Authorization, ETag, If-None-Match";
			response.setHeader("Access-Control-Allow-Headers", headers);
			response.setHeader("Access-Control-Expose-Headers", headers);
		}
	}

	/**
	 * Intercepts all requests and log the elapsed time took by its execution
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		// Handle CORS
		handleCORS(request, response);
		// Configure the logger to uniquely record the request
		MDC.clear();
		MDC.put("uuid", UUID.randomUUID().toString() + new Date().getTime());
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
