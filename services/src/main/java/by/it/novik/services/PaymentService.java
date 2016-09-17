package by.it.novik.services;

import by.it.novik.dao.AccountDao;
import by.it.novik.dao.PaymentDao;
import by.it.novik.dao.UserDao;
import by.it.novik.pojos.Account;
import by.it.novik.pojos.Payment;
import by.it.novik.pojos.User;
import by.it.novik.util.DaoException;
import by.it.novik.util.ServiceException;
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
//@Transactional
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
    public List<Payment> getPaymentsByUser(Serializable id_user) throws ServiceException {
        List<Payment> payments;
        User user;
        try {
            user = userDao.get(id_user);
        } catch (DaoException e) {
            log.error("Error get() user in PaymentService." + e);
            throw new ServiceException("Error get() user in PaymentService.");
        }
        try {
            payments = paymentDao.getPaymentsByUser(user);
        }
        catch (DaoException d){
            log.error("Error getPaymentsByUser() in PaymentService." + d);
            throw new ServiceException("Error getPaymentsByUser() in PaymentService.");
        }
        return payments;
    }

    @Override
    public List<Payment> getPaymentsByAccount(Serializable id_account) throws ServiceException {
        List<Payment> payments;
        Account account;
        try {
            account = accountDao.get(id_account);
            payments = paymentDao.getPaymentsByAccount(account);
        }
        catch (DaoException d){
            log.error("Error getPaymentsByAccount() in PaymentService." + d);
            throw new ServiceException("Error getPaymentsByAccount() in PaymentService.");
        }
        return payments;
    }

    @Override
    public void makePayment(int idAccountFrom, int idAccountTo, double pay_amount, String description) throws ServiceException {

        //Чтение счета-источника платежа по id
        Account accountSource = null;
        Double balance = null;
        Account accountDestination = null;
        try {
            accountSource = accountDao.get(idAccountFrom);
            balance = accountSource.getBalance();
            accountDestination =  accountDao.get(idAccountTo);
        } catch (DaoException e) {
            log.error("Error makePayment() in PaymentService." + e);
            throw new ServiceException("Error makePayment() in PaymentService.");
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
                    throw new ServiceException("Error parsing Date in PaymentService.");
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
    public List<Payment> getAllPayments() throws ServiceException {
        List<Payment> payments;
        try {
            payments = paymentDao.getAllPayments();
        }
        catch (DaoException d) {
            log.error("Error getAllPayments() in PaymentService." + d);
            throw new ServiceException("Error getAllPayments() in PaymentsService.");
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
            throw new ServiceException("Error saveOrUpdate() payment in PaymentDao." );
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
            throw new ServiceException("Error get() payment in PaymentDao." );
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
            throw new ServiceException("Error delete() payment in PaymentDao.");
        }
    }
}
