package by.it.novik.util;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Created by Kate Novik.
 */
public class HibernateUtil {

    //Объект HibernateUtil для получения сессии
    private static HibernateUtil util = null;

    //Объект логгер
    private static Logger log = Logger.getLogger(HibernateUtil.class);

    //Поле SessionFactory, содержащее скомпилированную конфигурацию и мэппинги
    private SessionFactory sessionFactory = null;

    //Поле ThreadLocal для хранения изолированных от других потоков объектов
    private final ThreadLocal sessions = new ThreadLocal();

    //Инициализация объекта sessionFactory в конструкторе
    private HibernateUtil() {
        try {
            Configuration configuration = new Configuration().configure();
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        } catch (Throwable e) {
            log.error("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Получение hibernate сессии
     * @return hibernate сессия
     */
    public Session getSession () {
        Session session = (Session) sessions.get();
        if (session == null) {
            session = sessionFactory.openSession();
            //Получение сессии из объекта ThreadLocal
            sessions.set(session);
        }
        return session;
    }

    /**
     * Получение потокобезопасного сингелтона HibernateUtil
     * @return объект HibernateUtil
     */
    public static HibernateUtil getHibernateUtil(){
        //Предпроверка инициализации поля HibernateUtil
        if (util != null){
            return util;
        }
        //Синхронизация по классу
        synchronized (HibernateUtil.class) {
            if (util == null) {
                util = new HibernateUtil();
            }
        }
        return util;
    }
}
