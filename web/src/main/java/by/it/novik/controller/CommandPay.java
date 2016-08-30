package by.it.novik.controller;

import by.it.novik.pojos.User;
import by.it.novik.services.Service;
import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kate Novik.
 */
public class CommandPay implements ActionCommand {
    private static Logger log = Logger.getLogger (CommandPay.class);
    @Override
    public String execute(HttpServletRequest request) {
        String page = Action.PAY.inPage;
        //Получаем из сессии объект user
        User user= (User) request.getSession(true).getAttribute("user");
//        if (user==null) {
//            return Action.LOGIN.inPage;
//        }
        //Поле суммы в форме пополнения счета
        String destination = request.getParameter("destination");
        String description = request.getParameter("description");
        String amount = request.getParameter("amount");
        //ID счета-источника платежа получаем из параметра
        String id = request.getParameter("id_account");
        Integer id_account = Integer.parseInt(id);
        request.setAttribute("id_account",id);
        //Если не введены данные, то возвращаем ту жу страницу
        if (amount == null || destination==null || description==null) {
            return page;
        }
        if (Validation.validDataFromForm(amount, "amount") && Validation.validDataFromForm(destination, "account")
                && Validation.validDataFromForm(description, "description")) {

            //ID счета-получателя платежа получаем из параметра
            Integer id_destination = Integer.parseInt(request.getParameter("destination"));
            //Приводим сумму платежа к типу Double
            Double pay_amount = Double.parseDouble(amount);
            try {
                Service.getService().getPaymentService().makePayment(id_account, id_destination, pay_amount, description);
                request.setAttribute(Action.msgMessage, "Payment was done.");
                request.setAttribute("type", "success");
                request.setAttribute("pay", "pay");
                page = Action.PAY.okPage;

            } catch (ServiceException e) {
                log.error("Error in CommandPay."+ e);
                request.setAttribute(Action.msgMessage, "Error in CommandPay.");
                request.setAttribute("type", "danger");
                page = Action.PAY.inPage;
            }
        }else {
            request.setAttribute(Action.msgMessage,"Not valid data! Repeat, please, input.");
            request.setAttribute("type","danger");
            page = Action.PAY.inPage;
        }
        return page;
    }
}
