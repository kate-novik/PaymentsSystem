package by.it.novik.controllers.pages;

import by.it.novik.entities.Role;
import by.it.novik.entities.User;
import by.it.novik.services.IUserService;
import by.it.novik.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Locale;

/**
 * Created by Kate Novik.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    IUserService userService;
    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String adminPage (ModelMap model, HttpSession session, Principal principal, Locale locale) throws ServiceException {
        //Getting user from session
        User user = userService.findByLogin(principal.getName());
        //Getting role for user in session
        String role = user.getRole().getRole();
        if (user == null) {
            model.addAttribute("message", messageSource.getMessage("message.access", null, locale));
            model.addAttribute("type", "danger");
            return "login";
        } else if (!role.equals("admin")){
            model.addAttribute("message", messageSource.getMessage("message.errorAccess", null, locale));
            model.addAttribute("type", "danger");
            return "login";
        } else {
            session.setAttribute("user", user);
        }
        return "admin/dashboard";
    }

    @RequestMapping(value = "/payments", method = RequestMethod.GET)
    public String profile(ModelMap model) {
        return "admin/payments";
    }


    //for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(ModelAndView model) {
        //check if user is log in
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            model.addObject("login", userDetail.getUsername());
        }
        return "403";

    }

}
