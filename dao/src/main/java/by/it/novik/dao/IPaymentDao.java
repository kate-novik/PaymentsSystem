package by.it.novik.dao;

import by.it.novik.pojos.Account;
import by.it.novik.pojos.Payment;
import by.it.novik.pojos.User;
import by.it.novik.util.DaoException;

import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IPaymentDao {

    List<Payment> getPaymentsByUser(User user) throws DaoException;
    List<Payment> getPaymentsByAccount(Account account) throws DaoException;
    List<Payment> getAllPayments() throws DaoException;
}
