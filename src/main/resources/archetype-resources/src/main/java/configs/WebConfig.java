#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.configs;

import ${package}.constants.EndpointConstants;
import ${package}.utils.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * This config class globally configure the web requests
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
	 * @param req, the incoming request
	 * @param res, the outgoing response
	 */
	private void handleCORS(ServletRequest req, ServletResponse res) {
		#if (${javaVersion} != '17')
		if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
		#end
		#if (${javaVersion} == '17')
		if (req instanceof HttpServletRequest request && res instanceof HttpServletResponse response) {	
		#end
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
     * Defines and customize rest template in order to ignore the SSL validation
     *
     * @param builder, the build from which defines the rest template
     * @return the customized rest template
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		return builder
				.requestFactory(() -> new BufferingClientHttpRequestFactory(requestFactory))
                .interceptors((request, body, execution) -> {
                    String payload = new String(body);
                    if (TextUtils.isNullOrEmpty(payload)) {
                        LOGGER.info(String.format("Sending %s request to %s",
                                request.getMethod(), request.getURI()));
                    } else {
                        LOGGER.info(String.format("Sending %s request to %s with payload %s",
                                request.getMethod(), request.getURI(), payload));
                    }
                    return execution.execute(request, body);
                })
                .build();
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
		MDC.put("uuid", UUID.randomUUID().toString());
		// Find servlet name
		String endpoint = EndpointConstants.ROOT;
		String name = "servlet";
		#if (${javaVersion} != '17')
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = ((HttpServletRequest) request);
			endpoint = req.getRequestURI();
            name = String.format("[%s] %s", req.getMethod(), endpoint);
            if (!TextUtils.isNullOrEmpty(req.getQueryString())) {
                name += "?" + req.getQueryString();
            }
		#end
		#if (${javaVersion} == '17')
		if (request instanceof HttpServletRequest req) {
			endpoint = req.getRequestURI();
			name = String.format("[%s] %s", req.getMethod(), endpoint);
            if (!TextUtils.isNullOrEmpty(req.getQueryString())) {
                name += "?" + req.getQueryString();
            }
		#end
		}
		if (EndpointConstants.ROOT.equals(endpoint)) {
			// Handle request
			chain.doFilter(request, response);
		} else {
			LOGGER.info("Requesting for {}", name);
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

}
