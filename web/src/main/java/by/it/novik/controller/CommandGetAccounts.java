package by.it.novik.controller;

import by.it.novik.pojos.Account;
import by.it.novik.pojos.User;
import by.it.novik.services.Service;
import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public class CommandGetAccounts implements ActionCommand{
    private static Logger log = Logger.getLogger (CommandGetAccounts.class);
    @Override
    public String execute(HttpServletRequest request) {
        String page = Action.ACCOUNTS.inPage;
        //Получаем из сессии объект user
        User user= (User) request.getSession(true).getAttribute("user");
//        if (user==null) {
//            return Action.LOGIN.inPage;
//        }

        try {
            List<Account> listAccounts = Service.getService().getAccountService().getAccountsByUser(user.getId());
            if (!listAccounts.isEmpty()) {
                request.setAttribute(Action.msgMessage, "List of accounts for user " + user.getLogin());
                request.setAttribute("listAccounts", listAccounts);
                request.getSession(true).setAttribute("listAccounts", listAccounts);
                request.setAttribute("type", "success");
                page = Action.ACCOUNTS.okPage;
            }
            else {
                request.setAttribute(Action.msgMessage, "Accounts don't exist.");
                request.setAttribute("type","info");
                page = Action.ACCOUNTS.inPage;
            }
        }
        catch (ServiceException e){
            log.error("Error in CommandGetAccounts." + e);
            request.setAttribute(Action.msgMessage, "Error in CommandGetAccounts.");
            request.setAttribute("type","danger");
            page = Action.ACCOUNTS.inPage;
        }

        return page;
    }
}
