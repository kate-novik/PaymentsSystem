package by.it.novik.dao;

import by.it.novik.entities.Role;
import by.it.novik.util.DaoException;

/**
 * Created by Kate Novik.
 */
public interface IRoleDao {

    /**
     * To get object Role by name
     * @param name Roles name
     * @return Object role
     * @throws DaoException
     */
    Role getRoleByName (String name) throws DaoException;

}
