package by.it.novik.services;


import by.it.novik.pojos.User;
import by.it.novik.util.ServiceException;

import java.io.Serializable;
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

    /**
     * Создание/обновление записи объекта в БД
     * @param user Объект для записи/обновления
     * @throws ServiceException
     */
    void saveOrUpdate(User user) throws ServiceException;

    void create(User user, String roleName) throws ServiceException;

    /**
     * Чтение записи (объекта) из БД
     * @param id Номер записи
     * @return Прочтенный объект User
     */
    User get(Serializable id) throws ServiceException;

    /**
     * Удаление записи (объекта) из БД по уникальному идентификатору
     * @param id Номер записи
     */
    void delete(Serializable id) throws ServiceException;
}
