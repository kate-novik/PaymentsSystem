package by.it.novik.dao;

import by.it.novik.entities.Account;
import by.it.novik.entities.User;
import by.it.novik.util.DaoException;
import by.it.novik.valueObjects.AccountsFilter;

import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IAccountDao {

    /**
     * Получение список счетов пользователя
     * @param user Объект пользователя
     * @param orderState Порядок сортировки счетов
     * @param pageSize Количество элементов на странице
     * @param firstItem Позиция первого элемента для вывода
     * @return Список счетов пользователя
     * @throws DaoException
     */
    List<Account> getAccountsByUser (User user, String orderState, Integer pageSize, Integer firstItem) throws DaoException;

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

    /**
     * Getting total count of accounts with filter
     * @param accountsFilter Object AccountsFilter
     * @return total count of accounts
     */
    Integer getTotalCountOfAccounts (AccountsFilter accountsFilter);
}
