#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.configs.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import jakarta.servlet.http.HttpServletResponse;

import ${package}.constants.EndpointConstants;

/**
 * This config class globally configures the spring security module, so it
 * requires authentication to every URL in the application
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * This variable allows the security configuration based on the jwt
	 * authentication
	 */
	@Value("${symbol_dollar}{security.security_jwtauth_enabled:false}")
	private boolean securityJwtAuthEnabled;

	/**
	 * The secret key used for the jwt auth
	 */
	@Value("${symbol_dollar}{security.security_jwtauth_secretkey}")
	private String jwtSecretKey;

	/**
	 * The expiration time used for the jwt auth
	 */
	@Value("${symbol_dollar}{security.security_jwtauth_minexpirationtime:5}")
	private long jwtMinExpirationTime;

	/**
	 * The application user details service
	 */
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * Configure the password encoder
	 * 
	 * @return the configured password encoder
	 */
	@Bean
	public static PasswordEncoder configurePasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * It configures the authentication manager used to authenticate the http
	 * requests
	 * 
	 * @param auth, the authentication manager builder
	 * @param userDetailsService, the user details service
	 * @throws Exception, if something goes wrong
	 */
	@Autowired
	public void configureGlobal(
			AuthenticationManagerBuilder auth, UserDetailsService userDetailsService
	) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(configurePasswordEncoder());
	}

	/**
	 * This method configures the security, enabling or disabling it and permitting
	 * or denying the http requests
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration config) throws Exception {
		if (securityJwtAuthEnabled) {
			SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
			AuthenticationManager authenticationManager = config.getAuthenticationManager();
			http.csrf(AbstractHttpConfigurer::disable)
					.authorizeHttpRequests(authorizeRequests ->
							authorizeRequests
									.requestMatchers(HttpMethod.OPTIONS).permitAll()
									.requestMatchers(HttpMethod.GET, EndpointConstants.ROOT).permitAll()
									.requestMatchers(HttpMethod.GET, EndpointConstants.ABOUT).permitAll()
									.requestMatchers(HttpMethod.POST, EndpointConstants.LOGIN).permitAll()
									.requestMatchers(EndpointConstants.SWAGGER).permitAll()
									.requestMatchers(EndpointConstants.SWAGGER_JSON).permitAll()
									.anyRequest().authenticated()
					)
					.sessionManagement(sessionManagement ->
							sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					)
					.addFilter(new JwtAuthenticationFilter(authenticationManager, key, jwtMinExpirationTime * 1000))
					.addFilter(new JwtAuthorizationFilter(authenticationManager, key))
					.exceptionHandling(exceptionHandling ->
							exceptionHandling.authenticationEntryPoint(unauthorizedEntryPoint())
					);
		} else {
			// permit each request (no auth)
			http.csrf(AbstractHttpConfigurer::disable)
					.authorizeHttpRequests(authorizeRequests ->
							authorizeRequests.anyRequest().permitAll()
					);
		}
		return http.build();
	}

	/**
	 * The authentication entry point used to return 401 when JWT is enabled and the
	 * authorization is denied
	 * 
	 * @return the authentication entry point
	 */
	private AuthenticationEntryPoint unauthorizedEntryPoint() {
		return (request, response, authException) -> response.sendError(
				HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()
		);
	}

}
