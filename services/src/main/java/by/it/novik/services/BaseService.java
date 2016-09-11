package by.it.novik.services;

import by.it.novik.dao.IDao;
import by.it.novik.util.DaoException;
import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.*;

import java.io.Serializable;

/**
 * Created by Kate Novik.
 */
@org.springframework.stereotype.Service
public class BaseService<T> implements IService<T> {
    protected static Logger log = Logger.getLogger (BaseService.class);

    protected IDao<T> dao;

    public BaseService() {
    }

    public BaseService(IDao<T> dao) {
        this.dao = dao;
    }

    @Override
    public void saveOrUpdate(T object) throws ServiceException {
        try {
            dao.saveOrUpdate(object);
        }
        catch (DaoException d) {
            log.error("Error saveOrUpdate() in " + getClass() + d);
            throw new ServiceException("Error saveOrUpdate() in " + getClass());
        }
    }

    @Override
    public T get(Serializable id) throws ServiceException {
        T object;
        try {
            object = dao.get(id);
        }
        catch (DaoException d) {
            log.error("Error get() in " + getClass() + d);
            throw new ServiceException("Error get() in " + getClass());
        }
        return object;
    }

    @Override
    public void delete(Serializable id) throws ServiceException {
        try {
            dao.delete(id);
        }
        catch (DaoException d) {
            log.error("Error delete() in " + getClass() + d);
            throw new ServiceException("Error delete() in " + getClass());
        }
    }


}
