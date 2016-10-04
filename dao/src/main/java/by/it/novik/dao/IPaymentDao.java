package by.it.novik.dao;

import by.it.novik.entities.Account;
import by.it.novik.entities.Payment;
import by.it.novik.entities.User;
import by.it.novik.util.DaoException;
import by.it.novik.valueObjects.PaymentsFilter;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IPaymentDao {

    /**
     * To get list of payments by user
     * @param user Object User
     * @param orderState Order of sorting payments
     * @param pageSize Size of elements on page
     * @param firstItem Position of first element for output
     * @return List of objects Payments
     * @throws DaoException
     */
    List<Payment> getPaymentsByUser(User user, String orderState, Integer pageSize, Integer firstItem)
            throws DaoException;

    /**
     * To get list of payments by account
     * @param account Object Account
     * @param orderState Order of sorting payments
     * @param pageSize Size of elements on page
     * @param firstItem Position of first element for output
     * @param paymentsFilter Object paymentsFilter
     * @return List of objects Payments
     * @throws DaoException
     */
    List<Payment> getPaymentsByAccount(Account account, String orderState, Integer pageSize, Integer firstItem
            , PaymentsFilter paymentsFilter)
            throws DaoException;

    /**
     * To get list of all payments
     * @param orderState Order of sorting payments
     * @param pageSize Size of elements on page
     * @param firstItem Position of first element for output
     * @param paymentsFilter Object paymentsFilter
     * @return List of objects Payments
     * @throws DaoException
     */
    List<Payment> getAllPayments(String orderState, Integer pageSize, Integer firstItem, PaymentsFilter paymentsFilter)
            throws DaoException;

    /**
     * Getting total count of payments with filter
     * @param paymentsFilter Object PaymentsFilter with filters
     * @return total count of payments
     */
    Number getTotalCountOfPayments(PaymentsFilter paymentsFilter);

    /**
     * Getting total count of payments with filter
     * @param paymentsFilter Object PaymentsFilter with filters
     * @param account Object Account
     * @return total count of payments
     */
    Long getTotalCountOfPayments(PaymentsFilter paymentsFilter, Account account);
}
