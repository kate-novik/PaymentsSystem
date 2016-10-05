package by.it.novik.utils;

import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

/**
 * Created by Kate Novik.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger log = Logger.getLogger (GlobalExceptionHandler.class);

    @Autowired
    MessageSource messageSource;

    /**
     * Handler for ServiceException
     * @param e Object ServiceException
     * @return Object ModelAndView with message of error
     */
    @ExceptionHandler(ServiceException.class)
    public ModelAndView handleServiceException(ServiceException e, Locale locale){
        log.error(e);
        ModelAndView model = new ModelAndView("error");
        model.addObject("message",messageSource.getMessage("message.errorService", null, locale));
        model.addObject("type", "danger");
        return model;
    }

    /**
     * Handler for  all Exception
     * @param e Object Exception
     * @return Object ModelAndView with message of error
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception e, Locale locale){
        log.error(e);
        ModelAndView model = new ModelAndView("error");
        model.addObject("message",messageSource.getMessage("message.errorApp", null, locale));
        model.addObject("type", "danger");
        return model;
    }
}
