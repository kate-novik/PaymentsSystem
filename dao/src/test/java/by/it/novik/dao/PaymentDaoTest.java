package by.it.novik.dao;

import by.it.novik.entities.Account;
import by.it.novik.entities.Payment;
import by.it.novik.entities.User;
import by.it.novik.util.DaoException;
import by.it.novik.valueObjects.PaymentsFilter;
import org.junit.Assert;
import org.junit.Before;
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
public class PaymentDaoTest {

    private static User user;
    private static Payment payment;

    @Autowired
    private UserDao userDao;
    @Autowired
    private PaymentDao paymentDao;

    @Before
    public void initializeEntity (){
        user = BuilderEntity.buildUser();
        payment = BuilderEntity.buildPayment(user);
    }

    @Test
    public  void saveOrUpdatePaymentTest () throws DaoException {
        user.setLogin("tttfk");
        userDao.saveOrUpdate(user);
        paymentDao.saveOrUpdate(payment);
        paymentDao.getSession().flush();
        paymentDao.getSession().evict(payment);
        Assert.assertNotNull("Payment did't save!", paymentDao.get(payment.getId()));

    }

    @Test
    public void getPaymentsByAccountTest() throws Exception {
        user.setLogin("Ppetya");
        userDao.saveOrUpdate(user);
        paymentDao.saveOrUpdate(payment);
        paymentDao.getSession().flush();
        paymentDao.getSession().evict(payment);
        userDao.getSession().evict(user);
        Account account = user.getAccounts().iterator().next();
        PaymentsFilter paymentsFilter = new PaymentsFilter();
        paymentsFilter.setMinAmountPayment(200);
        List<Payment> payments = paymentDao.getPaymentsByAccount(account, "ASC", 10, 0, paymentsFilter);
        Assert.assertEquals("Don't equals payments!", payments.iterator().next(), payment);
    }

    @Test
    public void getAllPaymentsTest() throws Exception {
        user.setLogin("Ppetr2");
        userDao.saveOrUpdate(user);
        paymentDao.saveOrUpdate(payment);
        paymentDao.getSession().flush();
        paymentDao.getSession().evict(payment);
        userDao.getSession().evict(user);
        PaymentsFilter paymentsFilter = new PaymentsFilter();
        paymentsFilter.setMinAmountPayment(200);
        List<Payment> payments = paymentDao.getAllPayments("ASC", 10, 0, paymentsFilter);
        Assert.assertEquals("Don't equals payments!", payments.iterator().next(), payment);
    }

}