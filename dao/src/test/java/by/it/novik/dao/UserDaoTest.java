package by.it.novik.dao;

import by.it.novik.pojos.*;
import by.it.novik.util.AccountState;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public class UserDaoTest {

//
//    private static User user2 = null;
//    private static Role role = null;
//    private static Account account = null;
//    private static Address address = null;
//    private static Passport passport = null;
//    private static UserDao userDao = null;
//    private static RoleDao roleDao = null;
//    private static AccountDao accountDao = null;
//
//    @Before
//    public void initialize () {
//        userDao = CreateDao.getDao().getUserDao();
//        roleDao = CreateDao.getDao().getRoleDao();
//        accountDao = CreateDao.getDao().getAccountDao();
//
//        address = new Address();
//        address.setCity("Minsk");
//        address.setStreet("Yankovskogo");
//        address.setHome("6");
//        address.setFlat("105");
//
//        passport = new Passport();
//        passport.setNumber("MP1234567");
//        passport.setDateOfIssue(Date.valueOf("2014-05-15"));
//        passport.setIssued("Minskiy ROVD");
//
//        user2 = new User();
//        user2.setFirstName("Anna");
//        user2.setMiddleName("Antonovna");
//        user2.setLastName("Ivanova");
//        user2.setAddress(address);
//        user2.setEmail("ret@mail.ru");
//        user2.setPassport(passport);
//        user2.setLogin("ter");
//        user2.setPassword("3af8212b2bee9ac54115a6fc5d455ca8");
//        user2.setPhone("375447547878");
//        user2.setSalt("•9uk¤ЄRщhE@К!х");
//
//        role = new Role();
//        role.setRole("user");
//
//        user2.setRole(role);
//
//        account = new Account();
//        account.setBalance(256);
//        account.setState(AccountState.WORKING);
//        account.setUser(user2);
//
//        user2.getAccounts().add(account);
//
//        address.setUser(user2);
//        passport.setUser(user2);
//
//
//    }
//
//    @Test
//    public void testSaveOrUpdate() throws Exception {
//        userDao.saveOrUpdate(user2);
//        userDao.session.flush();
//        Assert.assertNotNull("User did't save!", userDao.session.get(User.class,user2.getId()));
//    }

//    @Test
//    public void testDelete() throws Exception {
//        userDao.saveOrUpdate(user2);
//        //accountDao.delete(account.getId());
//        userDao.delete(user2.getId());
//        Assert.assertNull("User did't delete!", userDao.session.get(User.class,user2.getId()));
//    }

//    @Test
//    public void testDelete2() throws Exception {
//        userDao.saveOrUpdate(user2);
//        Long id = user2.getAccounts().iterator().next().getId();
//        //user2.setAccounts(null);
//        //user2.getAccounts().remove(user2.getAccounts().iterator().next());
//        user2.getAccounts().clear();
//        userDao.saveOrUpdate(user2);
//        Assert.assertEquals("Account did't delete!", 0, user2.getAccounts().size());
//    }

//    @Test
//    public void testLoad() throws Exception {
//        userDao.saveOrUpdate(user2);
//        userDao.session.evict(user2);
//        User userNew = (User) userDao.session.load(User.class,user2.getId());
//        Assert.assertEquals("User did't load!", userNew.getId(), user2.getId());
//    }
//
//    @Test
//    public void testGet() throws Exception {
//        userDao.saveOrUpdate(user2);
//        userDao.session.evict(user2);
//        Assert.assertNotNull("User did't get!", userDao.session.get(User.class,user2.getId()));
//    }

//    @Test
//    public void testCheckFlush() throws Exception {
//        userDao.saveOrUpdate(user2);
//        Assert.assertTrue("Don't work flush!", userDao.checkFlush(user2.getId(),"mail"));
//    }
//
//    @Test
//    public void testCheckRefresh() throws Exception {
//        userDao.saveOrUpdate(user2);
//        Assert.assertTrue("Don't work refresh!", userDao.checkRefresh(user2.getId(),"mail"));
//    }

//    @Test
//    public void testCheckLoadAddress() throws Exception {
//        userDao.saveOrUpdate(user2);
//        userDao.session.flush();
//        userDao.session.evict(user2);
//        User userNew = (User) userDao.session.load(User.class,user2.getId());
//        Address newAddress =  userNew.getAddress();
//        Assert.assertEquals("Don't equals objects Address!", newAddress, user2.getAddress());
//    }
//
//    @Test
//    public void testCheckLoadPassport() throws Exception {
//        userDao.saveOrUpdate(user2);
//        userDao.session.flush();
//        userDao.session.evict(user2);
//        User userNew = (User) userDao.session.load(User.class,user2.getId());
//        Passport newPassport =  userNew.getPassport();
//        Assert.assertEquals("Don't equals objects Passport!", newPassport, user2.getPassport());
//    }

//    @Test
//    public void findByLoginTest() throws Exception {
//        user2.setLogin("ivan");
//        userDao.saveOrUpdate(user2);
//        userDao.session.flush();
//        userDao.session.evict(user2);
//        User userTest = userDao.findByLogin("ivan");
//        Assert.assertEquals("Don't equal's objects",userTest, user2);
//    }
//
//    @Test
//    public void findByLoginAndPassTest() throws Exception {
//        user2.setLogin("petr");
//        userDao.saveOrUpdate(user2);
//        userDao.session.flush();
//        userDao.session.evict(user2);
//        User userTest = userDao.findByLoginAndPass("petr","123456");
//        Assert.assertEquals("Don't equal's objects",userTest, user2);
//    }
//
//    @Test
//    public void getAllUsersTest() throws Exception {
//        userDao.saveOrUpdate(user2);
//        //List<User> users = new ArrayList<User>();
//       // users.add(user2);
//        userDao.session.flush();
//        userDao.session.evict(user2);
//        List<User> usersTest = userDao.getAllUsers();
//        Assert.assertNotNull("Don't equal's objects",usersTest);
//    }
//
//    @AfterClass
//    public static void closeSession () {
//        userDao.session.close();
//    }

}