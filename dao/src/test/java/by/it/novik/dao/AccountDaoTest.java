package by.it.novik.dao;

import by.it.novik.entities.Account;
import by.it.novik.entities.User;
import by.it.novik.util.AccountState;
import by.it.novik.valueObjects.AccountsFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
public class AccountDaoTest {

    private static User user;

    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountDao accountDao;

    @Before
    public void initializeEntity (){
        user = BuilderEntity.buildUser();
    }

    @Test
    public void getAccountsByUserTest() throws Exception {
        user.setLogin("axf");
        userDao.saveOrUpdate(user);
        userDao.getSession().flush();
        userDao.getSession().evict(user);
        List<Account> accounts = accountDao.getAccountsByUser(user);
        Assert.assertEquals("Don't equals accounts!", accounts.iterator().next(), user.getAccounts().iterator().next());
    }

    @Test
    public void getAllAccountsTest() throws Exception {
        user.setLogin("petr");
        userDao.saveOrUpdate(user);
        userDao.getSession().flush();
        userDao.getSession().evict(user);
        AccountsFilter paymentsFilter = new AccountsFilter();
        paymentsFilter.setMinBalance(200);
        List<Account> accounts = accountDao.getAllAccounts("ASC", 10, 0, paymentsFilter);
        Assert.assertNotNull(accounts);
    }

    @Test
    public void getLockedAccountsTest() throws Exception {
        user.setLogin("pppp");
        userDao.saveOrUpdate(user);
        user.getAccounts().iterator().next().setState(AccountState.LOCKED);
        userDao.saveOrUpdate(user);
        userDao.getSession().flush();
        userDao.getSession().evict(user);
        List<Account> accounts = accountDao.getLockedAccounts();
        Assert.assertEquals("Don't equals states of accounts!",accounts.iterator().next().getState(),
                user.getAccounts().iterator().next().getState());
    }

    @Test
    public void deleteTest() throws Exception {
        user.setLogin("petrP");
        userDao.saveOrUpdate(user);
        user.getAccounts().iterator().next().setState(AccountState.DELETED);
        userDao.saveOrUpdate(user);
        userDao.getSession().flush();
        userDao.getSession().evict(user);
        Assert.assertEquals("Don't equals states of accounts!", AccountState.DELETED,
                user.getAccounts().iterator().next().getState());
    }


}