package by.it.novik.dao;

import by.it.novik.entities.Account;
import by.it.novik.entities.Payment;
import by.it.novik.entities.User;
import by.it.novik.util.DaoException;
import by.it.novik.valueObjects.PaymentsFilter;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Kate Novik.
 */
@Repository("paymentDao")
public class PaymentDao extends Dao<Payment> implements IPaymentDao {

    public PaymentDao() {
    }

    @Override
    public List<Payment> getPaymentsByAccount(Account account, String orderState, Integer pageSize, Integer firstItem,
            PaymentsFilter paymentsFilter) throws DaoException {
        List<Payment> payments;
        Criteria criteria = getCriteriaOfFilter(paymentsFilter);
        //To add checking payments of source and destination
        Criterion accountSource = Restrictions.eq("accountSource", account);
        Criterion accountDest = Restrictions.eq("accountDestination", account);
        LogicalExpression andExp = Restrictions.or(accountSource, accountDest);
        criteria.add(andExp);
        //Order for sorting
        Order order = "ASC".equals(orderState) ? Order.asc("payDate") : Order.desc("payDate");
        criteria.addOrder(order);
        criteria.setFirstResult(firstItem);
        criteria.setMaxResults(pageSize);
        try {
            payments = criteria.list();
            log.info("getPaymentsByAccount():" + payments);
        } catch (HibernateException e) {
            log.error("Error getPaymentsByAccount() in Dao" + e);
            throw new DaoException("Error getPaymentsByAccount() in Dao.");
        }
        return payments;
    }

    @Override
    public List<Payment> getAllPayments(String orderState, Integer pageSize, Integer firstItem, PaymentsFilter paymentsFilter)
            throws DaoException {
        List<Payment> payments;
        //Order for sorting by default
        Criteria criteria = getCriteriaOfFilter(paymentsFilter);
        //Order for sorting
        Order order = "ASC".equals(orderState) ? Order.asc("payDate") : Order.desc("payDate");
        criteria.addOrder(order);
        criteria.setFirstResult(firstItem);
        criteria.setMaxResults(pageSize);
        try {
            payments = criteria.list();
            log.info("getAllPayments():" + payments);
        } catch (HibernateException e) {
            log.error("Error getAllPayments() in Dao" + e);
            throw new DaoException("Error getAllPayments() in Dao.");
        }
        return payments;
    }

    @Override
    public Long getTotalCountOfPayments(PaymentsFilter paymentsFilter) {
        Criteria criteria = getCriteriaOfFilter(paymentsFilter);
        //To get total row count
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    @Override
    public Long getTotalCountOfPayments(PaymentsFilter paymentsFilter, Account account) {
        Criteria criteria = getCriteriaOfFilter(paymentsFilter);
        criteria.add(Restrictions.eq("accountSource", account));
        //To get total row count
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    /**
     * Getting Object Criteria depending on the filter values PaymentsFilter
     * @param paymentsFilter Object PaymentsFilter
     * @return Object Criteria
     */
    private Criteria getCriteriaOfFilter(PaymentsFilter paymentsFilter) {
        Date payDate = paymentsFilter.getPayDate();
        double minAmountPayment = paymentsFilter.getMinAmountPayment();
        double maxAmountPayment = paymentsFilter.getMaxAmountPayment();
        Criteria criteria = getSession().createCriteria(Payment.class);
        //To check existing filters
        if (payDate != null && minAmountPayment != 0 && maxAmountPayment != 0) {
            Criterion amount = Restrictions.between("amountPayment", minAmountPayment, maxAmountPayment);
            Criterion date = Restrictions.eq("payDate", payDate);
            LogicalExpression andExp = Restrictions.and(amount, date);
            criteria.add(andExp);
        } else if (payDate != null && minAmountPayment == 0 && maxAmountPayment == 0) {
            criteria.add(Restrictions.eq("payDate", payDate));
        } else if (payDate == null && minAmountPayment != 0 && maxAmountPayment != 0) {
            criteria.add(Restrictions.between("amountPayment", minAmountPayment, maxAmountPayment));
        } else if (payDate == null && minAmountPayment == 0 && maxAmountPayment != 0) {
            criteria.add(Restrictions.le("amountPayment", maxAmountPayment));
        } else if (payDate == null && minAmountPayment != 0 && maxAmountPayment == 0) {
            criteria.add(Restrictions.ge("amountPayment", minAmountPayment));
        } else if (payDate != null && minAmountPayment != 0 && maxAmountPayment == 0) {
            Criterion amount = Restrictions.ge("amountPayment", minAmountPayment);
            Criterion date = Restrictions.eq("payDate", payDate);
            LogicalExpression andExp = Restrictions.and(amount, date);
            criteria.add(andExp);
        } else if (payDate != null && minAmountPayment == 0 && maxAmountPayment != 0) {
            Criterion amount = Restrictions.le("amountPayment", maxAmountPayment);
            Criterion date = Restrictions.eq("payDate", payDate);
            LogicalExpression andExp = Restrictions.and(amount, date);
            criteria.add(andExp);
        }
        return criteria;
    }
}
