package by.it.novik;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import resources.ServiceTestConfig;

/**
 * Created by Kate Novik.
 */
@ContextConfiguration(classes = ServiceTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback(true)
public class UserServiceTest {
}
