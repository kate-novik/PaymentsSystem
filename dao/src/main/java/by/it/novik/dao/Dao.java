package by.it.novik.dao;

import by.it.novik.util.DaoException;
import by.it.novik.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Created by Kate Novik.
 */
public class Dao <T> implements IDao<T> {
    private final Class<T> persistentClass;
    //Поле объекта логгера
    protected static Logger log = Logger.getLogger(Dao.class);
    //Поле транзакции
    //protected Transaction transaction = null;
    //Поле Hibernate сессии
    //protected Session session = null;

    @SuppressWarnings("unchecked")
    public Dao () {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        Session session;
        try{
            session = sessionFactory.getCurrentSession();
        }
        catch (HibernateException e)
        {
            session = sessionFactory.openSession();
        }
        return session;
    }


    public void saveOrUpdate(T t) throws DaoException {
      //  Transaction transaction = null;
        try {
       //    Session session = HibernateUtil.getHibernateUtil().getSession();
//            //Открываем транзакцию
         //   transaction = session.beginTransaction();
            getSession().saveOrUpdate(t);
           // session.saveOrUpdate(t);
            log.info("saveOrUpdate(t):" + t);
//            //При отсутствии исключения коммитим транзакцию
         //  transaction.commit();
//            log.info("saveOrUpdate (commit):" + t);
        }
        catch (HibernateException e) {
            log.error("Error saveOrUpdate in Dao" + e);
            //Откатываем транзакцию
           // transaction.rollback();
            throw new DaoException("Error saveOrUpdate in Dao.");
        }
    }

    @SuppressWarnings("unchecked")
    public void delete(Serializable id) throws DaoException {
        try {
           // session = HibernateUtil.getHibernateUtil().getSession();
            //Открываем транзакцию
            //transaction = session.beginTransaction();
            //Получаем объект из базы данных по id и типу класса
            T object = (T) getSession().get(persistentClass,id);
            if (object != null) {
                //Удаляем объект
                getSession().delete(object);
            }
            else {
                throw new DaoException("Object for deleting don't exist!");
            }
            //При отсутствии исключения коммитим
            //transaction.commit();
            //log.info("delete(id):" + object);
        } catch (HibernateException e) {
            log.error("Error delete in Dao" + e);
            //Откатываем транзакцию
            //transaction.rollback();
            throw new DaoException("Error delete in Dao.");
        }
    }

    @SuppressWarnings("unchecked")
    public T load(Serializable id) throws DaoException {
        //session = HibernateUtil.getHibernateUtil().getSession();
        //Создаем ссылку на загруженный объект
        T object = null;
        try {
            //Открываем транзакцию
            //transaction = session.beginTransaction();
            object = (T) getSession().load(persistentClass, id);
            log.info("load(id) class:" + object);
            //При отсутствии исключения коммитим
            //transaction.commit();
        } catch (HibernateException e) {
            log.error("Error load(id) " + persistentClass + " in Dao" + e);
            //Откатываем транзакцию
            //transaction.rollback();
            throw new DaoException("Error load in Dao.");
        }
        return object;
    }

    @SuppressWarnings("unchecked")
    public T get(Serializable id) throws DaoException {
        //session = HibernateUtil.getHibernateUtil().getSession();
        //Создаем ссылку на полученный объект из БД
        T object = null;
        try {
            //transaction = session.beginTransaction();
            object = (T) getSession().get(persistentClass, id);
            //transaction.commit();
            log.info("get(id) class:" + object);
        } catch (HibernateException e) {
            //Откатываем транзакцию
            //transaction.rollback();
            log.error("Error get(id) " + persistentClass + " in Dao" + e);
            throw new DaoException("Error get in Dao.");
        }
        return object;
    }

}
