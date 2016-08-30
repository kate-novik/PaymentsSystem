package by.it.novik.dao;

import by.it.novik.pojos.*;
import by.it.novik.util.AccountState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public class AccountDaoTest {

    private static User user = null;
    private static Role role = null;
    private static Account account = null;
    private static Address address = null;
    private static Passport passport = null;
    private static UserDao userDao = null;
    private static RoleDao roleDao = null;
    private static AccountDao accountDao = null;
    @Before
    public void initialize () {
        userDao = CreateDao.getDao().getUserDao();
        roleDao = CreateDao.getDao().getRoleDao();
        accountDao = CreateDao.getDao().getAccountDao();

        address = new Address();
        address.setCity("Grodno");
        address.setStreet("Lenina");
        address.setHome("2");
        address.setFlat(10);

        passport = new Passport();
        passport.setNumber("MP1278956");
        passport.setDateOfIssue(Date.valueOf("2014-03-10"));
        passport.setIssued("Grodnenski ROVD");

        user = new User();
        user.setFirstName("Peter");
        user.setMiddleName("Ivanovich");
        user.setLastName("Petrov");
        user.setAddress(address);
        user.setEmail("pet@mail.ru");
        user.setPassport(passport);
        user.setLogin("petrov");
        user.setPassword("123");
        user.setPhone("375447244848");
        user.setSalt("356hgdf8776gjgk");

        role = new Role();
        role.setRole("user");

        user.setRole(role);

        account = new Account();
        account.setBalance(500);
        account.setState(AccountState.WORKING);
        account.setUser(user);

        user.getAccounts().add(account);

        address.setUser(user);
        passport.setUser(user);


    }

    @Test
    public void getAccountsByUserTest() throws Exception {
        userDao.saveOrUpdate(user);
        userDao.session.flush();
        userDao.session.evict(user);
        List<Account> accounts = accountDao.getAccountsByUser(user);
        Assert.assertEquals("Don't equals accounts!", accounts.iterator().next(), user.getAccounts().iterator().next());
    }


    @Test
    public void getAllAccountsTest() throws Exception {
        user.setLogin("petrovich");
        userDao.saveOrUpdate(user);
        userDao.session.flush();
        userDao.session.evict(user);
        List<Account> accounts = accountDao.getAllAccounts();
        Assert.assertNotNull(accounts);
    }

    @Test
    public void getLockedAccountsTest() throws Exception {
        user.setLogin("pppp");
        userDao.saveOrUpdate(user);
        user.getAccounts().iterator().next().setState(AccountState.LOCKED);
        userDao.saveOrUpdate(user);
        userDao.session.flush();
        userDao.session.evict(user);
        List<Account> accounts = accountDao.getLockedAccounts();
        Assert.assertEquals("Don't equals states of accounts!",accounts.iterator().next().getState(),
                user.getAccounts().iterator().next().getState());
    }

    @Test
    public void deleteTest() throws Exception {
        user.setLogin("petrovP");
        userDao.saveOrUpdate(user);
        user.getAccounts().iterator().next().setState(AccountState.DELETED);
        userDao.saveOrUpdate(user);
        userDao.session.flush();
        userDao.session.evict(user);
        Assert.assertEquals("Don't equals states of accounts!", AccountState.DELETED,
                user.getAccounts().iterator().next().getState());
    }


}