package by.it.novik.controllers.pages;

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

/**
 * Created by Kate Novik.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/")
    public String adminPage (ModelMap model) {
        model.addAttribute("message", "Page for admins!");
        return "admin/dashboard";
    }

    @RequestMapping(value = "/payments", method = RequestMethod.GET)
    public String profile(ModelMap model) {
        return "admin/payments";
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
