package by.it.novik.services;

import by.it.novik.entities.Role;
import by.it.novik.util.ServiceException;
import java.io.Serializable;

/**
 * Created by Kate Novik.
 */
public interface IRoleService {

    /**
     * To get object Role by name
     * @param name Name of Role
     * @return Object Role
     * @throws ServiceException
     */
    Role getRoleByName (String name) throws ServiceException;

    /**
     * To save/update object Role
     * @param role Object Role
     * @throws ServiceException
     */
    void saveOrUpdate(Role role) throws ServiceException;

    /**
     * To get object Role by ID
     * @param id ID of Role
     * @return Object Role
     */
    Role get(Serializable id) throws ServiceException;

    /**
     * To delete object Role by ID
     * @param id ID of Role
     */
    void delete(Serializable id) throws ServiceException;
}
