package lp.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * This class is used to http configuration
 * 
 * @author lucapompei
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MainApplication.class })
public class TestHttpInterceptor {

	/**
	 * It tests the http interceptor
	 */
	@Test
	public void testHttpInterceptor() {
		// restTemplate.setInterceptors(Arrays.asList(new HttpInterceptor()));
		String url = "http://api.openweathermap.org/data/2.5/forecast?q=Bologna,it&appid=10387e20c2b67f1efc038f11549d6dde";
		ResponseEntity<String> response = new RestTemplate().getForEntity(url, String.class);
		if (response != null) {
			System.out.println(response.getBody());
		}
	}

}
