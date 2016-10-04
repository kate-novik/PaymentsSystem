package by.it.novik.services;


import by.it.novik.entities.Account;
import by.it.novik.entities.User;
import by.it.novik.util.ServiceException;
import by.it.novik.valueObjects.AccountsFilter;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IAccountService {

    /**
     * To create account for user
     * @param user Object User
     * @return Object Account
     * @throws ServiceException
     */
    Account create(User user) throws ServiceException;

    /**
     * To get list of accounts by user
     * @param id_user ID of object User
     * @return List of accounts users
     * @throws ServiceException
     */
    List<Account> getAccountsByUser(Serializable id_user)
            throws ServiceException;

    /**
     * Locking account by object Account
     * @param account Object Account
     * @throws ServiceException
     */
    void lockingAccount(Account account) throws ServiceException;

    /**
     * Locking account by ID account
     * @param account ID Account
     * @throws ServiceException
     */
    void lockingAccount(Long account) throws ServiceException;

    /**
     * To refill balance of account
     * @param account ID account
     * @param amount Amount of refilling
     * @return Object Account
     * @throws ServiceException
     */
    Account refill(Long account, Double amount)  throws ServiceException;

    void moneyTransfer(Long source, Long destination, Double amount) throws ServiceException;

    /**
     * Unlocking account
     * @param account Object Account
     * @throws ServiceException
     */
    void unlockingAccount(Account account) throws ServiceException;

    /**
     * Unlocking account
     * @param account Id of account
     * @throws ServiceException
     */
    void unlockingAccount(Long account) throws ServiceException;

    /**
     * To get list of all accounts with filter
     * @param orderState Order of sorting accounts
     * @param pageSize Size of elements on page
     * @param firstItem Position of first element for output
     * @param accountsFilter Object AccountsFilter
     * @return List of accounts
     * @throws ServiceException
     */
    List<Account> getAllAccounts (String orderState, Integer pageSize, Integer firstItem,
                                  AccountsFilter accountsFilter) throws ServiceException;

    /**
     * To get list of locked accounts
     * @return List of locked accounts
     * @throws ServiceException
     */
    List<Account> getLockedAccounts () throws ServiceException;

    /**
     * To delete account
     * @param id_account ID of account
     * @throws ServiceException
     */
    void deleteAccount (Serializable id_account) throws ServiceException;

    /**
     * To save/update object Account
     * @param account Object Account
     * @throws ServiceException
     */
    void saveOrUpdate(Account account) throws ServiceException;

    /**
     * To get object Account by ID
     * @param id ID of account
     * @return Object account
     * @throws ServiceException
     */
    Account get(Serializable id) throws ServiceException;

    /**
     * To delete object Account by ID
     * @param id ID of account
     * @throws ServiceException
     */
    void delete(Serializable id) throws ServiceException;

    /**
     * Getting total count of accounts with filter
     * @param accountsFilter Object AccountsFilter
     * @return total count of accounts
     */
    Integer getTotalCountOfAccounts (AccountsFilter accountsFilter);

    /**
     * Getting object User by account
     * @param account Object Account
     * @return Object User
     */
    User getUserByAccount (Account account) throws ServiceException;
}
