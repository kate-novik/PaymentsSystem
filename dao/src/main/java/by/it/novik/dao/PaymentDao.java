package by.it.novik.dao;

import by.it.novik.entities.Account;
import by.it.novik.entities.Payment;
import by.it.novik.entities.User;
import by.it.novik.util.DaoException;
import by.it.novik.valueObjects.PaymentsFilter;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Kate Novik.
 */
@Repository("paymentDao")
public class PaymentDao extends Dao<Payment> implements IPaymentDao{
    public PaymentDao(){}

    @Override
    public List<Payment> getPaymentsByUser(User user, String orderState, Integer pageSize, Integer firstItem)
            throws DaoException {
        List<Payment> payments;
        //Order for sorting by default
        if (orderState == null) {
            orderState = "ASC";
        }
        try {
            Query query = getSession().getNamedQuery("getPaymentsByUser").setEntity("user",user)
                    .setString("orderState",orderState);
            query.setFirstResult(firstItem);
            query.setMaxResults(pageSize);
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
    public List<Payment> getPaymentsByAccount(Account account, String orderState, Integer pageSize, Integer firstItem) throws DaoException {
        List<Payment> payments;
        //Order for sorting by default
        if (orderState == null) {
            orderState = "ASC";
        }
        try {
            Query query = getSession().getNamedQuery("getPaymentsByAccount").setEntity("account",account)
                    .setString("orderState",orderState);
            query.setFirstResult(firstItem);
            query.setMaxResults(pageSize);
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
    public List<Payment> getAllPayments(String orderState, Integer pageSize, Integer firstItem) throws DaoException {
        List<Payment> payments;
        //Order for sorting by default
        if (orderState == null) {
            orderState = "ASC";
        }
        try {
            Query query = getSession().getNamedQuery("getAllPayments")
                    .setString("orderState",orderState);
            query.setFirstResult(firstItem);
            query.setMaxResults(pageSize);
            payments = query.list();
            log.info("getAllPayments():" + payments);
        }
        catch (HibernateException e) {
            log.error("Error getAllPayments() in Dao" + e);

            throw new DaoException("Error getAllPayments() in Dao.");
        }
        return payments;
    }

    @Override
    public Integer getTotalCountOfPayments(Date payDate, double minAmountPayment, double maxAmountPayment) {
        Criteria criteria = getSession().createCriteria(Payment.class);
        Criterion amount = Restrictions.between("amountPayment",minAmountPayment,maxAmountPayment);
        Criterion date = Restrictions.eq("payDate", payDate);
        LogicalExpression andExp = Restrictions.and(amount,date);
        criteria.add(andExp);
        //To get total row count
        criteria.setProjection(Projections.rowCount());
        return (Integer)criteria.uniqueResult();
    }
}
