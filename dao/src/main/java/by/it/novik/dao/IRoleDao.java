package by.it.novik.dao;

import by.it.novik.pojos.Role;
import by.it.novik.util.DaoException;

/**
 * Created by Kate Novik.
 */
public interface IRoleDao {

    /**
     * Получение объекта Role по названию роли
     * @param name Название роли
     * @return Объект Role
     * @throws DaoException
     */
    Role getRoleByName (String name) throws DaoException;

}
