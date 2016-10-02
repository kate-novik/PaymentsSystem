package by.it.novik.dao;

import by.it.novik.entities.User;
import by.it.novik.util.DaoException;

import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IUserDao {

    /**
     * To find user by login
     * @param login User's login
     * @return Object User
     * @throws DaoException
     */
    User findByLogin (String login) throws DaoException;

    /**
     * To get list of users
     * @return List of Users
     * @throws DaoException
     */
    List<User> getAllUsers () throws DaoException;
}
