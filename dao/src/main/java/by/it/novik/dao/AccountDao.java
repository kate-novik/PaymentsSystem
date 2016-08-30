package by.it.novik.dao;

import by.it.novik.pojos.Account;
import by.it.novik.pojos.User;
import by.it.novik.util.AccountState;
import by.it.novik.util.DaoException;
import by.it.novik.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public class AccountDao extends Dao <Account> {

    public AccountDao() {
    }

    public List<Account> getAccountsByUser (User user) throws DaoException {
        List<Account> accounts;
        try {
            session = HibernateUtil.getHibernateUtil().getSession();
            //Открываем транзакцию
            transaction = session.beginTransaction();
            Query query = session.getNamedQuery("getAccountsByUser").setEntity("user",user);
            accounts = query.list();
            log.info("getAccountsByUser():" + accounts);
            //При отсутствии исключения коммитим транзакцию
            transaction.commit();
            log.info("getAccountsByUser() (commit):" + accounts);
        }
        catch (HibernateException e) {
            log.error("Error getAccountsByUser() in Dao" + e);
            //Откатываем транзакцию
            transaction.rollback();
            throw new DaoException("Error getAccountsByUser() in Dao.");
        }
        return accounts;
    }

    public List<Account> getAllAccounts () throws DaoException {
        List<Account> accounts;
        try {
            session = HibernateUtil.getHibernateUtil().getSession();
            //Открываем транзакцию
            transaction = session.beginTransaction();
            Query query = session.getNamedQuery("getAllAccounts");
            accounts = query.list();
            log.info("getAllAccounts():" + accounts);
            //При отсутствии исключения коммитим транзакцию
            transaction.commit();
            log.info("getAllAccounts() (commit):" + accounts);
        }
        catch (HibernateException e) {
            log.error("Error getAllAccounts() in Dao" + e);
            //Откатываем транзакцию
            transaction.rollback();
            throw new DaoException("Error getAllAccounts() in Dao.");
        }
        return accounts;
    }

    public List<Account> getLockedAccounts () throws DaoException {
        List<Account> accounts;
        try {
            session = HibernateUtil.getHibernateUtil().getSession();
            //Открываем транзакцию
            transaction = session.beginTransaction();
            Query query = session.getNamedQuery("getLockedAccounts");
            accounts = query.list();
            log.info("getLockedAccounts():" + accounts);
            //При отсутствии исключения коммитим транзакцию
            transaction.commit();
            log.info("getLockedAccounts() (commit):" + accounts);
        }
        catch (HibernateException e) {
            log.error("Error getLockedAccounts() in Dao" + e);
            //Откатываем транзакцию
            transaction.rollback();
            throw new DaoException("Error getLockedAccounts() in Dao.");
        }
        return accounts;

    }
}
