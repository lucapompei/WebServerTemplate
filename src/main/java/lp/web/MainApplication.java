package lp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * This class represents the main application
 * 
 * @author lucapompei
 *
 */
@PropertySource({ "classpath:application.yml" })
@SpringBootApplication
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
