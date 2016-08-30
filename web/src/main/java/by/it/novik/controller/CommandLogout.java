package by.it.novik.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommandLogout implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        //Проверка на нажатие кнопки Logout
        if (request.getParameter("ButtonLogout") != null) {
            //HttpSession session = request.getSession(true);
            HttpSession session = request.getSession(false);
            if (session !=null){
            //Уничтожение сессии
            session.invalidate();}
            Cookie[] cookies = request.getCookies();
            if(cookies!=null) { //Удаляем все куки после команды LogOut
                for (int i = 0; i < cookies.length; i++) {
                    cookies[i].setMaxAge(0);
                }
            }
            return Action.LOGOUT.okPage;
        }
        return Action.LOGOUT.inPage;
    }
}
