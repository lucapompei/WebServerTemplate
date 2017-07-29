package lp.web.webtemplate.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This config class globally configures the spring security module, so it
 * requires authentication to every URL in the application
 * 
 * @author lucapompei
 *
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${security_enabled:true}")
	private boolean securityEnabled;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (this.securityEnabled) {
			http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login");
		} else {
			http.authorizeRequests().anyRequest().permitAll();
		}
	}
}
