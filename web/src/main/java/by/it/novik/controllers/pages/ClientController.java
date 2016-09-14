package by.it.novik.controllers.pages;

import by.it.novik.controller.Action;
import by.it.novik.controller.CommandGetAccounts;
import by.it.novik.controller.Validation;
import by.it.novik.pojos.Account;
import by.it.novik.pojos.Payment;
import by.it.novik.pojos.User;
import by.it.novik.services.AccountService;
import by.it.novik.services.PaymentService;
import by.it.novik.services.Service;
import by.it.novik.services.UserService;
import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

/**
 * Created by Kate Novik.
 */
@Controller
public class ClientController {

    private static Logger log = Logger.getLogger (ClientController.class);

    @Autowired
    PaymentService paymentService;
    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String home(ModelMap model) {
        model.addAttribute("message", "Page for users!");
        return "accounts";
    }

    @RequestMapping("/accounts")
    public String accounts(ModelMap model, HttpServletRequest request, Principal principal) {
        String page = Action.ACCOUNTS.inPage;
        //Получаем из сессии объект user
        String login = principal.getName();
        User user;
        try {
            user = userService.findByLogin(login);
        } catch (ServiceException e) {
            log.error("Error in findByLogin" + e);
            model.addAttribute(Action.msgMessage, "Didn't find user.");
            model.addAttribute("type","danger");
            return "login";
        }

        //Сделать сортировку на фронтенде
        String orderState = request.getParameter("orderState");
        try {
            List<Account> listAccounts = accountService.getAccountsByUser(user.getId(),orderState);
            if (!listAccounts.isEmpty()) {
                model.addAttribute("message", "List of accounts for user " + user.getLogin());
                model.addAttribute("accounts", listAccounts);
                model.addAttribute("type", "success");
            }
            else {
                model.addAttribute(Action.msgMessage, "Accounts don't exist.");
                model.addAttribute("type","info");
            }
        }
        catch (ServiceException e){
            log.error("Error in CommandGetAccounts." + e);
            model.addAttribute(Action.msgMessage, "Error in CommandGetAccounts.");
            model.addAttribute("type","danger");

        }

        return "accounts";
    }

    @RequestMapping(value = "/accounts/{id}/block", method = RequestMethod.GET)
    public String block(ModelMap model, @PathVariable Integer id, HttpServletRequest request) {
        request.setAttribute("id_account", id);
        return "block";
    }

    @RequestMapping(value = "/accounts/{id}/block", method = RequestMethod.POST)
    public String block(ModelMap model, @PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("id_account", id);
        try {
            Account account = accountService.get(id);
            accountService.lockingAccount(account);
            model.addAttribute(Action.msgMessage, "Locking of account #" + id + " was done.");
            model.addAttribute("type", "success");
            return "accounts";
        }
        catch (ServiceException e){
            log.error("Error in CommandLocking account. Locking of account #" + id + " wasn't done." + e);
            model.addAttribute(Action.msgMessage, "Locking of account #" + id + " wasn't done.");
            model.addAttribute("type", "danger");
        }
        return "block";
    }

    @RequestMapping("/logout")
    public String logout(ModelMap model) {
        return "logout";
    }

    @RequestMapping(value="/accounts/{id}/pay", method = RequestMethod.GET)
    public String pay(ModelMap model, @PathVariable Integer id, HttpServletRequest request) {
        request.setAttribute("id_account", id);
        return "pay";
    }

    @RequestMapping(value="/accounts/{id}/pay", method = RequestMethod.POST)
    public String pay(ModelMap model, @PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("id_account", id);
        //Получаем из сессии объект user

        //Поле суммы в форме пополнения счета
        String destination = request.getParameter("destination");
        String description = request.getParameter("description");
        String amount = request.getParameter("amount");
        //ID счета-источника платежа получаем из параметра
        //String id = request.getParameter("id_account");
        Integer id_account = id;
        //Если не введены данные, то возвращаем ту жу страницу
        if (Validation.validDataFromForm(amount, "amount") && Validation.validDataFromForm(destination, "account")
                && Validation.validDataFromForm(description, "description")) {

            //ID счета-получателя платежа получаем из параметра
            Integer id_destination = Integer.parseInt(destination);
            //Приводим сумму платежа к типу Double
            Double pay_amount = Double.parseDouble(amount);
            try {
                paymentService.makePayment(id_account, id_destination, pay_amount, description);
                model.addAttribute(Action.msgMessage, "Payment was done.");
                model.addAttribute("type", "success");
                request.setAttribute("pay", "pay");
                return "payments";

            } catch (ServiceException e) {
                log.error("Error in CommandPay."+ e);
                model.addAttribute(Action.msgMessage, "Error in CommandPay.");
                model.addAttribute("type", "danger");
            }
        }else {
            model.addAttribute(Action.msgMessage,"Not valid data! Repeat, please, input.");
            model.addAttribute("type","danger");
        }
        return "pay";
    }

