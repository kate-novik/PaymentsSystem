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
    public List<Payment> getPaymentsByAccount(Serializable idAccount, String orderState, Integer pageSize,
                                              Integer firstItem, PaymentsFilter paymentsFilter) throws ServiceException {
        List<Payment> payments;
        Account account;
        try {
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
    public void makePayment(Long idAccountFrom, Long idAccountTo, Double payAmount, String description)
            throws ServiceException {
        //To read source account
        Account accountSource;
        Account accountDestination;
        try {
            accountSource = accountDao.get(idAccountFrom);
            accountDestination =  accountDao.get(idAccountTo);
        } catch (DaoException e) {
            log.error("Error makePayment() in PaymentService." + e);
            throw new ServiceException("Error in making payment in PaymentService.");
        }
        //Проверим баланс счета для списывания денег
        if (calculateAccountsBalance(accountSource, accountDestination, payAmount)) {
                try{
                //Update accounts data
                accountDao.saveOrUpdate(accountSource);
                accountDao.saveOrUpdate(accountDestination);
                //To create date
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                String currentDate = formatDate.format(date);
                //To create payment
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
    public Number getTotalCountOfPayments(PaymentsFilter paymentsFilter, Long idAccount) throws ServiceException {
        Account account;
        try {
            account = accountDao.get(idAccount);
        } catch (DaoException e) {
            log.error("Error get account in PaymentDao " + e);
            throw new ServiceException("Error in getting account in PaymentService.");
        }
        return paymentDao.getTotalCountOfPayments(paymentsFilter,account);
    }

    private boolean calculateAccountsBalance (Account sourceAccount, Account accountDest, Double payAmount) {
        Double balance = sourceAccount.getBalance();
        //To Check balance source Account
        if (balance >= payAmount) {
            //To calculate balance of account after paying
            Double sourceAmount = balance - payAmount;
            sourceAccount.setBalance(sourceAmount);
            Double destinationAmount = accountDest.getBalance() + payAmount;
            accountDest.setBalance(destinationAmount);
            return true;
        }
        return false;
    }
}
