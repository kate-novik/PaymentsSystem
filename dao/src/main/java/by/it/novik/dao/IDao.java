package by.it.novik.dao;

import by.it.novik.util.DaoException;

import java.io.Serializable;

/**
 * Created by Kate Novik.
 */
public interface IDao <T> {
    /**
     * To Save or update object in data base
     * @param t Object for saving
     */
    void saveOrUpdate(T t) throws DaoException;

    /**
     * To delete object from data base
     * @param id Object's id
     */
    void delete(Serializable id) throws DaoException;

    /**
     * To get object from data base with id
     * @param id Object's id
     * @return Getting object
     */
    T get(Serializable id) throws DaoException;

}
