package lp.web.webtemplate.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

/**
 * This config class globally configure the http requests made by the customized
 * rest template
 * 
 * @author lucapompei
 *
 */
@Configuration
public class HttpConfig implements ClientHttpRequestInterceptor {

	/**
	 * The application name
	 */
	@Value("${app_name}")
	private String appName;

	/**
	 * The application version
	 */
	@Value("${app_version}")
	private String appVersion;

	/**
	 * It overrides the http interceptor definition
	 */
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		// Add custom headers
		request.getHeaders().add("AppName", this.appName);
		request.getHeaders().add("AppVersion", this.appVersion);
		return execution.execute(request, body);
	}

	/**
	 * Configures and returns a customized rest template
	 * 
	 * @return a customized rest template
	 */
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(Arrays.asList(this));
		return restTemplate;
	}

}
