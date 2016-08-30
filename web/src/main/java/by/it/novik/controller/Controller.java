package by.it.novik.controller;

import by.it.novik.DAO;
import by.it.novik.connection.ConnectorDB;
import by.it.novik.entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

//@WebServlet("/do")
public class Controller extends HttpServlet {
//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        //Получаем данные из файла ресурсов для соединения с базой данных
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("connection");
//        String url = resourceBundle.getString("db.url");
//        String user = resourceBundle.getString("db.user");
//        String pass = resourceBundle.getString("db.password");
//        ConnectorDB.setUrlDb(url);
//        ConnectorDB.setUserDb(user);
//        ConnectorDB.setPasswordDb(pass);
//
//    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
        ActionFactory client = new ActionFactory(); // определение команды, пришедшей из JSP
        ActionCommand command = client.defineCommand(request);
        //Проверка на наличие сессиии
//        HttpSession httpSession = request.getSession(false);
//        if (httpSession!=null) {
//            if (httpSession.getAttribute("remember")!=null) {
//                User user = (User) httpSession.getAttribute("user");
//                String login = (String) httpSession.getAttribute("login");
//                String password = (String) httpSession.getAttribute("password");
//                //Создадим куки на логин и зашифрованный пароль
//                Cookie cookieFirst = new Cookie("login", login);
//                Cookie cookieSecond = new Cookie("password", password);
//                //Установим срок хранения куки 30 дней
//                cookieFirst.setMaxAge(30 * 24 * 60 * 60);
//                cookieSecond.setMaxAge(30 * 24 * 60 * 60);
//                response.addCookie(cookieFirst);
//                response.addCookie(cookieSecond);
//            }
//        }
//        else { //Восстановление сессии из куки
//            String login = null;
//            String password = null;
//            Cookie[] cookies = request.getCookies();
//            for (Cookie cookie : cookies ) {
//                if (cookie.getName().equals("login")) {
//                    login = cookie.getValue();
//                }
//                if (cookie.getName().equals("password")) {
//                    password = cookie.getValue();
//                }
//            }
//            if (login!=null && password!=null) {
//                //Создаем объект DAO
//                DAO dao = DAO.getDAO();
//                //Получаем пользователя с логином и паролем из куки
//                List<User> users = dao.getUserDAO().getAll(String.format("where Login='%s' and Password='%s'", login, password));
//                User user = null;
//                if (users.size() > 0) {
//                    user = users.get(0);
//                }
//                if (user != null) {
//                    //Создадим сессию
//                    HttpSession session = request.getSession(true);
//                    //Передадим в сессию объект user
//                    session.setAttribute("user", user);
//                    session.setAttribute("login", user.getNickname());
//                    session.setAttribute("password", user.getPassword());
//                }
//            }
//        }
        //вызов реализованного метода execute() и передача параметров
        //классу-обработчику конкретной команды. Обработчик должен вернуть адрес view
        String viewPage = command.execute(request);

        response.setHeader("Cache-Control", "no-store");


        //метод отправляет пользователю страницу ответа
        if (viewPage != null) {
            ServletContext servletContext=getServletContext();
            RequestDispatcher dispatcher = servletContext.getRequestDispatcher(viewPage);
            // вызов страницы ответа на запрос
            dispatcher.forward(request, response);
        } else {
            // установка страницы c cообщением об ошибке
            viewPage = Action.ERROR.inPage;
            response.sendRedirect(request.getContextPath() + viewPage);
        }
    }
}