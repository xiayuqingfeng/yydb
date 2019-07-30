package base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:/web-front-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseTest {

}
