#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
#if (${withCache} == 'Y')
import org.springframework.cache.annotation.EnableCaching;
#end
import org.springframework.context.annotation.PropertySource;

/**
 * This class represents the main application
 */
@PropertySource({ "classpath:application.yml" })
@SpringBootApplication
#if (${withCache} == 'Y')
@EnableCaching
#end
public class MainApplication {

	/**
	 * Main method used to launch the spring boot application
	 * 
	 * @param args, optional arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
