package by.it.novik.controller;

import by.it.novik.services.Service;
import by.it.novik.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommandLogin implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = Action.LOGIN.inPage;
        if (request.getParameter("Password") == null) {
            return page;
        } else {

            String password = request.getParameter("Password");   //пароль
            String login = request.getParameter("Login");   //логин
            String remember = request.getParameter("remember"); //запомнить меня

            if (Validation.validDataFromForm(password, "password") && Validation.validDataFromForm(login, "login")) {
                //Поиск User по логину и паролю
                User user = Service.getService().getSecurityService().findUser(login,password);
                if (user == null) { //Вывод сообщение при отсутствии юзера в БД
                    request.setAttribute(Action.msgMessage, "Wrong data! Repeat, please, input or make registration.");
                    request.setAttribute("type","danger");
                    page = Action.LOGIN.inPage;
                } else {
                    //Создадим сессию при ее отсутствии
                    HttpSession session = request.getSession(true);
                    //Передадим в сессию объект user
                    session.setAttribute("user", user);
                    session.setAttribute("login",user.getNickname());
                    session.setAttribute("password",user.getPassword());
                    session.setAttribute("remember",remember);
                    request.setAttribute(Action.msgMessage, "Welcome, " + user.getNickname());
                    request.setAttribute("type","success");
                    page = Action.LOGIN.okPage;
                }
            } else {
                request.setAttribute(Action.msgMessage, "Not valid data! Repeat, please, input.");
                request.setAttribute("type","danger");
                page = Action.LOGIN.inPage;
            }
            return page;
        }
    }
}