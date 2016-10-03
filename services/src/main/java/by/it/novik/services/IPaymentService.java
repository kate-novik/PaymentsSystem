package by.it.novik.services;


import by.it.novik.entities.Payment;
import by.it.novik.util.ServiceException;
import by.it.novik.valueObjects.PaymentsFilter;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IPaymentService {

    /**
     * To get list of payments by user
     * @param idUser ID of User
     * @param orderState Order of sorting payments
     * @param pageSize Size of elements on page
     * @param firstItem Position of first element for output
     * @return List of objects Payments
     * @throws ServiceException
     */
    List<Payment> getPaymentsByUser(Serializable idUser, String orderState, Integer pageSize, Integer firstItem)
            throws ServiceException;

    /**
     * To get list of payments by account
     * @param idAccount ID of Account
     * @param orderState Order of sorting payments
     * @param pageSize Size of elements on page
     * @param firstItem Position of first element for output
     * @param paymentsFilter Object paymentsFilter
     * @return List of objects Payments
     * @throws ServiceException
     */
    List<Payment> getPaymentsByAccount (Serializable idAccount, String orderState, Integer pageSize, Integer firstItem,
                                        PaymentsFilter paymentsFilter) throws ServiceException;

    /**
     * To make payment
     * @param idAccountFrom ID account of source
     * @param idAccountTo ID account of destination
     * @param payAmount Amount of Payment
     * @param description Description of payment
     * @throws ServiceException
     */
    void makePayment(Long idAccountFrom, Long idAccountTo, Double payAmount, String description)
            throws ServiceException;

    /**
     * To get list of all payments
     * @param orderState Order of sorting payments
     * @param pageSize Size of elements on page
     * @param firstItem Position of first element for output
     * @param paymentsFilter Object paymentsFilter
     * @return List of objects Payments
     * @throws ServiceException
     */
    List<Payment> getAllPayments (String orderState, Integer pageSize, Integer firstItem, PaymentsFilter paymentsFilter)
            throws ServiceException;

    /**
     * To save/update object Payment
     * @param payment Object Payment
     * @throws ServiceException
     */
    void saveOrUpdate(Payment payment) throws ServiceException;

    /**
     * To get object Payment by ID
     * @param id ID of payment
     * @return Object Payment
     * @throws ServiceException
     */
    Payment get(Serializable id) throws ServiceException;

    /**
     * To delete object Payment by ID
     * @param id ID of payment
     * @throws ServiceException
     */
    void delete(Serializable id) throws ServiceException;

    /**
     * Getting total count of payments with filter
     * @param paymentsFilter Object PaymentsFilter with filters
     * @return total count of payments
     */
    Integer getTotalCountOfPayments(PaymentsFilter paymentsFilter);

    /**
     * Getting total count of payments with filter
     * @param paymentsFilter Object PaymentsFilter with filters
     * @param idAccount ID account
     * @return total count of payments
     * @throws ServiceException
     */
    Long getTotalCountOfPayments(PaymentsFilter paymentsFilter, Long idAccount) throws ServiceException;
}
