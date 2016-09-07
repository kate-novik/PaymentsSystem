package by.it.novik.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Kate Novik.
 */
//@WebFilter(urlPatterns = {"/index.jsp"},
//        initParams = {
//                @WebInitParam(name = "pageRedirect", value = "do?command=Profile", description = "For redirection with session")})
public class FilterRedirectLogIn implements Filter {
    //Поле, содержащее название страницы для перехода
    private String pageRedirect;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        pageRedirect = filterConfig.getInitParameter("pageRedirect");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String contextPath = request.getContextPath();
        //Проверка на наличие сессиии
        HttpSession httpSession = request.getSession(false);
        if (httpSession!=null) {
            response.sendRedirect(contextPath + "/" + pageRedirect);
        }
        //Указываем метод для запуска остальных фильтров и сервлета
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
pageRedirect = null;
    }
}
