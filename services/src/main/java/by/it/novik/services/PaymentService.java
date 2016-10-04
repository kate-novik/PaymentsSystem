package by.it.novik.services;

import by.it.novik.dao.AccountDao;
import by.it.novik.dao.PaymentDao;
import by.it.novik.dao.UserDao;
import by.it.novik.entities.Account;
import by.it.novik.entities.Payment;
import by.it.novik.entities.User;
import by.it.novik.util.DaoException;
import by.it.novik.util.ServiceException;
import by.it.novik.valueObjects.PaymentsFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Kate Novik.
 */
@Service("paymentService")
@Transactional
public class PaymentService implements IPaymentService {

    protected static Logger log = Logger.getLogger (PaymentService.class);

    @Autowired
    private PaymentDao paymentDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountDao accountDao;

    public PaymentService() {

    }

    @Override
    public List<Payment> getPaymentsByUser(Serializable idUser, String orderState, Integer pageSize, Integer firstItem)
            throws ServiceException {
        List<Payment> payments;
        User user;
        try {
            user = userDao.get(idUser); // seems like redundant operation for me
                                        // because you already know user's id
                                        // and in order to get all user payments you need only her id, not the whole object
                                        // consider replacing with EntityManager.getReference()
                                        // please see http://stackoverflow.com/a/32037084/3302263 for more information
            payments = paymentDao.getPaymentsByUser(user, orderState, pageSize, firstItem);
        }
        catch (DaoException d){
            log.error("Error getPaymentsByUser() in PaymentService." + d);
            throw new ServiceException("Error in getting payments in PaymentService.");
        }
        return payments;
    }

    @Override
    public List<Payment> getPaymentsByAccount(Serializable idAccount, String orderState, Integer pageSize,
                                              Integer firstItem, PaymentsFilter paymentsFilter) throws ServiceException {
        List<Payment> payments;
        Account account;
        try {
            // please see my comment in getPaymentsByUser()
            account = accountDao.get(idAccount);
            payments = paymentDao.getPaymentsByAccount(account, orderState, pageSize, firstItem, paymentsFilter);
        }
        catch (DaoException d){
            log.error("Error getPaymentsByAccount() in PaymentService." + d);
            throw new ServiceException("Error in getting payments in PaymentService.");
        }
        return payments;
    }

    @Override
    public void makePayment(Long idAccountFrom, Long idAccountTo, Double payAmount, String description) throws ServiceException {

        //Чтение счета-источника платежа по id
        Account accountSource;
        Double balance;
        Account accountDestination;
        try {
            accountSource = accountDao.get(idAccountFrom);
            balance = accountSource.getBalance();
            // please see my comment in getPaymentsByUser()
            accountDestination =  accountDao.get(idAccountTo);
        } catch (DaoException e) {
            log.error("Error makePayment() in PaymentService." + e);
            throw new ServiceException("Error in making payment in PaymentService.");
        }

        //Проверим баланс счета для списывания денег
        if (balance >= payAmount) {
            if (accountDestination != null) {
                try{
                    // please add indents here
                    // and consider refactoring - extracting the code above into shorter methods so that it is easier what is going on without going into details
                    // thus you can omit the comments too
                //Вычислим сумму, которая останется на счете после списания
                Double source_amount = balance - payAmount;
                accountSource.setBalance(source_amount);
                //Вычислим сумму, которая будет на счете получателя
                Double destination_amount = accountDestination.getBalance() + payAmount;
                accountDestination.setBalance(destination_amount);
                //Обновляем данные по счетам
                accountDao.saveOrUpdate(accountSource);
                accountDao.saveOrUpdate(accountDestination);
                //Создание текущей даты и ее форматирование
                Date date = new Date(System.currentTimeMillis());
                    // why?
                    // aren't they extra operations - converting a date to a string and then back
                    // why can't you just set payDate to new Date()?
                    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                String currentDate = formatDate.format(date);
                //Создаем платежку
                Payment payment = new Payment();
                payment.setAccountSource(accountSource);
                payment.setAccountDestination(accountDestination);
                payment.setDescription(description);
                payment.setPayDate(formatDate.parse(currentDate));
                payment.setAmountPayment(payAmount);
                saveOrUpdate(payment);

                } catch (DaoException e) {
                        log.error("Error makePayment() in PaymentService." + e);
                        throw new ServiceException("Payment wasn't done. Repeat, please, enter.");

                } catch (ParseException e) {
                    log.error("Error parsing Date in PaymentService."+ e);
                    throw new ServiceException("Error date of payment.");
                }
            }
            else {
                log.error("Error make payment in PaymentService. Account of destination doesn't exist.");
                throw new ServiceException("Account of destination doesn't exist. Repeat, please, enter.") ;
            }
        } else {
            log.error("Error make payment in PaymentService. Few funds in the account_source.");
            throw new ServiceException("Few funds in the account source. Refill your account.") ;
        }
    }

    @Override
    public List<Payment> getAllPayments(String orderState, Integer pageSize, Integer firstItem, PaymentsFilter paymentsFilter) throws ServiceException {
        List<Payment> payments;
        try {
            payments = paymentDao.getAllPayments(orderState, pageSize, firstItem, paymentsFilter);
        }
        catch (DaoException d) {
            log.error("Error getAllPayments() in PaymentService." + d);
            throw new ServiceException("Error in getting payments in PaymentService.");
        }
        return payments;
    }

    @Override
    public void saveOrUpdate(Payment payment) throws ServiceException {
        try {
            paymentDao.saveOrUpdate(payment);
        }
        catch (DaoException d) {
            log.error("Error saveOrUpdate() payment in PaymentDao " + d);
            throw new ServiceException("Error save/update payment in PaymentService." );
        }
    }

    @Override
    public Payment get(Serializable id) throws ServiceException {
        Payment payment;
        try {
            payment = paymentDao.get(id);
        }
        catch (DaoException d) {
            log.error("Error get() payment in PaymentDao " + d);
            throw new ServiceException("Error in getting payment in PaymentService." );
        }
        return payment;
    }

    @Override
    public void delete(Serializable id) throws ServiceException {
        try {
            paymentDao.delete(id);
        }
        catch (DaoException d) {
            log.error("Error delete() payment in PaymentDao " + d);
            throw new ServiceException("Error in deleting payment in PaymentService.");
        }
    }

    @Override
    public Number getTotalCountOfPayments(PaymentsFilter paymentsFilter) {
        return paymentDao.getTotalCountOfPayments(paymentsFilter);
    }

    @Override
    public Long getTotalCountOfPayments(PaymentsFilter paymentsFilter, Long idAccount) throws ServiceException {
        Account account = null;
        try {
            account = accountDao.get(idAccount);
        } catch (DaoException e) {
            log.error("Error get account in PaymentDao " + e);
            throw new ServiceException("Error in getting account in PaymentService.");
        }
        return paymentDao.getTotalCountOfPayments(paymentsFilter,account);
    }
}
