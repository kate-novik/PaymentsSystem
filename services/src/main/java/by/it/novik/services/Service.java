package by.it.novik.services;

import by.it.novik.dao.CreateDao;

/**
 * Created by Kate Novik.
 */
public class Service {
    //Объект сингелтон Service
    private static Service service;
    //Поля конкретных Service
    private UserService userService;
    private PaymentService paymentService;
    private AccountService accountService;
    private RoleService roleService;
    private SecurityService securityService;
    private static CreateDao dao;

    private Service (){}

    /**
     * Создание объекта Service c инициализацией полей (конкретными типами Service)
     * @return Объект Service
     */
    public static Service getService () {
        if (service == null) {
            synchronized (Service.class) {
                if (service == null){
                    dao = CreateDao.getDao();
                    service = new Service();
                    service.userService = new UserService(dao.getUserDao());
                    service.paymentService = new PaymentService (dao.getPaymentDao());
                    service.accountService = new AccountService(dao.getAccountDao());
                    service.roleService = new RoleService(dao.getRoleDao());
                    service.securityService = new SecurityService();
                }
            }
        }
        return service;
    }

    public UserService getUserService() {
        return userService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public SecurityService getSecurityService() {
        return securityService;
    }

    public RoleService getRoleService() {
        return roleService;
    }
}
