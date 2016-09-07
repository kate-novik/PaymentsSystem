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
public class ClientController {

    @RequestMapping("/")
    public String home(ModelMap model) {
        model.addAttribute("message", "Page for users!");
        return "payments";
    }

    @RequestMapping("/accounts")
    public String accounts(ModelMap model) {
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
    public String payments(ModelMap model) {
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
