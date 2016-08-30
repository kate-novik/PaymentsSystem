package by.it.novik.controller;

import by.it.novik.services.Service;
import by.it.novik.entities.Payment;
import by.it.novik.entities.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public class CommandGetPayments implements ActionCommand {
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
            List<Payment> listPayments = Service.getService().getPaymentService().getPaymentsByAccount(id_account);
            //Integer.parseInt(id_account)
            if (!listPayments.isEmpty()) {
                if (request.getAttribute("pay") == null) {
                    request.setAttribute(Action.msgMessage, "List of payments for account #" + id_account);
                    request.setAttribute("type", "success");
                }
                request.setAttribute("listPayments", listPayments);
                return page;
            }
            else {
                request.setAttribute(Action.msgMessage, "Payments don't exist.");
                request.setAttribute("type","info");
                return page;
            }

        }
        List<Payment> listPayments = Service.getService().getPaymentService().getPaymentsByUser(user.getIdUser());
        if (!listPayments.isEmpty()) {
            request.setAttribute(Action.msgMessage, "List of payments for user " + user.getNickname());
            request.setAttribute("type","success");
            request.setAttribute("listPayments", listPayments);
        }
        else {
            request.setAttribute(Action.msgMessage, "Payments don't exist.");
            request.setAttribute("type","info");
        }

        return page;
    }
}
