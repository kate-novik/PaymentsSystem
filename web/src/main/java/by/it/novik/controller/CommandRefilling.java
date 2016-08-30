package by.it.novik.controller;

import by.it.novik.services.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kate Novik.
 */
public class CommandRefilling implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page = Action.REFILL.inPage;
        //Получаем из сессии объект user
//        User user= (User) request.getSession(true).getAttribute("user");
//        if (user==null) {
//            return Action.LOGIN.inPage;
//        }
        //Поле суммы в форме пополнения счета
        String amount = request.getParameter("amount");
        String id_account = request.getParameter("id_account");
        String sourceAccount = request.getParameter("selectAccount");
        //ID счета, куда переводить,  получаем из параметра
        Integer id_destination = Integer.parseInt(id_account);
        request.setAttribute("id_account",id_account);
        //Если не введена сумма, то возвращаем ту жу страницу
        if (amount == null || sourceAccount == null) {
            return page;
        }
        //ID счета, откуда переводить
        Integer id_source = Integer.parseInt(sourceAccount);
            if (Validation.validDataFromForm(amount, "amount")) {
                //Приводим сумму пополнения счета к типу Double
                Double refill = Double.parseDouble(amount);

                    try {
                        Service.getService().getPaymentService().makePayment(id_source, id_destination, refill, "Refill account");
                        request.setAttribute(Action.msgMessage, "Balance was refilled.");
                        request.setAttribute("type", "success");
                        page = Action.REFILL.okPage;

                    } catch (Exception e) {
                        request.setAttribute(Action.msgMessage, e.getMessage());
                        request.setAttribute("type", "danger");
                        page = Action.REFILL.inPage;
                    }

        } else {
                request.setAttribute(Action.msgMessage,"Not valid data! Repeat, please, input.");
                request.setAttribute("type","danger");
                page = Action.REFILL.inPage;
            }


        return page;
    }
}
