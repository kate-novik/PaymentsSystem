package by.it.novik.controllers.pages;

import by.it.novik.controller.Action;
import by.it.novik.controller.CommandGetAccounts;
import by.it.novik.pojos.Account;
import by.it.novik.pojos.Payment;
import by.it.novik.pojos.User;
import by.it.novik.services.PaymentService;
import by.it.novik.services.Service;
import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Kate Novik.
 */
@Controller
public class ClientController {

    private static Logger log = Logger.getLogger (ClientController.class);

    @Autowired
    PaymentService paymentService;

    @RequestMapping("/")
    public String home(ModelMap model) {
        model.addAttribute("message", "Page for users!");
        return "accounts";
    }

    @RequestMapping("/accounts")
    public String accounts(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        String page = Action.ACCOUNTS.inPage;
        //Получаем из сессии объект user
        User user= (User) request.getSession(true).getAttribute("user");
//        if (user==null) {
//            return Action.LOGIN.inPage;
//        }
        //Сделать сортировку на фронтенде
        String orderState = request.getParameter("orderState");
        try {
            List<Account> listAccounts = Service.getService().getAccountService().getAccountsByUser(user.getId(),orderState);
            if (!listAccounts.isEmpty()) {
                model.addAttribute("message", "List of accounts for user " + user.getLogin());
                model.addAttribute("listAccounts", listAccounts);
                model.addAttribute("type", "success");
            }
            else {
                request.setAttribute(Action.msgMessage, "Accounts don't exist.");
                request.setAttribute("type","info");
            }
        }
        catch (ServiceException e){
            log.error("Error in CommandGetAccounts." + e);
            request.setAttribute(Action.msgMessage, "Error in CommandGetAccounts.");
            request.setAttribute("type","danger");

        }

        return "accounts";
    }

    @RequestMapping("/block")
    public String block(ModelMap model) {
        return "block";
    }

    @RequestMapping("/logout")
    public String logout(ModelMap model) {
        return "logout";
    }

    @RequestMapping("/pay")
    public String pay(ModelMap model) {
        return "pay";
    }

    @RequestMapping("/payments")
    public String payments(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ServiceException {
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

//        return page;
        return "payments";
    }

    @RequestMapping("/profile")
    public String profile(ModelMap model) {
        return "profile";
    }

    @RequestMapping("/refill")
    public String refill(ModelMap model) {
        return "refill";
    }

    @RequestMapping("/registration")
    public String registration(ModelMap model) {
        return "reg";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login (ModelMap model,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        if (error != null) {
            model.addAttribute("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addAttribute("msg", "You've been logged out successfully.");
        }

        return "login";
    }

}
