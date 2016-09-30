package by.it.novik.controllers.pages;

import by.it.novik.entities.*;
import by.it.novik.services.*;
import by.it.novik.util.AccountState;
import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Kate Novik.
 */
@Controller
public class ClientController {

    private static Logger log = Logger.getLogger (ClientController.class);

    @Autowired
    IPaymentService paymentService;
    @Autowired
    IAccountService accountService;
    @Autowired
    IUserService userService;
    @Autowired
    IRoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MessageSource messageSource;

    @RequestMapping("/")
    public String home(ModelMap model, Principal principal, HttpSession session, RedirectAttributes redirectAttr) throws ServiceException {
        //Getting user from session
        User user = getUserFromSession(principal, model);
        if (user == null) {
            return "login";
        }
        else {
            session.setAttribute("user", user);
        }
        //Getting role for user in session
        Role role = user.getRole();
        Map <String, ?> mapAttr = redirectAttr.getFlashAttributes();
        if (!mapAttr.isEmpty()) {
            model.addAttribute("message", mapAttr.get("message"));
            model.addAttribute("type", mapAttr.get("type"));
        }
        if (role.getRole().equals("admin")){
            return "admin";
        }
        return "accounts";
    }

    @RequestMapping(value="/createAccount", method = RequestMethod.GET)
    public String createAccount(ModelMap model, Principal principal, RedirectAttributes redirectAttr, Locale locale) throws ServiceException {
        //Getting user from session
        User user = getUserFromSession(principal,model);
        if (user == null) {
            return "login";
        }
        Account account = new Account();
        account.setUser(user);
        account.setState(AccountState.WORKING);
        account.setBalance(0);

//        try {
            accountService.saveOrUpdate(account);
            String name =  messageSource.getMessage("name.account", null, locale);
            redirectAttr.addFlashAttribute("message", messageSource.getMessage("message.isCreated", new Object[] {name}, locale));
        redirectAttr.addFlashAttribute("type","success");
//        }
//        catch (ServiceException e){
//            log.error("Error in CommandCreateAccount. Account wasn't created." + e);
//            model.addAttribute("message", "Account wasn't created.");
//            model.addAttribute("type", "danger");
//        }
        return "redirect:/";
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

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(ModelMap model, Principal principal, RedirectAttributes redirectAttr) throws ServiceException {
        //Getting user from session
        User user = getUserFromSession(principal,model);
        if (user == null) {
            return "login";
        }
        Map <String, ?> mapAttr = redirectAttr.getFlashAttributes();
        if (!mapAttr.isEmpty()) {
            model.addAttribute("message", mapAttr.get("message"));
            model.addAttribute("type", mapAttr.get("type"));
        }
        return "profile";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistration(ModelMap model, Authentication auth, RedirectAttributes redirectAttr, Locale locale) {
        if (auth != null){
            redirectAttr.addFlashAttribute("message",messageSource.getMessage("message.isReg", null, locale));
            redirectAttr.addFlashAttribute("type","danger");
            return "redirect:/profile";
        }
        model.addAttribute("registrationForm", new User());
        return "reg";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("registrationForm") @Valid User user,
                               BindingResult result, ModelMap model, RedirectAttributes redirectAttr, Locale locale) {
        //Checking form for errors
        if (result.hasErrors()) {
            model.addAttribute("message", messageSource.getMessage("message.validData", null, locale));
            model.addAttribute("type", "danger");
            return "reg";
        } else {
            try {
                userService.create(user, "user");
                redirectAttr.addFlashAttribute("message", messageSource.getMessage("message.validReg", null, locale));
                redirectAttr.addFlashAttribute("type", "success");
            } catch (ServiceException e) {
                log.error("Error in CommandRegistration. User wasn't create." + e);
                model.addAttribute("message", messageSource.getMessage(e.getMessage(), null, locale));
                model.addAttribute("type", "danger");
                return "reg";
            }
        }
        return "redirect:/";
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

//    /**
//     * Checking whether user has this account
//     * @param user Object User
//     * @param id_account Account ID
//     * @return true - user owns this account
//     */
//    private boolean checkAccountOfUser (User user, Long id_account) {
//        List <Account> accounts;
//        try {
//            //Getting list of accounts for this user
//            accounts = accountService.getAccountsByUser(user.getId(),"ASC");
//        } catch (ServiceException e) {
//            log.error("Error in getAccountsByUser" + e);
//            return false;
//        }
//        //Flag of the existence of the account
//        boolean flag = false;
//        if (accounts.size() != 0) {
//            for (Account account : accounts) {
//                if (account.getId().equals(id_account)) {
//                    flag = true;
//                    break;
//                }
//            }
//        }
//        return flag;
//    }

    /**
     * Getting object User from session
     * @param principal Object Principal
     * @param model Object ModelMap
     * @return Object User
     */
    private User getUserFromSession (Principal principal, ModelMap model) throws ServiceException {
        //Getting login
        String login = principal.getName();
        User user = null;
//        try {
            //Searching object User with login
            user = userService.findByLogin(login);
//        } catch (ServiceException e) {
//            log.error("Error in findByLogin" + e);
//            model.addAttribute("message", "Didn't find user.");
//            model.addAttribute("type","danger");
//        }
        if (user != null) {
            model.addAttribute("user", user);
        }
        return user;
    }

    /**
     * Handler for ServiceException
     * @param e Object ServiceException
     * @return Object ModelAndView with message of error
     */
    @ExceptionHandler(ServiceException.class)
    public ModelAndView handleServiceException(ServiceException e){
        log.error(e);
        ModelAndView model = new ModelAndView("error");
        model.addObject("message",e.getMessage());
        return model;
    }

    /**
     * Handler for  all Exception
     * @param e Object Exception
     * @return Object ModelAndView with message of error
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception e){
        log.error(e);
        ModelAndView model = new ModelAndView("error");
        model.addObject("message","Error in application.");
        return model;
    }

}
