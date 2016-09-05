package by.it.novik.controllers.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Kate Novik.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/admin")
    public String adminPage (ModelMap model) {
        model.addAttribute("message", "Page for admins!");
        return "admin";
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
