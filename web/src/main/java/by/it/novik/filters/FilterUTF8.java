package by.it.novik.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kate Novik.
 */
@WebFilter(urlPatterns = {"/*"},
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})
public class FilterUTF8 implements Filter {
    //Поле, содержащее кодировку
    private String coding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Получаем кодировку из конфигураций
        coding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //Получаем кодировку запроса
        String codingRequest = servletRequest.getCharacterEncoding();
        if (coding != null && !coding.equals(codingRequest)) {
            //Устанавливаем кодировку из фильта, если не совпала с пришедшей
            servletRequest.setCharacterEncoding(coding);
        }
        //Получаем кодировку ответа
        String codingResponse = servletResponse.getCharacterEncoding();
        if (coding != null && !coding.equals(codingResponse)) {
            //Устанавливаем кодировку из фильта, если не совпала с ответом
            servletResponse.setCharacterEncoding(coding);
        }
        //Зделаем приведение типов для запроса и ответа
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //Указываем метод для запуска остальных фильтров и сервлета
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        coding = null;
    }
}
