package by.it.novik.services;

import by.it.novik.util.ServiceException;

import java.io.Serializable;

/**
 * Created by Kate Novik.
 */
public interface IService <T> {
    /**
     * Создание/обновление записи объекта в БД
     * @param object Объект для записи/обновления
     * @throws ServiceException
     */
    void saveOrUpdate(T object) throws ServiceException;

    /**
     * Чтение записи (объекта) из БД
     * @param id Номер записи
     * @return Прочтенный объект
     */
    T get(Serializable id) throws ServiceException;

    /**
     * Удаление записи (объекта) из БД по уникальному идентификатору
     * @param id Номер записи
     */
    void delete(Serializable id) throws ServiceException;

}
