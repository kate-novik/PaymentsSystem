package by.it.novik.dao;

import by.it.novik.entities.Account;
import by.it.novik.entities.Payment;
import by.it.novik.entities.User;
import by.it.novik.util.DaoException;
import by.it.novik.valueObjects.AccountsFilter;
import by.it.novik.valueObjects.PaymentsFilter;

import java.util.Date;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IPaymentDao {

    /**
     * Получение списка платежей пользователя
     * @param user Объект User
     * @return Список объектов Payments
     * @param orderState порядок сортировки по состоянию счета
     * @param pageSize Количество элементов на странице
     * @param firstItem Позиция первого элемента для вывода
     * @throws DaoException
     */
    List<Payment> getPaymentsByUser(User user, String orderState, Integer pageSize, Integer firstItem)
            throws DaoException;

    /**
     * Получение списка платежей счета
     * @param account Объект Account
     * @param orderState порядок сортировки по состоянию счета
     * @param pageSize Количество элементов на странице
     * @param firstItem Позиция первого элемента для вывода
     * @return Список объектов Payments
     * @throws DaoException
     */
    List<Payment> getPaymentsByAccount(Account account, String orderState, Integer pageSize, Integer firstItem)
            throws DaoException;

    /**
     * Получение списка всех платежей
     * @param orderState порядок сортировки по состоянию счета
     * @param pageSize Количество элементов на странице
     * @param firstItem Позиция первого элемента для вывода
     * @return Список объектов Payments
     * @throws DaoException
     */
    List<Payment> getAllPayments(String orderState, Integer pageSize, Integer firstItem) throws DaoException;

    /**
     * Getting total count of payments with filter
     * @param payDate Object Date of payment
     * @param minAmountPayment min amount of payment
     * @param maxAmountPayment max amount of payment
     * @return total count of payments
     */
    Integer getTotalCountOfPayments(Date payDate, double minAmountPayment, double maxAmountPayment);
}
