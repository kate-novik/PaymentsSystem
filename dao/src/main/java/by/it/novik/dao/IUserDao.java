package by.it.novik.dao;

import by.it.novik.entities.User;
import by.it.novik.util.DaoException;

import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IUserDao {

    /**
     * Поиск пользователя по логину
     * @param login Логин
     * @return Объект User
     * @throws DaoException
     */
    User findByLogin (String login) throws DaoException;

    /**
     * Получение списка пользователей
     * @return Список объектов User
     * @throws DaoException
     */
    List<User> getAllUsers () throws DaoException;
}
