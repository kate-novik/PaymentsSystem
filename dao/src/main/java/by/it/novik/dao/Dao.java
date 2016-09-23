package by.it.novik.dao;

import by.it.novik.util.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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


    @Override
    public void saveOrUpdate(T t) throws DaoException {
        try {
            getSession().saveOrUpdate(t);
            log.info("saveOrUpdate(t):" + t);
        }
        catch (HibernateException e) {
            log.error("Error saveOrUpdate in Dao" + e);
            throw new DaoException("Error saveOrUpdate in Dao.");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void delete(Serializable id) throws DaoException {
        try {
            //Получаем объект из базы данных по id и типу класса
            T object = (T) getSession().get(persistentClass,id);
            if (object != null) {
                //Удаляем объект
                getSession().delete(object);
                log.info("delete(id):" + object);
            }
            else {
                throw new DaoException("Object for deleting don't exist!");
            }
        } catch (HibernateException e) {
            log.error("Error delete in Dao" + e);
            throw new DaoException("Error delete in Dao.");
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public T get(Serializable id) throws DaoException {
        //Создаем ссылку на полученный объект из БД
        T object;
        try {
            object = (T) getSession().get(persistentClass, id);
            log.info("get(id) class:" + object);
        } catch (HibernateException e) {
            log.error("Error get(id) " + persistentClass + " in Dao" + e);
            throw new DaoException("Error get in Dao.");
        }
        return object;
    }

}
