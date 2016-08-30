package by.it.novik.controller;

import by.it.novik.pojos.Payment;
import by.it.novik.pojos.User;
import by.it.novik.services.Service;
import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public class CommandGetPayments implements ActionCommand {
    private static Logger log = Logger.getLogger (CommandGetPayments.class);
    @Override
    public String execute(HttpServletRequest request) {
        String page = Action.PAYMENTS.inPage;
        //Получаем из сессии объект user
        User user= (User) request.getSession(true).getAttribute("user");
//        if (user==null) {
//            return Action.LOGIN.inPage;
//        }

        //String id_account = request.getParameter("id_account");
        //Integer id_account = (Integer) request.getSession(true).getAttribute("id_account");
        String id = request.getParameter("id_account");

        if (id != null){
            Integer id_account = Integer.parseInt(id);
            try {
                List<Payment> listPayments = Service.getService().getPaymentService().getPaymentsByAccount(id_account);
                if (!listPayments.isEmpty()) {
                    if (request.getAttribute("pay") == null) {
                        request.setAttribute(Action.msgMessage, "List of payments for account #" + id_account);
                        request.setAttribute("type", "success");
                    }
                    request.setAttribute("listPayments", listPayments);
                    return page;
                } else {
                    request.setAttribute(Action.msgMessage, "Payments don't exist.");
                    request.setAttribute("type", "info");
                    return page;
                }

            }
            catch (ServiceException e) {
                log.error("Error in CommandGetPayments."+ e);
                request.setAttribute(Action.msgMessage, "Error in CommandGetPayments.");
                request.setAttribute("type", "danger");
                return page;
            }

        }
        try {
            List<Payment> listPayments = Service.getService().getPaymentService().getPaymentsByUser(user.getId());
            if (!listPayments.isEmpty()) {
                request.setAttribute(Action.msgMessage, "List of payments for user " + user.getLogin());
                request.setAttribute("type", "success");
                request.setAttribute("listPayments", listPayments);
            } else {
                request.setAttribute(Action.msgMessage, "Payments don't exist.");
                request.setAttribute("type", "info");
            }
        }
        catch(ServiceException e){
            log.error("Error in CommandGetPayments."+ e);
            request.setAttribute(Action.msgMessage, "Error in CommandGetPayments.");
            request.setAttribute("type", "danger");
            return page;
        }

        return page;
    }
}
