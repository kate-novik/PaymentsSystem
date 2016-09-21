package by.it.novik.dao;

import by.it.novik.util.DaoException;

import java.io.Serializable;

/**
 * Created by Kate Novik.
 */
public interface IDao <T> {
    /**
     * Сохранение или изменение существующего объекта в базе данных
     * @param t Объект для сохранения/изменения
     */
    void saveOrUpdate(T t) throws DaoException;

    /**
     * Удаление объекта из базы данных
     * @param id Идентификатор объекта для удаления
     */
    void delete(Serializable id) throws DaoException;

    /**
     * Получение объекта из базы данных по идентификатору объекта
     * @param id Идентификатор объекта
     * @return Загруженный объект
     */
    T get(Serializable id) throws DaoException;

}
