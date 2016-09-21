package by.it.novik.dao;

import by.it.novik.pojos.Role;
import by.it.novik.util.DaoException;
import by.it.novik.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * Created by Kate Novik.
 */
@Repository("roleDao")
public class RoleDao extends Dao <Role> implements IRoleDao {

    public RoleDao() {
    }

    @Override
    public Role getRoleByName (String name) throws DaoException {
        Role role;
        try {
            Session session = getSession();
            Query query = session.getNamedQuery("findByName");
            query.setString("role",name);
            role = (Role) query.uniqueResult();
            log.info("findByName() role:" + role);
        }
        catch (HibernateException e) {
            log.error("Error findByName() role in Dao" + e);
            throw new DaoException("Error findByName() role in Dao.");
        }
        return role;
    }

}
