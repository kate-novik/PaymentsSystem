package by.it.novik.dao;

import by.it.novik.entities.Account;
import by.it.novik.entities.User;
import by.it.novik.util.AccountState;
import by.it.novik.util.DaoException;
import by.it.novik.valueObjects.AccountsFilter;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Kate Novik.
 */
@Repository("accountDao")
public class AccountDao extends Dao <Account> implements IAccountDao {

    public AccountDao() {
    }

    @Override
    public List<Account> getAccountsByUser (User user)
            throws DaoException {
        List<Account> accounts;
        try {
            Query query = getSession().getNamedQuery("getAccountsByUser").setEntity("user",user);
            accounts = query.list();
            log.info("getAccountsByUser():" + accounts);
        }
        catch (HibernateException e) {
            log.error("Error getAccountsByUser() in Dao" + e);
            throw new DaoException("Error getAccountsByUser() in Dao.");
        }
        return accounts;
    }

    @Override
    public List<Account> getBusinessAccounts () throws DaoException {
        List<Account> accounts;
        try {
            Query query = getSession().getNamedQuery("getBusinessAccounts");
            accounts = query.list();
            log.info("getBusinessAccounts():" + accounts);
        }
        catch (HibernateException e) {
            log.error("Error getBusinessAccounts() in Dao" + e);
            throw new DaoException("Error getBusinessAccounts() in Dao.");
        }
        return accounts;
    }

    @Override
    public List<Account> getAllAccounts (String orderState, Integer pageSize, Integer firstItem,
                                         AccountsFilter accountsFilter) throws DaoException {
        List<Account> accounts;
        Criteria criteria = getCriteriaOfFilter(accountsFilter);
        //Order for sorting
        Order order = "ASC".equals(orderState) ? Order.asc("id") : Order.desc("id");
        criteria.addOrder(order);
        criteria.setFirstResult(firstItem);
        criteria.setMaxResults(pageSize);
        try {
            accounts = criteria.list();
            log.info("getAllAccounts():" + accounts);
        } catch (HibernateException e) {
            log.error("Error getAllAccounts() in Dao" + e);
            throw new DaoException("Error getAllAccounts() in Dao.");
        }
        return accounts;
    }

    @Override
    public List<Account> getLockedAccounts () throws DaoException {
        List<Account> accounts;
        try {
            Query query = getSession().getNamedQuery("getLockedAccounts");
            accounts = query.list();
            log.info("getLockedAccounts():" + accounts);
        }
        catch (HibernateException e) {
            log.error("Error getLockedAccounts() in Dao" + e);
            throw new DaoException("Error getLockedAccounts() in Dao.");
        }
        return accounts;

    }

    @Override
    public Number getTotalCountOfAccounts(AccountsFilter accountsFilter) {
        Criteria criteria = getCriteriaOfFilter(accountsFilter);
        //To get total row count
        criteria.setProjection(Projections.rowCount());
        return (Number) criteria.uniqueResult();
    }

    @Override
    public User getUserByAccount(Account account) throws DaoException {
        User user;
        try {
            Query query = getSession().getNamedQuery("getUserByAccount").setEntity("id", account.getId());
            user = (User) query.uniqueResult();
            log.info("getUserByAccount():" + user);
        }
        catch (HibernateException e) {
            log.error("Error getUserByAccount() in Dao" + e);
            throw new DaoException("Error getAccountsByUser() in Dao.");
        }
        return user;
    }

    /**
     * Getting Object Criteria depending on the filter values AccountsFilter
     * @param accountsFilter Object AccountsFilter
     * @return Object Criteria
     */
    private Criteria getCriteriaOfFilter(AccountsFilter accountsFilter) {
        AccountState state =accountsFilter.getState();
        double minBalance = accountsFilter.getMinBalance();
        double maxBalance =accountsFilter.getMaxBalance();
        Criteria criteria = getSession().createCriteria(Account.class);
        //To check existing filters
        if (state != null && minBalance != 0 && maxBalance != 0) {
            Criterion balance = Restrictions.between("balance", minBalance, maxBalance);
            Criterion accountsState = Restrictions.eq("state", state);
            LogicalExpression andExp = Restrictions.and(balance, accountsState);
            criteria.add(andExp);
        } else if (state != null && minBalance == 0 && maxBalance == 0) {
            criteria.add(Restrictions.eq("state", state));
        } else if (state == null && minBalance != 0 && maxBalance != 0) {
            criteria.add(Restrictions.between("balance", minBalance, maxBalance));
        } else if (state == null && minBalance == 0 && maxBalance != 0) {
            criteria.add(Restrictions.le("balance", maxBalance));
        } else if (state == null && minBalance != 0 && maxBalance == 0) {
            criteria.add(Restrictions.ge("balance", minBalance));
        } else if (state != null && minBalance != 0 && maxBalance == 0) {
            Criterion balance = Restrictions.ge("balance", minBalance);
            Criterion accountsState = Restrictions.eq("state", state);
            LogicalExpression andExp = Restrictions.and(balance, accountsState);
            criteria.add(andExp);
        } else if (state != null && minBalance == 0 && maxBalance != 0) {
            Criterion balance = Restrictions.le("balance", maxBalance);
            Criterion accountsState = Restrictions.eq("state", state);
            LogicalExpression andExp = Restrictions.and(balance, accountsState);
            criteria.add(andExp);
        }
        return criteria;
    }
}
