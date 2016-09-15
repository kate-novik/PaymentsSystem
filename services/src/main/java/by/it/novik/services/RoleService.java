package by.it.novik.services;

import by.it.novik.dao.RoleDao;
import by.it.novik.pojos.Role;
import by.it.novik.util.DaoException;
import by.it.novik.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Kate Novik.
 */
@Service("roleService")
@Transactional
public class RoleService extends BaseService<Role> implements IRoleService {

    @Autowired
    private RoleDao roleDao;

    public RoleService () {
        dao = roleDao;
    }


    @Override
    public Role getRoleByName(String name) throws ServiceException {
        Role role;
        try{
            role = roleDao.getRoleByName(name);
        } catch (DaoException e) {
            log.error("Error getRoleByName() user in RoleService." + e);
            throw new ServiceException("Error getRoleByName() user in RoleService.");
        }
        return role;
    }
}
