package lp.web;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lp.web.webtemplate.service.DataService;

/**
 * This class is used to test the context loading
 * 
 * @author lucapompei
 *
 */
@ContextConfiguration(locations = { "classpath:META-INF/spring/applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMultipleAsync {

	@Autowired
	private DataService dataService;

	/**
	 * Test the context loading
	 */
	@Test
	public void testContext() {
		List<Integer> data = dataService.getFakeData();
		System.out.println(data);
	}

}
