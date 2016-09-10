package by.it.novik.dao;

import by.it.novik.pojos.Role;
import by.it.novik.util.DaoException;

/**
 * Created by Kate Novik.
 */
public interface IRoleDao {

    Role getRoleByName (String name) throws DaoException;

}
