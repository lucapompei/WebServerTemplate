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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
	 * The api timeout
	 */
	private static final int API_TIMEOUT = 10000;

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
		requestFactory.setConnectionRequestTimeout(API_TIMEOUT);
		requestFactory.setConnectTimeout(API_TIMEOUT);
		return builder
				.requestFactory(() -> new BufferingClientHttpRequestFactory(requestFactory))
                .interceptors((request, body, execution) -> {
                    String payload = new String(body);
                    if (TextUtils.isNullOrEmpty(payload)) {
                        LOGGER.info(String.format("Sending %s request to %s with header %s",
                                request.getMethod(), request.getURI(), request.getHeaders()));
                    } else {
                        LOGGER.info(String.format("Sending %s request to %s with payload %s with header %s",
                                request.getMethod(), request.getURI(), payload, request.getHeaders()));
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
		CustomHttpServletRequestWrapper requestWrapper = null;

		// Handle CORS
		handleCORS(request, response);

		// Configure the logger to uniquely record the request
		MDC.clear();
		MDC.put("uuid", UUID.randomUUID().toString());

		// Find servlet name
		String endpoint = EndpointConstants.ROOT;
		String name = "servlet";
		String payload = "";
		#if (${javaVersion} != '17')
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = ((HttpServletRequest) request);
			String method = req.getMethod();
			endpoint = req.getRequestURI();
            name = String.format("[%s] %s", req.getMethod(), endpoint);
            if (!TextUtils.isNullOrEmpty(req.getQueryString())) {
                name += "?" + req.getQueryString();
            }
			if (
					(HttpMethod.POST.name().equals(method) || HttpMethod.PUT.name().equals(method))
					&& !endpoint.contains(EndpointConstants.LOGIN)
					&& (req.getHeader(HttpHeaders.CONTENT_TYPE) == null || !req.getHeader(HttpHeaders.CONTENT_TYPE).contains(MediaType.MULTIPART_FORM_DATA_VALUE))
			) {
				requestWrapper = new CustomHttpServletRequestWrapper(req);
				payload += " with payload: " + requestWrapper.getBody();
			}
		#end
		#if (${javaVersion} == '17')
		if (request instanceof HttpServletRequest req) {
			String method = req.getMethod();
			endpoint = req.getRequestURI();
			name = String.format("[%s] %s", req.getMethod(), endpoint);
            if (!TextUtils.isNullOrEmpty(req.getQueryString())) {
                name += "?" + req.getQueryString();
            }
			if (
					(HttpMethod.POST.name().equals(method) || HttpMethod.PUT.name().equals(method))
					&& !endpoint.contains(EndpointConstants.LOGIN)
					&& (req.getHeader(HttpHeaders.CONTENT_TYPE) == null || !req.getHeader(HttpHeaders.CONTENT_TYPE).contains(MediaType.MULTIPART_FORM_DATA_VALUE))
			) {
				requestWrapper = new CustomHttpServletRequestWrapper(req);
				payload += " with payload: " + requestWrapper.getBody();
			}
		#end
		}
		if (EndpointConstants.ROOT.equals(endpoint)) {
			// Handle request
			chain.doFilter(request, response);
		} else {
			LOGGER.info("Requesting for {}{}", name, payload);
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
