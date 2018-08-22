package lp.web.webtemplate.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

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
	@Value("${application.app_name}")
	private String appName;

	/**
	 * The application version
	 */
	@Value("${application.app_version}")
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

}
