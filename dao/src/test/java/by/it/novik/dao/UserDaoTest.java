package by.it.novik.dao;

import by.it.novik.entities.*;
import by.it.novik.util.DaoException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import resources.HibernateTestConfig;
import java.util.List;

/**
 * Created by Kate Novik.
 */
@ContextConfiguration(classes = HibernateTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback(true)
public class UserDaoTest {

    private static User user;

    @Autowired
    private UserDao userDao;

    @Before
    public void initializeEntity (){
        user = BuilderEntity.buildUser();
    }

    @Test
    public void saveOrUpdateUserTest () throws DaoException {
        user.setLogin("fgh");
        userDao.saveOrUpdate(user);
        userDao.getSession().evict(user);
        Assert.assertNotNull("User didn't save!", userDao.get(user.getId()));

    }

    @Test
    public void deleteUserTest () throws DaoException {
        user.setLogin("fgll");
        userDao.saveOrUpdate(user);
        userDao.getSession().evict(user);
        userDao.delete(user.getId());
        Assert.assertNull("User didn't delete!", userDao.get(user.getId()));

    }

    @Test
    public void checkLoadAddressTest() throws Exception {
        user.setLogin("cvb");
        userDao.saveOrUpdate(user);
        userDao.getSession().flush();
        userDao.getSession().evict(user);
        User userNew = userDao.get(user.getId());
        Address newAddress =  userNew.getAddress();
        Assert.assertEquals("Don't equals objects Address!", newAddress, user.getAddress());
    }

    @Test
    public void checkLoadPassportTest() throws Exception {
        user.setLogin("qws");
        userDao.saveOrUpdate(user);
        userDao.getSession().flush();
        userDao.getSession().evict(user);
        User userNew = userDao.get(user.getId());
        Passport newPassport =  userNew.getPassport();
        Assert.assertEquals("Don't equals objects Passport!", newPassport, user.getPassport());
    }

    @Test
    public void findByLoginTest() throws Exception {
        user.setLogin("nmk");
        userDao.saveOrUpdate(user);
        userDao.getSession().evict(user);
        User userNew = userDao.findByLogin(user.getLogin());
        Assert.assertEquals("Don't equal's objects",userNew, user);
    }

    @Test
    public void getAllUsersTest() throws Exception {
        user.setLogin("asx");
        userDao.saveOrUpdate(user);
        userDao.getSession().evict(user);
        List<User> usersTest = userDao.getAllUsers();
        Assert.assertNotNull("Don't equal's objects",usersTest);
    }


}