package by.it.novik.services;

import by.it.novik.dao.AccountDao;
import by.it.novik.pojos.Account;
import by.it.novik.pojos.User;
import by.it.novik.util.AccountState;
import by.it.novik.util.DaoException;
import by.it.novik.util.ServiceException;
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
    public List<Account> getAccountsByUser(Serializable id_user, String orderState) throws ServiceException {
        List<Account> accounts;
        try {
            User user = userService.get(id_user);
            accounts = accountDao.getAccountsByUser(user, orderState);
        }
        catch (DaoException d){
            log.error("Error getAccountsByUser() in AccountService." + d);
            throw new ServiceException("Error getAccountsByUser() in AccountService.");
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
            throw new ServiceException("Error getAllAccounts() in AccountService.");
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
            throw new ServiceException("Error getLockedAccounts() in AccountService.");
        }
        return accounts;
    }

    @Override
    public void lockingAccount(Account account) throws ServiceException {
        //Меняем поле счета на заблокированное
        account.setState(AccountState.LOCKED);
        saveOrUpdate(account);
    }

    @Override
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
    public void unlockingAccount(Account account) throws ServiceException {
        //Меняем поле счета на рабочее
        account.setState(AccountState.WORKING);
        saveOrUpdate(account);
    }

    @Override
    public void deleteAccount(Serializable id_account) throws ServiceException {
        //Получаем объект из базы данных по id и типу класса
        Account account = get(id_account);
        //Устанавливаем статус для аккаунта на удален
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
            throw new ServiceException("Error saveOrUpdate() account in AccountDao." );
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
            throw new ServiceException("Error get() account in AccountDao." );
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
            throw new ServiceException("Error delete() account in AccountDao.");
        }
    }
}
