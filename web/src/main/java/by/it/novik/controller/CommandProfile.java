package by.it.novik.controller;

import by.it.novik.entities.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kate Novik.
 */
public class CommandProfile implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        //Получаем из сессии объект user
        User user= (User) request.getSession(true).getAttribute("user");
        if (user==null) {
            return Action.LOGIN.inPage;
        }
        request.setAttribute("login", user.getNickname());
        request.setAttribute("password", user.getPassword());
        return Action.PROFILE.inPage;
    }
}
