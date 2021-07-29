#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * This class configures the swagger generation
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {

	/**
	 * Creates the Docket bean
	 * 
	 * @return the Docket bean
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("^((?!error.*).)*$")).build();
	}
}
