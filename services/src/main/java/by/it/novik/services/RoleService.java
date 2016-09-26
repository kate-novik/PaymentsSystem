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

    public static void main(String[] args) throws ServiceException, DaoException {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfig.class);


        RoleService service1 = (RoleService) context.getBean("roleService");
        UserService service = (UserService) context.getBean("userService");
        Address address = new Address();
        address.setCity("Minsk");
        address.setStreet("Yankovskogo");
        address.setHome("6");
        address.setFlat("105");

        Passport passport = new Passport();
        passport.setNumber("MP1234567");
        passport.setDateOfIssue(Date.valueOf("2014-05-15"));
        passport.setIssued("Minskiy ROVD");

        User user2 = new User();
        user2.setFirstName("Anna");
        user2.setMiddleName("Antonovna");
        user2.setLastName("Ivanova");
        user2.setAddress(address);
        user2.setEmail("ret@mail.ru");
        user2.setPassport(passport);
        user2.setLogin("q" + System.currentTimeMillis() % 10000);
        user2.setPassword("123");
        user2.setPhone("375447547878");

        Role role = service1.getRoleByName("user");
        user2.setRole(role);

//        Account account = new Account();
//        account.setBalance(256);
//        account.setState(AccountState.WORKING);
//        account.setUser(user2);
//
//        user2.getAccounts().add(account);

        address.setUser(user2);
        passport.setUser(user2);

        service.saveOrUpdate(user2);

        System.out.println(service.get(user2.getId()));
        System.out.println(service.get(user2.getId()).getAddress());
        //System.out.println(service.get(user2.getId()).getPassport());
        context.close();
    }

    @Override
    public Role getRoleByName(String name) throws ServiceException {
        Role role;
        try{
            role = roleDao.getRoleByName(name);
        } catch (DaoException e) {
            log.error("Error getRoleByName() user in RoleService." + e);
            throw new ServiceException("Error in getting role.");
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
            throw new ServiceException("Error save/update() role." );
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
            throw new ServiceException("Error in getting role." );
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
            throw new ServiceException("Error in deleting role.");
        }
    }
}