    @RequestMapping("/payments")
    public String payments(ModelMap model, HttpServletRequest request, Principal principal) throws ServiceException {
        String page = "payments";
        //Получаем из сессии объект user
        String login = principal.getName();
        User user;
        try {
            user = userService.findByLogin(login);
        } catch (ServiceException e) {
            log.error("Error in findByLogin" + e);
            model.addAttribute(Action.msgMessage, "Didn't find user.");
            model.addAttribute("type","danger");
            return "login";
        }
        //String id_account = request.getParameter("id_account");
        //Integer id_account = (Integer) request.getSession(true).getAttribute("id_account");
        String id = request.getParameter("id_account");

        if (id != null){
            Integer id_account = Integer.parseInt(id);
            try {
                List<Payment> listPayments = paymentService.getPaymentsByAccount(id_account);
                if (!listPayments.isEmpty()) {
                    if (request.getAttribute("pay") == null) {
                        model.addAttribute(Action.msgMessage, "List of payments for account #" + id_account);
                        model.addAttribute("type", "success");
                    }
                    model.addAttribute("payments", listPayments);
                    return page;
                } else {
                    model.addAttribute(Action.msgMessage, "Payments don't exist.");
                    model.addAttribute("type", "info");
                    return page;
                }

            }
            catch (ServiceException e) {
                log.error("Error in CommandGetPayments."+ e);
                model.addAttribute(Action.msgMessage, "Error in CommandGetPayments.");
                model.addAttribute("type", "danger");
                return page;
            }

        }
        try {
            List<Payment> listPayments = paymentService.getPaymentsByUser(user.getId());
            if (!listPayments.isEmpty()) {
                model.addAttribute(Action.msgMessage, "List of payments for user " + user.getLogin());
                model.addAttribute("type", "success");
                model.addAttribute("payments", listPayments);
            } else {
                model.addAttribute(Action.msgMessage, "Payments don't exist.");
                model.addAttribute("type", "info");
            }
        }
        catch(ServiceException e){
            log.error("Error in CommandGetPayments."+ e);
            model.addAttribute(Action.msgMessage, "Error in CommandGetPayments.");
            model.addAttribute("type", "danger");
            return page;
        }
        return "payments";
    }

    @RequestMapping("/profile")
    public String profile(ModelMap model) {
        return "profile";
    }

    @RequestMapping(value = "/accounts/{id}/refill", method = RequestMethod.GET)
    public String refill(ModelMap model, @PathVariable Integer id, HttpServletRequest request) {
        request.setAttribute("id_account", id);
        return "refill";
    }

    @RequestMapping(value = "/accounts/{id}/refill", method = RequestMethod.POST)
    public String refill(ModelMap model, @PathVariable Integer id, HttpServletRequest request, HttpServletResponse response ) {
        //Получаем из сессии объект user
//        User user= (User) request.getSession(true).getAttribute("user");
//        if (user==null) {
//            return Action.LOGIN.inPage;
//        }
        request.setAttribute("id_account",id);
        //Поле суммы в форме пополнения счета
        String amount = request.getParameter("amount");
       // String id_account = request.getParameter("id_account");
        String sourceAccount = request.getParameter("selectAccount");
        //ID счета, куда переводить,  получаем из параметра
        Integer id_destination = id;


        //ID счета, откуда переводить
        Integer id_source = Integer.parseInt(sourceAccount);
        if (Validation.validDataFromForm(amount, "amount")) {
            //Приводим сумму пополнения счета к типу Double
            Double refill = Double.parseDouble(amount);

            try {
                paymentService.makePayment(id_source, id_destination, refill, "Refill account");
                model.addAttribute(Action.msgMessage, "Balance was refilled.");
                model.addAttribute("type", "success");
                return "accounts";

            } catch (ServiceException e) {
                log.error("Error in CommandRefilling."+ e);
                model.addAttribute(Action.msgMessage, "Error in CommandRefilling.");
                model.addAttribute("type", "danger");
            }

        } else {
            model.addAttribute(Action.msgMessage,"Not valid data! Repeat, please, input.");
            model.addAttribute("type","danger");
        }
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
