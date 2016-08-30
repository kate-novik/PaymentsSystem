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
public class PaymentDaoTest {

    private static User user = null;
    private static Role role = null;
    private static Account account = null;
    private static Account account2 = null;
    private static Address address = null;
    private static Passport passport = null;
    private static Payment payment = null;
    private static UserDao userDao = null;
    private static RoleDao roleDao = null;
    private static AccountDao accountDao = null;
    private static PaymentDao paymentDao = null;

    @Before
    public void initialize () {
        userDao = CreateDao.getDao().getUserDao();
        roleDao = CreateDao.getDao().getRoleDao();
        accountDao = CreateDao.getDao().getAccountDao();
        paymentDao = CreateDao.getDao().getPaymentDao();

        address = new Address();
        address.setCity("Grodno");
        address.setStreet("Lenina");
        address.setHome("2");
        address.setFlat("10");

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
        user.setLogin("pppp65");
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

        account2 = new Account();
        account2.setBalance(600);
        account2.setState(AccountState.WORKING);
        account2.setUser(user);

        user.getAccounts().add(account);
        user.getAccounts().add(account2);

        address.setUser(user);
        passport.setUser(user);

        payment = new Payment();
        payment.setDescription("Refilling");
        payment.setPayDate(Date.valueOf("2014-03-10"));
        payment.setAmountPayment(200);
        payment.setAccountSource(account);
        payment.setAccountDestination(account2);

    }

    @Test
    public  void createTest () throws Exception{
        userDao.saveOrUpdate(user);
        paymentDao.saveOrUpdate(payment);
        userDao.session.flush();
        Assert.assertNotNull("Payment did't save!", paymentDao.session.get(Payment.class,payment.getId()));

    }

        @Test
    public void getPaymentsByUserTest() throws Exception {
            user.setLogin("tefk");
            userDao.saveOrUpdate(user);
            paymentDao.saveOrUpdate(payment);
            userDao.session.flush();
            userDao.session.evict(user);
            paymentDao.session.evict(payment);
            List<Payment> payments = paymentDao.getPaymentsByUser(user);
            Assert.assertEquals("Don't equals payments!", payments.iterator().next(), payment);
    }

    @Test
    public void getPaymentsByAccountTest() throws Exception {
        user.setLogin("Ppetya");
        userDao.saveOrUpdate(user);
        paymentDao.saveOrUpdate(payment);
        userDao.session.flush();
        userDao.session.evict(user);
        paymentDao.session.evict(payment);
        List<Payment> payments = paymentDao.getPaymentsByAccount(account);
        Assert.assertEquals("Don't equals payments!", payments.iterator().next(), payment);
    }

    @Test
    public void getAllPaymentsTest() throws Exception {
        user.setLogin("Ppetr2");
        userDao.saveOrUpdate(user);
        paymentDao.saveOrUpdate(payment);
        userDao.session.flush();
        userDao.session.evict(user);
        paymentDao.session.evict(payment);
        List<Payment> payments = paymentDao.getAllPayments();
        Assert.assertEquals("Don't equals payments!", payments.iterator().next(), payment);
    }

}