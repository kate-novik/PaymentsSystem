package by.it.novik.dao;

import by.it.novik.pojos.Account;
import by.it.novik.pojos.Payment;
import by.it.novik.pojos.User;
import by.it.novik.util.DaoException;
import by.it.novik.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kate Novik.
 */
@Repository("paymentDao")
public class PaymentDao extends Dao<Payment> implements IPaymentDao{
    public PaymentDao(){}

    @Override
    public List<Payment> getPaymentsByUser(User user) throws DaoException {
        List<Payment> payments;
        try {
            Query query = getSession().getNamedQuery("getPaymentsByUser").setEntity("user",user);
            payments = query.list();
            log.info("getPaymentsByUser():" + payments);
        }
        catch (HibernateException e) {
            log.error("Error getPaymentsByUser() in Dao" + e);
            throw new DaoException("Error getPaymentsByUser() in Dao.");
        }
        return payments;

    }

    @Override
    public List<Payment> getPaymentsByAccount(Account account) throws DaoException {
        List<Payment> payments;
        try {
            Query query = getSession().getNamedQuery("getPaymentsByAccount").setEntity("account",account);
            payments = query.list();
            log.info("getPaymentsByAccount():" + payments);
        }
        catch (HibernateException e) {
            log.error("Error getPaymentsByAccount() in Dao" + e);
            throw new DaoException("Error getPaymentsByAccount() in Dao.");
        }
        return payments;
    }

    @Override
    public List<Payment> getAllPayments() throws DaoException {
        List<Payment> payments;
        try {
            Query query = getSession().getNamedQuery("getAllPayments");
            payments = query.list();
            log.info("getAllPayments():" + payments);
        }
        catch (HibernateException e) {
            log.error("Error getAllPayments() in Dao" + e);
            throw new DaoException("Error getAllPayments() in Dao.");
        }
        return payments;
    }
}
