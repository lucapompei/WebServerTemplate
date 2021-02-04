#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.config.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import ${package}.constants.EndpointConstants;
import ${package}.service.ApplicationUserDetailsService;

/**
 * This config class globally configures the spring security module, so it
 * requires authentication to every URL in the application
 * 
 * @author lucapompei
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * This variable allows the security configuration based on the basic
	 * authentication
	 */
	@Value("${symbol_dollar}{security.security_basicauth_enabled:true}")
	private boolean securityBasicAuthEnabled;

	/**
	 * This variable allows the security configuration based on the jwt
	 * authentication
	 */
	@Value("${symbol_dollar}{security.security_jwtauth_enabled:false}")
	private boolean securityJwtAuthEnabled;

	/**
	 * The secret key used for the jwt auth
	 */
	@Value("${symbol_dollar}{security.security_jwtauth_secretkey:${artifactId}SecretKey!}")
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
	private ApplicationUserDetailsService userDetailsService;

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
	 * @throws Exception, if something goes wrong
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService).passwordEncoder(configurePasswordEncoder());
	}

	/**
	 * This method configures the security, enabling or disabling it and permitting
	 * or denying the http requests
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (this.securityBasicAuthEnabled) {
			// permit form login and require the basic authentication for each other request
			http.authorizeRequests().antMatchers(HttpMethod.GET, EndpointConstants.ROOT).permitAll()
					.antMatchers(HttpMethod.GET, EndpointConstants.ABOUT).permitAll()
					.antMatchers(HttpMethod.GET, EndpointConstants.LOGS).permitAll()
					.antMatchers(EndpointConstants.SWAGGER).permitAll().antMatchers(EndpointConstants.SWAGGER_JSON)
					.permitAll().anyRequest().authenticated().and().formLogin().and().httpBasic();
		} else if (this.securityJwtAuthEnabled) {
			// permit form login and require the jwt authentication for each other request
			http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
					.antMatchers(HttpMethod.GET, EndpointConstants.ROOT).permitAll()
					.antMatchers(HttpMethod.GET, EndpointConstants.ABOUT).permitAll()
					.antMatchers(HttpMethod.GET, EndpointConstants.LOGS).permitAll()
					.antMatchers(HttpMethod.POST, EndpointConstants.LOGIN).permitAll()
					.antMatchers(EndpointConstants.SWAGGER).permitAll().antMatchers(EndpointConstants.SWAGGER_JSON)
					.permitAll().anyRequest().authenticated().and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.addFilter(new JwtAuthenticationFilter(authenticationManager(), this.jwtSecretKey,
							this.jwtMinExpirationTime * 1000))
					.addFilter(new JwtAuthorizationFilter(authenticationManager(), this.jwtSecretKey))
					.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());
		} else {
			// permit each request (no auth)
			http.csrf().disable();
			http.authorizeRequests().anyRequest().permitAll();
		}
	}

	/**
	 * The authentication entry point used to return 401 when JWT is enabled and the
	 * authorization is denied
	 * 
	 * @return the authentication entry point
	 */
	private AuthenticationEntryPoint unauthorizedEntryPoint() {
		return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				authException.getMessage());
	}

}
