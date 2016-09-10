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

    /**
     * Получение платежей по объекту User
     * @param user Объект User
     * @return Список платежей
     */
    public List<Payment> getPaymentsByUser(User user) throws DaoException {
        List<Payment> payments;
        try {
            //session = HibernateUtil.getHibernateUtil().getSession();
            //Открываем транзакцию
            //transaction = session.beginTransaction();
            Query query = getSession().getNamedQuery("getPaymentsByUser").setEntity("user",user);
            payments = query.list();
            log.info("getPaymentsByUser():" + payments);
            //При отсутствии исключения коммитим транзакцию
            //transaction.commit();
            //log.info("getPaymentsByUser (commit):" + payments);
        }
        catch (HibernateException e) {
            log.error("Error getPaymentsByUser() in Dao" + e);
            //Откатываем транзакцию
            //transaction.rollback();
            throw new DaoException("Error getPaymentsByUser() in Dao.");
        }
        return payments;

    }

    /**
     * Получение платежей по объекту Account
     * @param account Объект Account
     * @return Список платежей
     */
    public List<Payment> getPaymentsByAccount(Account account) throws DaoException {
        List<Payment> payments;
        try {
            //session = HibernateUtil.getHibernateUtil().getSession();
            //Открываем транзакцию
            //transaction = session.beginTransaction();
            Query query = getSession().getNamedQuery("getPaymentsByAccount").setEntity("account",account);
            payments = query.list();
            log.info("getPaymentsByAccount():" + payments);
            //При отсутствии исключения коммитим транзакцию
            //transaction.commit();
            //log.info("getPaymentsByAccount (commit):" + payments);
        }
        catch (HibernateException e) {
            log.error("Error getPaymentsByAccount() in Dao" + e);
            //Откатываем транзакцию
            //transaction.rollback();
            throw new DaoException("Error getPaymentsByAccount() in Dao.");
        }
        return payments;
    }

    public List<Payment> getAllPayments() throws DaoException {
        List<Payment> payments;
        try {
            //session = HibernateUtil.getHibernateUtil().getSession();
            //Открываем транзакцию
            //transaction = session.beginTransaction();
            Query query = getSession().getNamedQuery("getAllPayments");
            payments = query.list();
            log.info("getAllPayments():" + payments);
            //При отсутствии исключения коммитим транзакцию
            //transaction.commit();
            log.info("getAllPayments (commit):" + payments);
        }
        catch (HibernateException e) {
            log.error("Error getAllPayments() in Dao" + e);
            //Откатываем транзакцию
            //transaction.rollback();
            throw new DaoException("Error getAllPayments() in Dao.");
        }
        return payments;
    }
}
