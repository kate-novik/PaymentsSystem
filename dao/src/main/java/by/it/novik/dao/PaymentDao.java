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
    public Number getTotalCountOfPayments(PaymentsFilter paymentsFilter) {
        Criteria criteria = getCriteriaOfFilter(paymentsFilter);
        return getPaymentsCount(criteria);
    }

    @Override
    public Number getTotalCountOfPayments(PaymentsFilter paymentsFilter, Account account) {
        Criteria criteria = getCriteriaOfFilter(paymentsFilter);
        //To add checking payments of source and destination
        Criterion accountSource = Restrictions.eq("accountSource", account);
        Criterion accountDest = Restrictions.eq("accountDestination", account);
        LogicalExpression andExp = Restrictions.or(accountSource, accountDest);
        criteria.add(andExp);
        return getPaymentsCount(criteria);
    }

    /**
     * Getting count of payments with criteria
     * @param criteria Object Criteria
     * @return Count of payments
     */
    private Number getPaymentsCount (Criteria criteria){
        criteria.setProjection(Projections.rowCount());
        return (Number) criteria.uniqueResult();
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
        criteria.add(getRestriction(payDate, minAmountPayment, maxAmountPayment));
        return criteria;
    }

    private Criterion getRestriction(Date payDate, double minAmountPayment, double maxAmountPayment) {
        Junction conjunction = Restrictions.conjunction();
        if (payDate != null) {
            conjunction.add(Restrictions.eq("payDate", payDate));
        }
        if (minAmountPayment != 0 || maxAmountPayment != 0) {
            conjunction.add(getAmountRestriction(minAmountPayment, maxAmountPayment));
        }
        return conjunction;
    }

    private Criterion getAmountRestriction(double minAmountPayment, double maxAmountPayment) {
        if (minAmountPayment != 0) {
            if (maxAmountPayment != 0) {
                return Restrictions.between("amountPayment", minAmountPayment, maxAmountPayment);
            }
            return Restrictions.ge("amountPayment", minAmountPayment);
        }
        return Restrictions.le("amountPayment", maxAmountPayment);
    }
}
