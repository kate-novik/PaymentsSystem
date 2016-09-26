package by.it.novik.dao;

import by.it.novik.entities.User;
import by.it.novik.util.DaoException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kate Novik.
 */
@Repository("userDao")
public class UserDao extends Dao <User> implements IUserDao {

    public UserDao() {
    }

    @Override
    public User findByLogin (String login) throws DaoException {
        User user;
        try {
            Query query = getSession().getNamedQuery("findByLogin").setString("login",login);
            user = (User) query.uniqueResult();
            log.info("findByLogin() user:" + user);
        }
        catch (HibernateException e) {
            log.error("Error findByLogin() user in Dao" + e);
            throw new DaoException("Error findByLogin() user in Dao.");
        }
        return user;
    }


    @Override
    public List<User> getAllUsers () throws DaoException {
        List<User> users;
        try {
            Query query = getSession().getNamedQuery("getAllUsers");
            users = query.list();
            log.info("getAllUsers():" + users);
        }
        catch (HibernateException e) {
            log.error("Error getAllUsers() in Dao" + e);
            throw new DaoException("Error getAllUsers() in Dao.");
        }
        return users;
    }

}
