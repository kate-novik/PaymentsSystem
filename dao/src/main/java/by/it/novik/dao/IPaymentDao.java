package by.it.novik.dao;

import by.it.novik.entities.Account;
import by.it.novik.entities.Payment;
import by.it.novik.entities.User;
import by.it.novik.util.DaoException;

import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IPaymentDao {

    /**
     * Получение списка платежей пользователя
     * @param user Объект User
     * @return Список объектов Payments
     * @throws DaoException
     */
    List<Payment> getPaymentsByUser(User user) throws DaoException;

    /**
     * Получение списка платежей счета
     * @param account Объект Account
     * @return Список объектов Payments
     * @throws DaoException
     */
    List<Payment> getPaymentsByAccount(Account account) throws DaoException;

    /**
     * Получение списка всех платежей
     * @return Список объектов Payments
     * @throws DaoException
     */
    List<Payment> getAllPayments() throws DaoException;
}
