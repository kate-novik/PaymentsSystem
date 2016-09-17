package by.it.novik.services;

import by.it.novik.config.ServiceConfig;
import by.it.novik.dao.RoleDao;
import by.it.novik.pojos.Role;
import by.it.novik.pojos.User;
import by.it.novik.util.DaoException;
import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Created by Kate Novik.
 */
@Service("roleService")
//@Transactional
public class RoleService implements IRoleService {
    protected static Logger log = Logger.getLogger (RoleService.class);

    @Autowired
    private RoleDao roleDao;

    public RoleService () {
    }

//    public static void main(String[] args) throws ServiceException {
//        AbstractApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfig.class);
//
//        RoleService service = (RoleService) context.getBean("roleService");
//
//        Role role = service.getRoleByName("user");
//        System.out.println(role);
//
//        context.close();
//    }

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

    @Override
    public void saveOrUpdate(Role role) throws ServiceException {
        try {
            roleDao.saveOrUpdate(role);
        }
        catch (DaoException d) {
            log.error("Error saveOrUpdate() role in RoleDao " + d);
            throw new ServiceException("Error saveOrUpdate() role in RoleDao." );
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
            throw new ServiceException("Error get() role in RoleDao." );
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
            throw new ServiceException("Error delete() role in RoleDao.");
        }
    }
}
