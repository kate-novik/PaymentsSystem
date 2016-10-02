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
     * To get list of accounts by user
     * @param user Object User
     * @param orderState Order of sorting accounts
     * @param pageSize Size of elements on page
     * @param firstItem Position of first element for output
     * @return List of accounts users
     * @throws DaoException
     */
    List<Account> getAccountsByUser (User user, String orderState, Integer pageSize, Integer firstItem) throws DaoException;

    /**
     * To get list of accounts all users
     * @return List of accounts all users
     * @throws DaoException
     */
    List<Account> getAllAccounts () throws DaoException;

    /**
     * To get list of locked accounts all users
     * @return List of locked accounts all users
     * @throws DaoException
     */
    List<Account> getLockedAccounts () throws DaoException;

    /**
     * Getting total count of accounts with filter
     * @param accountsFilter Object AccountsFilter
     * @return Total count of accounts
     */
    Integer getTotalCountOfAccounts (AccountsFilter accountsFilter);
}
