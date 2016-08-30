package by.it.novik.controller;

import by.it.novik.services.Service;
import by.it.novik.entities.Account;
import by.it.novik.entities.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kate Novik.
 */
public class CommandCreateAccount implements ActionCommand{

    @Override
    public String execute(HttpServletRequest request) {
        String page = Action.NEW_ACCOUNT.inPage;
        //Получаем из сессии объект user
        User user= (User) request.getSession(true).getAttribute("user");
//        if (user==null) {
//            return Action.LOGIN.inPage;
//        }
        Account account = new Account();
        account.setFk_Users(user.getIdUser());
        if (Service.getService().getAccountService().create(account)) {
            request.setAttribute(Action.msgMessage, "Account # " + account.getIdAccount() +" was created.");
            request.setAttribute("type","success");
            page = Action.NEW_ACCOUNT.okPage;
        }
        else {
            request.setAttribute(Action.msgMessage, "Account wasn't created.");
            request.setAttribute("type","danger");
            page = Action.NEW_ACCOUNT.okPage;
        }
        return page;
    }
}
