package by.it.novik.dao;

import by.it.novik.pojos.User;
import by.it.novik.util.DaoException;

import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IUserDao {
    User findByLogin (String login) throws DaoException;
   // User findByLoginAndPass (String login, String password) throws DaoException;
    List<User> getAllUsers () throws DaoException;
}
