package by.it.novik.controllers.pages;

import by.it.novik.controller.Validation;
import by.it.novik.pojos.*;
import by.it.novik.services.AccountService;
import by.it.novik.services.PaymentService;
import by.it.novik.services.RoleService;
import by.it.novik.services.UserService;
import by.it.novik.util.AccountState;
import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String home(ModelMap model, Principal principal) {
        model.addAttribute("message", "Hello" + principal.getName());
        model.addAttribute("type", "info");
        return "accounts";
    }

    @RequestMapping("/accounts")
    public String accounts(ModelMap model, HttpServletRequest request, Principal principal) {

        //Получаем из сессии объект user
        String login = principal.getName();
        User user;
        try {
            user = userService.findByLogin(login);
        } catch (ServiceException e) {
            log.error("Error in findByLogin" + e);
            model.addAttribute("message", "Didn't find user.");
            model.addAttribute("type","danger");
            return "login";
        }
        model.addAttribute("user",user);

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
                model.addAttribute("message", "Accounts don't exist.");
                model.addAttribute("type","info");
            }
        }
        catch (ServiceException e){
            log.error("Error in CommandGetAccounts." + e);
            model.addAttribute("message", "Error getting accounts.");
            model.addAttribute("type","danger");

        }
        return "accounts";
    }

    @RequestMapping(value="/createAccount", method = RequestMethod.GET)
    public String createAccount(ModelMap model, HttpServletRequest request, Principal principal) {
        //Получаем из сессии объект user
        String login = principal.getName();
        User user;
        try {
            user = userService.findByLogin(login);
        } catch (ServiceException e) {
            log.error("Error in findByLogin" + e);
            model.addAttribute("message", "Didn't find user.");
            model.addAttribute("type","danger");
            return "login";
        }
        Account account = new Account();
        account.setUser(user);
        account.setState(AccountState.WORKING);
        account.setBalance(0);
        try {
            accountService.saveOrUpdate(account);
            model.addAttribute("message", "Account # " + account.getId() + " was created.");
            model.addAttribute("type", "success");
        }
        catch (ServiceException e){
            log.error("Error in CommandCreateAccount. Account wasn't created." + e);
            model.addAttribute("message", "Account wasn't created.");
            model.addAttribute("type", "danger");
        }
        return "redirect:/accounts";
    }

    @RequestMapping(value = "/accounts/{id}/getBlock", method = RequestMethod.POST)
    public String getBlock(ModelMap model, @PathVariable Long id, HttpServletRequest request, Principal principal) {
        request.setAttribute("id_account", id);
        //Получим зарегестрированного пользователя
        String login = principal.getName();
        User user;
        try {
            user = userService.findByLogin(login);
        } catch (ServiceException e) {
            log.error("Error in findByLogin" + e);
            model.addAttribute("message", "Didn't find user.");
            model.addAttribute("type","danger");
            return "login";
        }
        //Флаг наличия аккаунта
        boolean flag = false;
        //Проверим принадлежность переданного счета данному пользователю
        try {
            flag = checkAccountOfUser(user,id);
        } catch (ServiceException e) {
            log.error("Error in getAccountsByUser" + e);
            model.addAttribute("message", "Didn't find accounts for user.");
            model.addAttribute("type","danger");
            return "redirect:/accounts";
        }

        if (!flag){
            model.addAttribute("message", "You can't block not your account.");
            model.addAttribute("type","danger");
            return "redirect:/accounts";
        }
        return "block";
    }

    @RequestMapping(value = "/accounts/{id}/block", method = RequestMethod.POST)
    public String block(ModelMap model, @PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("id_account", id);
        try {
            Account account = accountService.get(id);
            accountService.lockingAccount(account);
            model.addAttribute("message", "Locking of account #" + id + " was done.");
            model.addAttribute("type", "success");
            return "redirect:/accounts";
        }
        catch (ServiceException e){
            log.error("Error in CommandLocking account. Locking of account #" + id + " wasn't done." + e);
            model.addAttribute("message", "Locking of account #" + id + " wasn't done.");
            model.addAttribute("type", "danger");
        }
        return "block";
    }

    @RequestMapping(value = "/getLogout", method = RequestMethod.GET)
    public String getLogout(ModelMap model) {
        return "logout";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping(value="/accounts/{id}/getPay", method = RequestMethod.GET)
    public String getPay(ModelMap model, @PathVariable Long id, HttpServletRequest request, Principal principal) {
        request.setAttribute("id_account", id);
        //Получим зарегестрированного пользователя
        String login = principal.getName();
        User user;
        try {
            user = userService.findByLogin(login);
        } catch (ServiceException e) {
            log.error("Error in findByLogin" + e);
            model.addAttribute("message", "Didn't find user.");
            model.addAttribute("type","danger");
            return "login";
        }
        //Флаг наличия аккаунта
        boolean flag = false;
        //Проверим принадлежность переданного счета данному пользователю
        try {
            flag = checkAccountOfUser(user,id);
        } catch (ServiceException e) {
            log.error("Error in getAccountsByUser" + e);
            model.addAttribute("message", "Didn't find accounts for user.");
            model.addAttribute("type","danger");
            return "redirect:/accounts";
        }

        if (!flag){
            model.addAttribute("message", "You can't pay not your account.");
            model.addAttribute("type","danger");
            return "redirect:/accounts";
        }
        return "pay";
    }

    @RequestMapping(value="/accounts/{id}/pay", method = RequestMethod.POST)
    public String pay (ModelMap model, @PathVariable Integer id, HttpServletRequest request, Principal principal) {
        request.setAttribute("id_account", id);
        //Получим зарегестрированного пользователя
        String login = principal.getName();
        User user;
        try {
            user = userService.findByLogin(login);
        } catch (ServiceException e) {
            log.error("Error in findByLogin" + e);
            model.addAttribute("message", "Didn't find user.");
            model.addAttribute("type","danger");
            return "login";
        }

        //Поле суммы в форме пополнения счета
        String destination = request.getParameter("destination");
        String description = request.getParameter("description");
        String amount = request.getParameter("amount");
        //ID счета-источника платежа получаем из параметра
        //String id = request.getParameter("id_account");
        //Если не введены данные, то возвращаем ту жу страницу
        if (Validation.validDataFromForm(amount, "amount") && Validation.validDataFromForm(destination, "account")
                && Validation.validDataFromForm(description, "description")) {

            //ID счета-получателя платежа получаем из параметра
            Integer id_destination = Integer.parseInt(destination);
            //Приводим сумму платежа к типу Double
            Double pay_amount = Double.parseDouble(amount);
            try {
                paymentService.makePayment(id, id_destination, pay_amount, description);
                model.addAttribute("message", "Payment was done.");
                model.addAttribute("type", "success");
                request.setAttribute("pay", "pay");
                return "redirect:/payments";

            } catch (ServiceException e) {
                log.error("Error in CommandPay."+ e);
                model.addAttribute("message", "Error in CommandPay.");
                model.addAttribute("type", "danger");
            }
        }else {
            model.addAttribute("message","Not valid data! Repeat, please, input.");
            model.addAttribute("type","danger");
        }
        return "pay";
    }

    @RequestMapping(value = "/accounts/{id}/payments", method = RequestMethod.GET)
    public String payments(ModelMap model, @PathVariable Long id, HttpServletRequest request, Principal principal){
        request.setAttribute("id_account", id);
        //Получаем из сессии объект user
        String login = principal.getName();
        User user;
        try {
            user = userService.findByLogin(login);
        } catch (ServiceException e) {
            log.error("Error in findByLogin" + e);
            model.addAttribute("message", "Didn't find user.");
            model.addAttribute("type","danger");
            return "login";
        }

        //Флаг наличия аккаунта
        boolean flag = false;
        //Проверим принадлежность переданного счета данному пользователю
        try {
            flag = checkAccountOfUser(user,id);
        } catch (ServiceException e) {
            log.error("Error in getAccountsByUser" + e);
            model.addAttribute("message", "Didn't find accounts for user.");
            model.addAttribute("type","danger");
            return "redirect:/accounts";
        }

        if (!flag){
            model.addAttribute("message", "You can't pay not your account.");
            model.addAttribute("type","danger");
            return "redirect:/accounts";
        }
        //String id_account = request.getParameter("id_account");
        //Integer id_account = (Integer) request.getSession(true).getAttribute("id_account");
        String id_source = request.getParameter("id_account");

        if (id_source != null){
            Integer id_account = Integer.parseInt(id_source);
            try {
                List<Payment> listPayments = paymentService.getPaymentsByAccount(id_account);
                if (!listPayments.isEmpty()) {
                    if (request.getAttribute("pay") == null) {
                        model.addAttribute("message", "List of payments for account #" + id_account);
                        model.addAttribute("type", "success");
                    }
                    model.addAttribute("payments", listPayments);
                    return "payments";
                } else {
                    model.addAttribute("message", "Payments don't exist.");
                    model.addAttribute("type", "info");
                    return "redirect:/accounts";
                }

            }
            catch (ServiceException e) {
                log.error("Error in CommandGetPayments."+ e);
                model.addAttribute("message", "Error displaying payments.");
                model.addAttribute("type", "danger");
                return "redirect:/accounts";
            }

        }
        return "payments";
    }


    @RequestMapping(value = "/payments", method = RequestMethod.GET)
    public String payments(ModelMap model, Principal principal) {
        //Получаем из сессии объект user
        String login = principal.getName();
        User user;
        try {
            user = userService.findByLogin(login);
        } catch (ServiceException e) {
            log.error("Error in findByLogin" + e);
            model.addAttribute("message", "Didn't find user.");
            model.addAttribute("type", "danger");
            return "login";
        }
        try {
            List<Payment> listPayments = paymentService.getPaymentsByUser(user.getId());
            if (!listPayments.isEmpty()) {
                model.addAttribute("message", "List of payments for user " + user.getLogin());
                model.addAttribute("type", "success");
                model.addAttribute("payments", listPayments);
            } else {
                model.addAttribute("message", "Payments don't exist.");
                model.addAttribute("type", "info");
                return "redirect:/accounts";
            }
        }
        catch(ServiceException e){
            log.error("Error in CommandGetPayments."+ e);
            model.addAttribute("message", "Error displaying payments.");
            model.addAttribute("type", "danger");
            return "redirect:/accounts";
        }
        return "payments";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(ModelMap model, Principal principal) {
        //Получаем из сессии объект user
        String login = principal.getName();
        User user;
        try {
            user = userService.findByLogin(login);
        } catch (ServiceException e) {
            log.error("Error in findByLogin" + e);
            model.addAttribute("message", "Didn't find user.");
            model.addAttribute("type", "danger");
            return "login";
        }
        model.addAttribute("user",user);
        return "profile";
    }

    @RequestMapping(value = "/accounts/{id}/getRefill", method = RequestMethod.POST)
    public String getRefill(ModelMap model, @PathVariable Long id, HttpServletRequest request, Principal principal)  {
        request.setAttribute("id_account", id);
        //Получаем из сессии объект user
        String login = principal.getName();
        User user;
        try {
            user = userService.findByLogin(login);
        } catch (ServiceException e) {
            log.error("Error in findByLogin" + e);
            model.addAttribute("message", "Didn't find user.");
            model.addAttribute("type","danger");
            return "login";
        }

        //Флаг наличия аккаунта
        boolean flag = false;
        //Проверим принадлежность переданного счета данному пользователю
        try {
            flag = checkAccountOfUser(user,id);
        } catch (ServiceException e) {
            log.error("Error in getAccountsByUser" + e);
            model.addAttribute("message", "Error finding accounts for user.");
            model.addAttribute("type","danger");
            return "redirect:/accounts";
        }

        if (!flag){
            model.addAttribute("message", "You can't refill not your account.");
            model.addAttribute("type","danger");
            return "redirect:/accounts";
        }
        //Получим счета пользователя для перевода
        List <Account> accounts = null;
        try {
            accounts = accountService.getAccountsByUser(user.getId(),"ASC");
        } catch (ServiceException e) {
            log.error("Error in getAccountsByUser" + e);
            model.addAttribute("message", "Error finding accounts for user.");
            model.addAttribute("type","danger");
            return "redirect:/accounts";
        }
        if (accounts!=null && accounts.size()>1){
        model.addAttribute("accounts", accounts);}
        else {
            model.addAttribute("message", "You don't have account for refilling.");
            model.addAttribute("type","danger");
            return "redirect:/accounts";
        }

        return "refill";
    }

    @RequestMapping(value = "/accounts/{id}/refill", method = RequestMethod.POST)
    public String refill(ModelMap model, @PathVariable Integer id, HttpServletRequest request, HttpServletResponse response ) {

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
                model.addAttribute("message", "Balance was refilled.");
                model.addAttribute("type", "success");
                return "redirect:/accounts";

            } catch (ServiceException e) {
                log.error("Error in CommandRefilling."+ e);
                model.addAttribute("message", "Error refilling.");
                model.addAttribute("type", "danger");
                return "redirect:/accounts";
            }

        } else {
            model.addAttribute("message","Not valid data! Repeat, please, input.");
            model.addAttribute("type","danger");
        }
        return "refill";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistration(ModelMap model, Authentication auth) {
        if (auth != null){
            model.addAttribute("message","You are already registered.");
            model.addAttribute("type","danger");
            return "redirect:/profile";
        }
        model.addAttribute("registrationForm", new User());
        return "reg";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("registrationForm") @Valid User user,
                               BindingResult result, ModelMap model) {
        //Проверка на заполненность формы данными
        if (result.hasErrors()) {
            model.addAttribute("message", "Not valid data! Repeat, please, input.");
            model.addAttribute("type", "danger");
            return "reg";
        } else {
            try {
                userService.create(user, "user");
                model.addAttribute("message", "User was created. Enter data for authorization.");
                model.addAttribute("type", "success");
            } catch (ServiceException e) {
                log.error("Error in CommandRegistration. User wasn't create." + e);
                model.addAttribute("message", "User wasn't create.\n" + e.getMessage());
                model.addAttribute("type", "danger");
                return "reg";
            }
        }
        return "redirect:/accounts";
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

    private boolean checkAccountOfUser (User user, Long id_account) throws ServiceException {
        //Проверим принадлежность переданного счета данному пользователю
        List <Account> accounts = accountService.getAccountsByUser(user.getId(),"ASC");
        //Флаг наличия аккаунта
        boolean flag = false;
        for (Account account : accounts){
            if (account.getId().equals(id_account)){
                flag = true;
                break;
            }
        }
        return flag;
    }
}
