package by.it.novik.services;


import by.it.novik.dao.UserDao;
import by.it.novik.pojos.User;
import by.it.novik.util.DaoException;
import by.it.novik.util.ServiceException;

import java.util.List;

/**
 * Created by Kate Novik.
 */
public class UserService extends BaseService<User> implements IUserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        super(userDao);
        this.userDao = userDao;
    }

    @Override
    public User findByLoginAndPass(String login, String password) throws ServiceException {
        User user;
        try {
            user = userDao.findByLoginAndPass(login, password);
        }
        catch (DaoException d) {
            log.error("Error findByLoginAndPass() user in UserService." + d);
            throw new ServiceException("Error findByLoginAndPass() user in UserService.");
        }
        return user;
    }

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
}
