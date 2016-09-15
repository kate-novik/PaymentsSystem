package by.it.novik.dao;

import by.it.novik.pojos.User;
import by.it.novik.util.DaoException;
import by.it.novik.util.HibernateUtil;
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

    /**
     * Поиск User по логину
     * @param login Логин
     * @return Объект User
     */
    public User findByLogin (String login) throws DaoException {
        User user;
        try {
            //session = HibernateUtil.getHibernateUtil().getSession();
            //Открываем транзакцию
            //transaction = session.beginTransaction();
            Query query = getSession().getNamedQuery("findByLogin").setString("login",login);
            user = (User) query.uniqueResult();
            log.info("findByLogin() user:" + user);
            //При отсутствии исключения коммитим транзакцию
            //transaction.commit();
            //log.info("findByLogin() user (commit):" + user);
        }
        catch (HibernateException e) {
            log.error("Error findByLogin() user in Dao" + e);
            //Откатываем транзакцию
            //transaction.rollback();
            throw new DaoException("Error findByLogin() user in Dao.");
        }
        return user;
    }

//    /**
//     * Поиск User по логину и паролю
//     * @param login Логин
//     * @param password Пароль
//     * @return Объект User
//     */
//    public User findByLoginAndPass (String login, String password) throws DaoException {
//        User user;
//        try {
//            //session = HibernateUtil.getHibernateUtil().getSession();
//            //Открываем транзакцию
//            //transaction = session.beginTransaction();
//            Query query = getSession().getNamedQuery("findByLoginAndPass").setString("login",login).setString("password",password);
//            user = (User) query.uniqueResult();
//            log.info("findByLoginAndPass() user:" + user);
//            //При отсутствии исключения коммитим транзакцию
//            //transaction.commit();
//            //log.info("findByLoginAndPass() user (commit):" + user);
//        }
//        catch (HibernateException e) {
//            log.error("Error findByLoginAndPass() user in Dao" + e);
//            //Откатываем транзакцию
//            //transaction.rollback();
//            throw new DaoException("Error findByLoginAndPass() user in Dao.");
//        }
//        return user;
//    }

    public List<User> getAllUsers () throws DaoException {
        List<User> users;
        try {
            //session = HibernateUtil.getHibernateUtil().getSession();
            //Открываем транзакцию
            //transaction = session.beginTransaction();
            Query query = getSession().getNamedQuery("getAllUsers");
            users = query.list();
            log.info("getAllUsers():" + users);
            //При отсутствии исключения коммитим транзакцию
            //transaction.commit();
            //log.info("getAllUsers() (commit):" + users);
        }
        catch (HibernateException e) {
            log.error("Error getAllUsers() in Dao" + e);
            //Откатываем транзакцию
            //transaction.rollback();
            throw new DaoException("Error getAllUsers() in Dao.");
        }
        return users;
    }

}
