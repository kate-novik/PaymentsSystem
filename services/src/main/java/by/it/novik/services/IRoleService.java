package by.it.novik.services;

import by.it.novik.pojos.Role;
import by.it.novik.util.ServiceException;

/**
 * Created by Kate Novik.
 */
public interface IRoleService {

    Role getRoleByName (String name) throws ServiceException;
}
