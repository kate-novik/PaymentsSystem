package by.it.novik.filters;



import by.it.novik.dao.CreateDao;
import by.it.novik.dao.Dao;
import by.it.novik.pojos.User;
import by.it.novik.services.Service;
import by.it.novik.services.UserService;
import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Kate Novik.
 */
//@WebFilter(urlPatterns = {"/*"},
//        initParams = {
//                @WebInitParam(name = "pageLogin", value = "index.jsp", description = "For prohibition jumps without session")})
public class FilterCheckSession implements Filter{
    //Поле, содержащее название страницы для перехода
    private String pageLogin;
    private static Logger log = Logger.getLogger (FilterCheckSession.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        pageLogin = filterConfig.getInitParameter("pageLogin");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String contextPath = request.getContextPath();

        //Проверка на наличие сессиии
        HttpSession httpSession = request.getSession(false);
        if (httpSession!=null) {
            if (httpSession.getAttribute("remember")!=null) {
                User user = (User) httpSession.getAttribute("user");
                String login = (String) httpSession.getAttribute("login");
                String password = (String) httpSession.getAttribute("password");
                //Создадим куки на логин и зашифрованный пароль
                Cookie cookieFirst = new Cookie("login", login);
                Cookie cookieSecond = new Cookie("password", password);
                //Установим срок хранения куки 30 дней
                cookieFirst.setMaxAge(30 * 24 * 60 * 60);
                cookieSecond.setMaxAge(30 * 24 * 60 * 60);
                response.addCookie(cookieFirst);
                response.addCookie(cookieSecond);
            }
        }
        else { //Восстановление сессии из куки
            String login = null;
            String password = null;
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies ) {
                if (cookie.getName().equals("login")) {
                    login = cookie.getValue();
                }
                if (cookie.getName().equals("password")) {
                    password = cookie.getValue();
                }
            }
            if (login!=null && password!=null) {

                UserService userService = Service.getService().getUserService();
                //Получаем пользователя с логином и паролем из куки
                User user = null;
                try {
                    user = userService.findByLoginAndPass( login, password);
                } catch (ServiceException e) {
                    log.error("Error in FilterCheckSession." + e);
                }

                if (user != null) {
                    //Создадим сессию
                    HttpSession session = request.getSession(true);
                    //Передадим в сессию объект user
                    session.setAttribute("user", user);
                    session.setAttribute("login", user.getLogin());
                    session.setAttribute("password", user.getPassword());
                }
            }
//            else {
//                response.sendRedirect(contextPath + "/" + pageLogin);
//            }
        }

        //Указываем метод для запуска остальных фильтров и сервлета
        filterChain.doFilter(request, response);


    }

    @Override
    public void destroy() {
        pageLogin = null;
    }
}
