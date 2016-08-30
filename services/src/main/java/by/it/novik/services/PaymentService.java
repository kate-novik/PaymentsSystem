package by.it.novik.services;

import by.it.novik.dao.PaymentDao;
import by.it.novik.pojos.Account;
import by.it.novik.pojos.Payment;
import by.it.novik.pojos.User;
import by.it.novik.util.DaoException;
import by.it.novik.util.ServiceException;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public class PaymentService extends BaseService<Payment> implements IPaymentService {

    private PaymentDao paymentDao;


    public PaymentService(PaymentDao paymentDAO) {
        super(paymentDAO);
        this.paymentDao = paymentDAO;
    }

    @Override
    public List<Payment> getPaymentsByUser(Serializable id_user) throws ServiceException {
        List<Payment> payments;
        User user = Service.getService().getUserService().get(id_user);
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
        Account account = Service.getService().getAccountService().get(id_account);
        try {
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
        AccountService accountService = Service.getService().getAccountService();
        //Чтение счета-источника платежа по id
        Account accountSource = accountService.get(idAccountFrom);
        Double balance = accountSource.getBalance();
        Account accountDestination =  accountService.get(idAccountTo);
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
                accountService.saveOrUpdate(accountSource);
                accountService.saveOrUpdate(accountDestination);
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

                } catch (ServiceException e) {
                        log.error("Error make payment in PaymentService.");
                        throw new ServiceException("Payment wasn't done. Repeat, please, enter.");

                } catch (ParseException e) {
                    log.error("Error parsing Date in PaymentService.");
                    throw new ServiceException("Error parsing Date in PaymentService.");
                }
            }
            else {
                log.error("Error make payment in PaymentService. Account of destination doesn't exist.");
                throw new ServiceException("Account of destination doesn't exist. Repeat, please, enter.") ;
            }
        } else {
            log.error("Error make payment in PaymentService. Few funds in the account_source..");
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
}
