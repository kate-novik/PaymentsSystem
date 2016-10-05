package by.it.novik.services;

import by.it.novik.dao.AccountDao;
import by.it.novik.entities.Account;
import by.it.novik.entities.User;
import by.it.novik.util.AccountState;
import by.it.novik.util.AccountType;
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
        account.setType(AccountType.PERSONAL);
        account.setTitle("New account");
        account.setBalance(0);
        saveOrUpdate(account);
        return account;
    }

    @Override
    public List<Account> getAccountsByUser(Serializable id_user) throws ServiceException {
        List<Account> accounts;
        try {
            User user = userService.get(id_user);
            accounts = accountDao.getAccountsByUser(user);
        }
        catch (DaoException d){
            log.error("Error getAccountsByUser() in AccountService." + d);
            throw new ServiceException("Error in getting accounts in AccountService.");
        }
        return accounts;
    }

    @Override
    public List<Account> getBusinessAccounts() throws ServiceException {
        List<Account> accounts;
        try {
            accounts = accountDao.getBusinessAccounts();
        }
        catch (DaoException d){
            log.error("Error getBusinessAccounts() in AccountService." + d);
            throw new ServiceException("Error in getting accounts in AccountService.");
        }
        return accounts;
    }

    @Override
    public List<Account> getAllAccounts(String orderState, Integer pageSize, Integer firstItem,
                                        AccountsFilter accountsFilter) throws ServiceException {
        List<Account> accounts;
        try {
            accounts = accountDao.getAllAccounts(orderState, pageSize, firstItem, accountsFilter);
        }
        catch (DaoException d){
            log.error("Error getAllAccounts() in AccountService." + d);
            throw new ServiceException("Error in getting accounts in AccountService.");
        }
        return accounts;
    }

    @Override
    public List<Account> getLockAccounts() throws ServiceException {
        List<Account> accounts;
        try {
            accounts = accountDao.getLockedAccounts();
        }
        catch (DaoException d){
            log.error("Error getLockAccounts() in AccountService." + d);
            throw new ServiceException("Error in getting locked accounts in AccountService.");
        }
        return accounts;
    }

    @Override
    public void lockAccount(Account account) throws ServiceException {
        //Changing the field state in LOCKED
        account.setState(AccountState.LOCKED);
        saveOrUpdate(account);
    }

    @Override
    public void lockAccount(Long id) throws ServiceException {
        Account account = get(id);
        lockAccount(account);
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
    public void unlockAccount(Account account) throws ServiceException {
        //Changing the field state in WORKING
        account.setState(AccountState.WORKING);
        saveOrUpdate(account);
    }

    @Override
    public void unlockAccount(Long id) throws ServiceException {
        Account account = get(id);
        unlockAccount(account);
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
    public Number getTotalCountOfAccounts (AccountsFilter accountsFilter){
        return accountDao.getTotalCountOfAccounts(accountsFilter);
    }

    @Override
    public User getUserByAccount(Account account) throws ServiceException {
        User user;
        try{
            user = accountDao.getUserByAccount(account);
        }
        catch (DaoException e){
            log.error("Error getUserByAccount() in AccountDao " + e);
            throw new ServiceException("Error in getting user by account in AccountService.");
        }
        return user;
    }
}
