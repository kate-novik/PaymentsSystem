package by.it.novik.dao;

/**
 * Created by Kate Novik.
 */
public class CreateDao {
    //Объект сингелтон DAO
    private static CreateDao dao;
    //Поля конкретных DAO
    private UserDao userDao;
    private RoleDao roleDao;
    private AccountDao accountDao;
    private PaymentDao paymentDao;

    private CreateDao (){}

    /**
     * Создание объекта DAO c инициализацией полей (конкретными типами DAO)
     * @return Объект DAO
     */
    public static CreateDao getDao () {
        if (dao == null) {
            synchronized (CreateDao.class) {
                if (dao == null){
                    dao = new CreateDao();
                    dao.userDao = new UserDao();
                    dao.roleDao = new RoleDao();
                    dao.accountDao = new AccountDao();
                    dao.paymentDao = new PaymentDao ();
                }
            }
        }
        return dao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public PaymentDao getPaymentDao() {
        return paymentDao;
    }
}
