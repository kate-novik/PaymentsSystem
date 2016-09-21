package by.it.novik.dao;

import by.it.novik.pojos.Account;
import by.it.novik.pojos.User;
import by.it.novik.util.DaoException;

import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IAccountDao {

    /**
     * Получение список счетов пользователя
     * @param user Объект пользователя
     * @param orderState Порядок сортировки счетов
     * @return Список счетов пользователя
     * @throws DaoException
     */
    List<Account> getAccountsByUser (User user, String orderState) throws DaoException;

    /**
     * Получение списка счетов всех пользователей
     * @return Список счетов всех пользователей
     * @throws DaoException
     */
    List<Account> getAllAccounts () throws DaoException;

    /**
     * Получение заблокированных счетов пользователей
     * @return Список заблокированных счетов
     * @throws DaoException
     */
    List<Account> getLockedAccounts () throws DaoException;
}
