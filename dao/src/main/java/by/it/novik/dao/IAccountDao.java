package by.it.novik.dao;

import by.it.novik.pojos.Account;
import by.it.novik.pojos.User;
import by.it.novik.util.DaoException;

import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IAccountDao {

    List<Account> getAccountsByUser (User user, String orderState) throws DaoException;
    List<Account> getAllAccounts () throws DaoException;
    List<Account> getLockedAccounts () throws DaoException;
}
