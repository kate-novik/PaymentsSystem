package by.it.novik.services;

import by.it.novik.pojos.Role;
import by.it.novik.util.ServiceException;

import java.io.Serializable;

/**
 * Created by Kate Novik.
 */
public interface IRoleService {

    /**
     * Получение роли по названию роли
     * @param name Название роли
     * @return Объект Role
     * @throws ServiceException
     */
    Role getRoleByName (String name) throws ServiceException;

    /**
     * Создание/обновление записи объекта в БД
     * @param role Объект для записи/обновления
     * @throws ServiceException
     */
    void saveOrUpdate(Role role) throws ServiceException;

    /**
     * Чтение записи (объекта) из БД
     * @param id Номер записи
     * @return Прочтенный объект User
     */
    Role get(Serializable id) throws ServiceException;

    /**
     * Удаление записи (объекта) из БД по уникальному идентификатору
     * @param id Номер записи
     */
    void delete(Serializable id) throws ServiceException;
}
