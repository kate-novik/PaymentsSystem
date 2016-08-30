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
public class CommandLocking implements ActionCommand {
    private static Logger log = Logger.getLogger (CommandLocking.class);
    @Override
    public String execute(HttpServletRequest request) {
        String page = Action.LOCK.inPage;
        //Получаем из сессии объект user
        User user= (User) request.getSession(true).getAttribute("user");
        if (user==null) {
            return Action.LOGIN.inPage;
        }
        String id_account = request.getParameter("id_account");
        //ID счета получаем из параметра
        Integer id = Integer.parseInt(id_account);
        request.setAttribute("id_account",id_account);
        String reference = request.getParameter("reference");
        if (reference != null) {
            return page;
        }
        try {
            Account account = Service.getService().getAccountService().get(id);
            Service.getService().getAccountService().lockingAccount(account);
                request.setAttribute(Action.msgMessage, "Locking of account #" + id + " was done.");
                request.setAttribute("type", "success");
                page = Action.LOCK.okPage;
        }
        catch (ServiceException e){
            log.error("Error in CommandLocking account. Locking of account #" + id + " wasn't done.");
            request.setAttribute(Action.msgMessage, "Locking of account #" + id + " wasn't done.");
            page = Action.LOCK.inPage;
            request.setAttribute("type", "danger");
        }
        return page;
    }
}
