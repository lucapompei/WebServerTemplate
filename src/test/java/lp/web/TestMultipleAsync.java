package lp.web;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lp.web.webtemplate.service.DataService;

/**
 * This class is used to test the context loading
 * 
 * @author lucapompei
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MainApplication.class })
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
