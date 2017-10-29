package lp.web.webtemplate.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lp.web.webtemplate.controller.Endpoints;

/**
 * This config class globally configures the spring security module, so it
 * requires authentication to every URL in the application
 * 
 * @author lucapompei
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * This variable allows to enable/disable the spring security module
	 */
	@Value("${security_enabled:true}")
	private boolean securityEnabled;

	/**
	 * This variable allows to enable/disable the security controls to api endpoints
	 */
	@Value("${security_api_enabled:false}")
	private boolean securityApiEnabled;

	/**
	 * It configures the authentication manager used to authenticate the http
	 * requests
	 * 
	 * @param auth,
	 *            the authentication manager builder
	 * @throws Exception,
	 *             if something goes wrong
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}

	/**
	 * This method configures the security, enabling or disabling it and permitting
	 * or denying the http requests
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (this.securityEnabled) {
			// disable CSRF
			http.csrf().disable();
			if (!this.securityApiEnabled) {
				// permit all api endpoints
				http.authorizeRequests().antMatchers(Endpoints.API_BASE + "/**").permitAll();
			}
			// permit login and logout, require authentication for each other endpoint
			http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage(Endpoints.LOGIN)
					.permitAll().and().logout().logoutUrl(Endpoints.LOGOUT).logoutSuccessUrl(Endpoints.LOGIN)
					.permitAll();
		} else {
			// permit all
			http.authorizeRequests().anyRequest().permitAll();
		}
	}

}
