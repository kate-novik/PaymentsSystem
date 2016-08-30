package by.it.novik.controller;

import by.it.novik.pojos.User;
import by.it.novik.services.Service;
import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommandLogin implements ActionCommand {
    private static Logger log = Logger.getLogger (CommandLogin.class);

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
                try {
                    //Поиск User по логину и паролю
                    User user = Service.getService().getSecurityService().findUser(login, password);
                    if (user == null) { //Вывод сообщение при отсутствии юзера в БД
                        request.setAttribute(Action.msgMessage, "Wrong data! Repeat, please, input or make registration.");
                        request.setAttribute("type", "danger");
                        page = Action.LOGIN.inPage;
                    } else {
                        //Создадим сессию при ее отсутствии
                        HttpSession session = request.getSession(true);
                        //Передадим в сессию объект user
                        session.setAttribute("user", user);
                        session.setAttribute("login", user.getLogin());
                        session.setAttribute("password", user.getPassword());
                        session.setAttribute("remember", remember);
                        request.setAttribute(Action.msgMessage, "Welcome, " + user.getLogin());
                        request.setAttribute("type", "success");
                        page = Action.LOGIN.okPage;
                    }
                }
                catch (ServiceException e){
                    log.error("Error in CommandLogin."+ e);
                    request.setAttribute(Action.msgMessage, "Error in CommandLogin.");
                    request.setAttribute("type", "danger");
                    page = Action.LOGIN.inPage;
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