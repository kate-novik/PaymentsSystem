package by.it.novik.services;

import by.it.novik.dao.AccountDao;
import by.it.novik.pojos.Account;
import by.it.novik.pojos.User;
import by.it.novik.util.AccountState;
import by.it.novik.util.DaoException;
import by.it.novik.util.ServiceException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public class AccountService extends BaseService<Account> implements IAccountService {

    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void saveOrUpdate(Account account) throws ServiceException {
        account.setState(AccountState.WORKING);
        account.setBalance(0);
        super.saveOrUpdate(account);
    }

    @Override
    public List<Account> getAccountsByUser(Serializable id_user, String orderState) throws ServiceException {
        List<Account> accounts;
        try {
            User user = Service.getService().getUserService().get(id_user);
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
        super.saveOrUpdate(account);
    }

    @Override
    public void unlockingAccount(Account account) throws ServiceException {
        //Меняем поле счета на рабочее
        account.setState(AccountState.WORKING);
        super.saveOrUpdate(account);
    }

    @Override
    public void deleteAccount(Serializable id_account) throws ServiceException {
        //Получаем объект из базы данных по id и типу класса
        Account account = get(id_account);
        //Устанавливаем статус для аккаунта на удален
        account.setState(AccountState.DELETED);
        saveOrUpdate(account);
    }
}
