package by.it.novik.services;

import by.it.novik.config.ServiceConfig;
import by.it.novik.dao.RoleDao;
import by.it.novik.entities.*;
import by.it.novik.util.DaoException;
import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Kate Novik.
 */
@Service("roleService")
@Transactional
public class RoleService implements IRoleService {
    protected static Logger log = Logger.getLogger (RoleService.class);

    @Autowired
    private RoleDao roleDao;

    public RoleService () {
    }

    @Override
    public Role getRoleByName(String name) throws ServiceException {
        Role role;
        try{
            role = roleDao.getRoleByName(name);
        } catch (DaoException e) {
            log.error("Error getRoleByName() user in RoleService." + e);
            throw new ServiceException("Error in getting role in RoleService.");
        }
        return role;
    }

    @Override
    public void saveOrUpdate(Role role) throws ServiceException {
        try {
            roleDao.saveOrUpdate(role);
        }
        catch (DaoException d) {
            log.error("Error saveOrUpdate() role in RoleDao " + d);
            throw new ServiceException("Error save/update() role in RoleService." );
        }
    }

    @Override
    public Role get(Serializable id) throws ServiceException {
        Role user;
        try {
            user = roleDao.get(id);
        }
        catch (DaoException d) {
            log.error("Error get() role in RoleDao " + d);
            throw new ServiceException("Error in getting role in RoleService." );
        }
        return user;
    }

    @Override
    public void delete(Serializable id) throws ServiceException {
        try {
            roleDao.delete(id);
        }
        catch (DaoException d) {
            log.error("Error delete() role in RoleDao " + d);
            throw new ServiceException("Error in deleting role in RoleService.");
        }
    }
}
