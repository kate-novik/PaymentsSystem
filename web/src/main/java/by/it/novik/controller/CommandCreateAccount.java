package by.it.novik.controller;

import by.it.novik.pojos.Account;
import by.it.novik.pojos.User;
import by.it.novik.services.Service;
import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kate Novik.
 */
public class CommandCreateAccount implements ActionCommand{
    private static Logger log = Logger.getLogger (CommandCreateAccount.class);

    @Override
    public String execute(HttpServletRequest request) {
        String page = Action.NEW_ACCOUNT.inPage;
        //Получаем из сессии объект user
        User user= (User) request.getSession(true).getAttribute("user");
//        if (user==null) {
//            return Action.LOGIN.inPage;
//        }
        Account account = new Account();
        account.setUser(user);
        try {
            Service.getService().getAccountService().saveOrUpdate(account);
                request.setAttribute(Action.msgMessage, "Account # " + account.getId() + " was created.");
                request.setAttribute("type", "success");
                page = Action.NEW_ACCOUNT.okPage;
        }
        catch (ServiceException e){
            log.error("Error in CommandCreateAccount. Account wasn't created." + e);
            request.setAttribute(Action.msgMessage, "Account wasn't created.");
            request.setAttribute("type", "danger");
            page = Action.NEW_ACCOUNT.okPage;
        }
        return page;
    }
}
