package by.it.novik.services;


import by.it.novik.pojos.User;
import by.it.novik.util.ServiceException;

import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IUserService {

//    /**
//     * Поиск User по логину и паролю
//     * @param login Логин
//     * @param password Пароль
//     * @return Объект User
//     */
//    User findByLoginAndPass(String login, String password) throws ServiceException;

    /**
     * Поиск User по логину
     * @param login Логин
     * @return Объект User
     */
    User findByLogin (String login) throws ServiceException;

    /**
     * Получение всех объектов User
     * @return список объектов User
     */
    List<User> getAllUsers () throws ServiceException;
}
