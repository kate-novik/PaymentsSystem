package by.it.novik.controller;

import by.it.novik.services.Service;
import by.it.novik.entities.Account;
import by.it.novik.entities.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public class CommandGetAccounts implements ActionCommand{
    @Override
    public String execute(HttpServletRequest request) {
        String page = Action.ACCOUNTS.inPage;
        //Получаем из сессии объект user
        User user= (User) request.getSession(true).getAttribute("user");
//        if (user==null) {
//            return Action.LOGIN.inPage;
//        }
        List<Account> listAccounts = Service.getService().getAccountService().getAccountsByUser(user.getIdUser());
        if (!listAccounts.isEmpty()) {
            request.setAttribute(Action.msgMessage, "List of accounts for user " + user.getNickname());
            request.setAttribute("listAccounts", listAccounts);
            request.getSession(true).setAttribute("listAccounts", listAccounts);
            request.setAttribute("type","success");
            page = Action.ACCOUNTS.okPage;
        }
        else {
            request.setAttribute(Action.msgMessage, "Accounts don't exist.");
            request.setAttribute("type","info");
            page = Action.ACCOUNTS.inPage;
        }

        return page;
    }
}
