package by.it.novik.services;


import by.it.novik.dao.UserDao;
import by.it.novik.pojos.Address;
import by.it.novik.pojos.Passport;
import by.it.novik.pojos.Role;
import by.it.novik.pojos.User;
import by.it.novik.util.DaoException;
import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kate Novik.
 */
@Service("userService")
//@Transactional
public class UserService implements IUserService {
    protected static Logger log = Logger.getLogger (UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    public UserService () {

    }


//    @Override
//    public User findByLoginAndPass(String login, String password) throws ServiceException {
//        User user;
//        try {
//            user = userDao.findByLoginAndPass(login, password);
//        }
//        catch (DaoException d) {
//            log.error("Error findByLoginAndPass() user in UserService." + d);
//            throw new ServiceException("Error findByLoginAndPass() user in UserService.");
//        }
//        return user;
//    }

    @Override
    public User findByLogin(String login) throws ServiceException {
        User user;
        try {
            user = userDao.findByLogin(login);
        }
        catch (DaoException d) {
            log.error("Error findByLogin() user in UserService." + d);
            throw new ServiceException("Error findByLogin() user in UserService.");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        List<User> users;
        try {
            users = userDao.getAllUsers();
        }
        catch (DaoException d) {
            log.error("Error getAllUsers() in UserService." + d);
            throw new ServiceException("Error getAllUsers() in UserService.");
        }
        return users;
    }

    @Override
    public void saveOrUpdate(User user) throws ServiceException {
        try {
            userDao.saveOrUpdate(user);
        }
        catch (DaoException d) {
            log.error("Error saveOrUpdate() user in UserDao " + d);
            throw new ServiceException("Error saveOrUpdate() user in UserDao." );
        }
    }

    @Override
    public void create(User user, String roleName) throws ServiceException {
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        Role role = roleService.getRoleByName(roleName);
        user.setRole(role);
        Address address = user.getAddress();
        Passport passport = user.getPassport();
        address.setUser(user);
        passport.setUser(user);

//        passport.

        saveOrUpdate(user);
    }

    @Override
    public User get(Serializable id) throws ServiceException {
        User user;
        try {
            user = userDao.get(id);
        }
        catch (DaoException d) {
            log.error("Error get() user in UserDao " + d);
            throw new ServiceException("Error get() user in UserDao." );
        }
        return user;
    }

    @Override
    public void delete(Serializable id) throws ServiceException {
        try {
            userDao.delete(id);
        }
        catch (DaoException d) {
            log.error("Error delete() user in UserDao " + d);
            throw new ServiceException("Error delete() user in UserDao.");
        }
    }
}
