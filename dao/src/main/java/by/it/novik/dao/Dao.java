package by.it.novik.dao;

import by.it.novik.util.DaoException;
import by.it.novik.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Created by Kate Novik.
 */
public class Dao <T> implements IDao<T> {
    //Поле объекта логгера
    protected static Logger log = Logger.getLogger(Dao.class);
    //Поле транзакции
    protected Transaction transaction = null;
    //Поле Hibernate сессии
    protected Session session = null;
    private Class<T> persistentClass = null;

    public Dao () {
    }


    public void saveOrUpdate(T t) throws DaoException {
        try {
            session = HibernateUtil.getHibernateUtil().getSession();
            //Открываем транзакцию
            transaction = session.beginTransaction();
            session.saveOrUpdate(t);
            log.info("saveOrUpdate(t):" + t);
            //При отсутствии исключения коммитим транзакцию
            transaction.commit();
            log.info("saveOrUpdate (commit):" + t);
        }
        catch (HibernateException e) {
            log.error("Error saveOrUpdate in Dao" + e);
            //Откатываем транзакцию
            transaction.rollback();
            throw new DaoException("Error saveOrUpdate in Dao.");
        }
    }

    public void delete(Serializable id) throws DaoException {
        try {
            session = HibernateUtil.getHibernateUtil().getSession();
            //Открываем транзакцию
            transaction = session.beginTransaction();
            //Получаем объект из базы данных по id и типу класса
            T object = (T) session.get(getPersistentClass(),id);
            if (object != null) {
                //Удаляем объект
                session.delete(object);
            }
            else {
                throw new DaoException("Object for deleting don't exist!");
            }
            //При отсутствии исключения коммитим
            transaction.commit();
            log.info("delete(id):" + object);
        } catch (HibernateException e) {
            log.error("Error delete in Dao" + e);
            //Откатываем транзакцию
            transaction.rollback();
            throw new DaoException("Error delete in Dao.");
        }
    }

    public T load(Serializable id) throws DaoException {
        session = HibernateUtil.getHibernateUtil().getSession();
        //Создаем ссылку на загруженный объект
        T object = null;
        try {
            //Открываем транзакцию
            transaction = session.beginTransaction();
            object = (T) session.load(getPersistentClass(), id);
            log.info("load(id) class:" + object);
            //При отсутствии исключения коммитим
            transaction.commit();
        } catch (HibernateException e) {
            log.error("Error load(id) " + getPersistentClass() + " in Dao" + e);
            //Откатываем транзакцию
            transaction.rollback();
            throw new DaoException("Error load in Dao.");
        }
        return object;
    }

    public T get(Serializable id) throws DaoException {
        session = HibernateUtil.getHibernateUtil().getSession();
        //Создаем ссылку на полученный объект из БД
        T object = null;
        try {
            transaction = session.beginTransaction();
            object = (T) session.get(getPersistentClass(), id);
            transaction.commit();
            log.info("get(id) class:" + object);
        } catch (HibernateException e) {
            //Откатываем транзакцию
            transaction.rollback();
            log.error("Error get(id) " + getPersistentClass() + " in Dao" + e);
            throw new DaoException("Error get in Dao.");
        }
        return object;
    }

    /**
     * Получаем тип параметризованного класса (т.к. в runtime не определишь без рефлексии)
     * @return тип класса
     */
    private Class<T> getPersistentClass() {
        if (persistentClass == null) {
            this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return persistentClass;
    }
}
