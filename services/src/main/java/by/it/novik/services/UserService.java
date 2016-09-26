package by.it.novik.services;


import by.it.novik.dao.UserDao;
import by.it.novik.entities.Address;
import by.it.novik.entities.Passport;
import by.it.novik.entities.Role;
import by.it.novik.entities.User;
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
@Transactional
public class UserService implements IUserService {
    protected static Logger log = Logger.getLogger (UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleService roleService;

    public UserService () {

    }


    @Override
    public User findByLogin(String login) throws ServiceException {
        User user;
        try {
            user = userDao.findByLogin(login);
        }
        catch (DaoException d) {
            log.error("Error findByLogin() user in UserService." + d);
            throw new ServiceException("Error in finding user.");
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
            throw new ServiceException("Error in getting users.");
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
            throw new ServiceException("Error save/update() user." );
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
            throw new ServiceException("Error in getting user." );
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
            throw new ServiceException("Error in deleting user.");
        }
    }
}
