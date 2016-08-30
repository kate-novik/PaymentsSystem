package by.it.novik.services;

import by.it.novik.pojos.User;
import by.it.novik.util.ServiceException;

/**
 * Created by Kate Novik.
 */
public interface ISecurityService {

    /**
     * Поиск User по логину и паролю
     * @param login Логин
     * @param password Пароль
     * @return Объект User
     */
    User findUser (String login, String password) throws ServiceException;

    /**
     * Создание объекта User в базе данных
     * @param user Объект User
     */
    void createUser (User user) throws ServiceException;
}
