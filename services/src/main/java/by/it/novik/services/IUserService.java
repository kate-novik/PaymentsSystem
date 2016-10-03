package by.it.novik.services;


import by.it.novik.entities.User;
import by.it.novik.util.ServiceException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IUserService {

    /**
     * To find user by login
     * @param login User's login
     * @return Object User
     * @throws ServiceException
     */
    User findByLogin (String login) throws ServiceException;

    /**
     * To get list of users
     * @return List of Users
     * @throws ServiceException
     */
    List<User> getAllUsers () throws ServiceException;

    /**
     * To save/update object User
     * @param user Object User
     * @throws ServiceException
     */
    void saveOrUpdate(User user) throws ServiceException;

    /**
     * To create object User with role
     * @param user Object User
     * @param roleName Name of Role
     * @throws ServiceException
     */
    void create(User user, String roleName) throws ServiceException;

    /**
     * To get object User by ID
     * @param id ID of user
     * @return Object User
     * @throws ServiceException
     */
    User get(Serializable id) throws ServiceException;

    /**
     * To delete object User by ID
     * @param id ID of User
     * @throws ServiceException
     */
    void delete(Serializable id) throws ServiceException;
}
