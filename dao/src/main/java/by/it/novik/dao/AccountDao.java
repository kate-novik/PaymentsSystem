package by.it.novik.dao;

import by.it.novik.entities.Account;
import by.it.novik.entities.User;
import by.it.novik.util.DaoException;
import by.it.novik.valueObjects.AccountsFilter;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kate Novik.
 */
@Repository("accountDao")
public class AccountDao extends Dao <Account> implements IAccountDao {

    public AccountDao() {
    }

    @Override
    public List<Account> getAccountsByUser (User user, String orderState, Integer pageSize, Integer firstItem) throws DaoException {
        List<Account> accounts;
        //Определяем порядок сортировки счетов по умолчанию
        if (orderState == null) {
            orderState = "ASC";
        }
        try {
            Query query = getSession().getNamedQuery("getAccountsByUser").setEntity("user",user)
                    .setString("orderState",orderState);
            query.setFirstResult(firstItem);
            query.setMaxResults(pageSize);
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
    public List<Account> getAllAccounts () throws DaoException {
        List<Account> accounts;
        try {
            Query query = getSession().getNamedQuery("getAllAccounts");
            accounts = query.list();
            log.info("getAllAccounts():" + accounts);
        }
        catch (HibernateException e) {
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
    public Integer getTotalCountOfAccounts(AccountsFilter accountsFilter) {
        Criteria criteria = getSession().createCriteria(Account.class);
        Criterion balance = Restrictions.between("balance",accountsFilter.getMinBalance(),accountsFilter.getMaxBalance());
        Criterion state = Restrictions.eq("state", accountsFilter.getState());
        LogicalExpression andExp = Restrictions.and(balance,state);
        criteria.add(andExp);
        //To get total row count
        criteria.setProjection(Projections.rowCount());
        Integer countAccounts = (Integer)criteria.uniqueResult();
        return countAccounts;
    }
}
