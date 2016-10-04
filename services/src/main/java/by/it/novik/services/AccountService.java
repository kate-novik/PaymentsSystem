package by.it.novik.services;

import by.it.novik.dao.AccountDao;
import by.it.novik.entities.Account;
import by.it.novik.entities.User;
import by.it.novik.util.AccountState;
import by.it.novik.util.DaoException;
import by.it.novik.util.ServiceException;
import by.it.novik.valueObjects.AccountsFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kate Novik.
 */
@Service("accountService")
@Transactional
public class AccountService implements IAccountService {

    protected static Logger log = Logger.getLogger (AccountService.class);

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private IUserService userService;

    public AccountService(){

    }

    @Override
    public Account create(User user) throws ServiceException {
        Account account = new Account();
        account.setUser(user);
        account.setState(AccountState.WORKING);
        account.setBalance(0);
        saveOrUpdate(account);
        return account;
    }

    @Override
    public List<Account> getAccountsByUser(Serializable id_user, String orderState, Integer pageSize, Integer firstItem) throws ServiceException {
        List<Account> accounts;
        try {
            User user = userService.get(id_user);
            accounts = accountDao.getAccountsByUser(user, orderState, pageSize, firstItem);
        }
        catch (DaoException d){
            log.error("Error getAccountsByUser() in AccountService." + d);
            throw new ServiceException("Error in getting accounts in AccountService.");
        }
        return accounts;
    }

    @Override
    public List<Account> getAllAccounts() throws ServiceException {
        List<Account> accounts;
        try {
            accounts = accountDao.getAllAccounts();
        }
        catch (DaoException d){
            log.error("Error getAllAccounts() in AccountService." + d);
            throw new ServiceException("Error in getting accounts in AccountService.");
        }
        return accounts;
    }

    @Override
    public List<Account> getLockedAccounts() throws ServiceException {
        List<Account> accounts;
        try {
            accounts = accountDao.getLockedAccounts();
        }
        catch (DaoException d){
            log.error("Error getLockedAccounts() in AccountService." + d);
            throw new ServiceException("Error in getting locked accounts in AccountService.");
        }
        return accounts;
    }

    @Override
    // please rename to lockAccount()
    public void lockingAccount(Account account) throws ServiceException {
        //Changing the field state in LOCKED
        account.setState(AccountState.LOCKED);
        saveOrUpdate(account);
    }

    @Override
    // please rename to lockAccount()
    public void lockingAccount(Long id) throws ServiceException {
        Account account = get(id);
        lockingAccount(account);
    }

    @Override
    public Account refill(Long id, Double amount) throws ServiceException {
        Account account = get(id);
        account.setBalance(account.getBalance() + amount);
        saveOrUpdate(account);
        return account;
    }

    @Override
    public void moneyTransfer(Long sourceId, Long destinationId, Double amount) throws ServiceException {
        Account source = get(sourceId);
        Account destination = get(destinationId);
        source.setBalance(source.getBalance() - amount);
        destination.setBalance(destination.getBalance() + amount);
        saveOrUpdate(source);
        saveOrUpdate(destination);
    }

    @Override
    // please rename to unlockAccount()
    public void unlockingAccount(Account account) throws ServiceException {
        //Changing the field state in WORKING
        account.setState(AccountState.WORKING);
        saveOrUpdate(account);
    }

    @Override
    public void unlockingAccount(Long id) throws ServiceException {
        Account account = get(id);
        unlockingAccount(account);
    }

    @Override
    public void deleteAccount(Serializable id_account) throws ServiceException {
        //Getting object by id
        Account account = get(id_account);
        //Setting status for account in DELETED
        account.setState(AccountState.DELETED);
        saveOrUpdate(account);
    }

    @Override
    public void saveOrUpdate(Account account) throws ServiceException {
        try {
            accountDao.saveOrUpdate(account);
        }
        catch (DaoException d) {
            log.error("Error saveOrUpdate() account in AccountDao " + d);
            throw new ServiceException("Account wasn't created/updated." );
        }
    }

    @Override
    public Account get(Serializable id) throws ServiceException {
        Account account;
        try {
            account = accountDao.get(id);
        }
        catch (DaoException d) {
            log.error("Error get() account in AccountDao " + d);
            throw new ServiceException("Error in getting account in AccountService." );
        }
        return account;
    }

    @Override
    public void delete(Serializable id) throws ServiceException {
        try {
            accountDao.delete(id);
        }
        catch (DaoException d) {
            log.error("Error delete() account in AccountDao " + d);
            throw new ServiceException("Error in deleting account in AccountService.");
        }
    }

    @Override
    public Integer getTotalCountOfAccounts (AccountsFilter accountsFilter){
        return accountDao.getTotalCountOfAccounts(accountsFilter);
    }
}
