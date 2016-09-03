package by.it.novik.dao;

import by.it.novik.pojos.Role;
import by.it.novik.util.DaoException;
import by.it.novik.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 * Created by Kate Novik.
 */
public class RoleDao extends Dao <Role> {

    public RoleDao() {
    }

    public Role getRoleByName (String name) throws DaoException {
        Role role;
        try {
            session = HibernateUtil.getHibernateUtil().getSession();
            //Открываем транзакцию
            transaction = session.beginTransaction();
            Query query = session.getNamedQuery("findByName").setString("role",name);
            role = (Role) query.uniqueResult();
            log.info("findByName() role:" + role);
            //При отсутствии исключения коммитим транзакцию
            transaction.commit();
            log.info("findByName() role (commit):" + role);
        }
        catch (HibernateException e) {
            log.error("Error findByName() role in Dao" + e);
            //Откатываем транзакцию
            transaction.rollback();
            throw new DaoException("Error findByName() role in Dao.");
        }
        return role;
    }

}
