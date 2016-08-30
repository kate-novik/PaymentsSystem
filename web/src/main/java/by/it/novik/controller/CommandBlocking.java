package by.it.novik.controller;

import by.it.novik.services.Service;
import by.it.novik.entities.Account;
import by.it.novik.entities.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kate Novik.
 */
public class CommandBlocking implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page = Action.BLOCK.inPage;
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
        Account account = Service.getService().getAccountService().read(id);
        if (Service.getService().getAccountService().blockingAccount(account)) {
            request.setAttribute(Action.msgMessage, "Blocking of account #" + id +" was done.");
            request.setAttribute("type","success");
            page = Action.BLOCK.okPage;
        }
        else {
            request.setAttribute(Action.msgMessage, "Blocking of account #" + id + " wasn't done.");
            page = Action.BLOCK.inPage;
            request.setAttribute("type","danger");
        }
        return page;
    }
}
