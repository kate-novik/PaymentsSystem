package by.it.novik.dao;

import by.it.novik.util.DaoException;

import java.io.Serializable;

/**
 * Created by Kate Novik.
 */
public interface IDao <T> {
    /**
     * Сохранить или изменить существующий объект в базе данных
     * @param t Объект для сохранения/изменения
     */
    void saveOrUpdate(T t) throws DaoException;

    /**
     * Удалить объект из базы данных
     * @param id Идентификатор объекта для удаления
     */
    void delete(Serializable id) throws DaoException;

    /**
     * Загрузить объект из базы данных по идентификатору
     * @param id Идентификатор объекта
     * @return Загруженный объект
     */
    T load(Serializable id) throws DaoException;

    /**
     * Получить объект из базы данных по идентификатору объекта
     * @param id Идентификатор объекта
     * @return Загруженный объект
     */
    T get(Serializable id) throws DaoException;

}
