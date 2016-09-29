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
    public List<Payment> getPaymentsByUser(Serializable id_user, String orderState, Integer pageSize, Integer firstItem)
            throws ServiceException {
        List<Payment> payments;
        User user;
        try {
            user = userDao.get(id_user);
            payments = paymentDao.getPaymentsByUser(user, orderState, pageSize, firstItem);
        }
        catch (DaoException d){
            log.error("Error getPaymentsByUser() in PaymentService." + d);
            throw new ServiceException("Error in getting payments.");
        }
        return payments;
    }

    @Override
    public List<Payment> getPaymentsByAccount(Serializable id_account, String orderState, Integer pageSize,
                                              Integer firstItem, PaymentsFilter paymentsFilter) throws ServiceException {
        List<Payment> payments;
        Account account;
        try {
            account = accountDao.get(id_account);
            payments = paymentDao.getPaymentsByAccount(account, orderState, pageSize, firstItem, paymentsFilter);
        }
        catch (DaoException d){
            log.error("Error getPaymentsByAccount() in PaymentService." + d);
            throw new ServiceException("Error in getting payments.");
        }
        return payments;
    }

    @Override
    public void makePayment(int idAccountFrom, int idAccountTo, double pay_amount, String description) throws ServiceException {

        //Чтение счета-источника платежа по id
        Account accountSource;
        Double balance;
        Account accountDestination;
        try {
            accountSource = accountDao.get(idAccountFrom);
            balance = accountSource.getBalance();
            accountDestination =  accountDao.get(idAccountTo);
        } catch (DaoException e) {
            log.error("Error makePayment() in PaymentService." + e);
            throw new ServiceException("Error in making payment.");
        }

        //Проверим баланс счета для списывания денег
        if (balance >= pay_amount) {
            if (accountDestination != null) {
                try{
                //Вычислим сумму, которая останется на счете после списания
                Double source_amount = balance - pay_amount;
                accountSource.setBalance(source_amount);
                //Вычислим сумму, которая будет на счете получателя
                Double destination_amount = accountDestination.getBalance() + pay_amount;
                accountDestination.setBalance(destination_amount);
                //Обновляем данные по счетам
                accountDao.saveOrUpdate(accountSource);
                accountDao.saveOrUpdate(accountDestination);
                //Создание текущей даты и ее форматирование
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                String currentDate = formatDate.format(date);
                //Создаем платежку
                Payment payment = new Payment();
                payment.setAccountSource(accountSource);
                payment.setAccountDestination(accountDestination);
                payment.setDescription(description);
                payment.setPayDate(formatDate.parse(currentDate));
                payment.setAmountPayment(pay_amount);
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
            throw new ServiceException("Few funds in the account_source. Refill your account.") ;
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
            throw new ServiceException("Error in getting payments.");
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
            throw new ServiceException("Error save/update payment." );
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
            throw new ServiceException("Error in getting payment." );
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
            throw new ServiceException("Error in deleting payment.");
        }
    }

    @Override
    public Integer getTotalCountOfPayments(PaymentsFilter paymentsFilter) {
        return paymentDao.getTotalCountOfPayments(paymentsFilter);
    }

    @Override
    public Integer getTotalCountOfPayments(PaymentsFilter paymentsFilter, Long idAccount) throws ServiceException {
        Account account = null;
        try {
            account = accountDao.get(idAccount);
        } catch (DaoException e) {
            log.error("Error get account in PaymentDao " + e);
            throw new ServiceException("Error in getting account.");
        }
        return paymentDao.getTotalCountOfPayments(paymentsFilter,account);
    }
}
