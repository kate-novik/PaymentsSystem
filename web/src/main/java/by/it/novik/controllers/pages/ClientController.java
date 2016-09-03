package by.it.novik.controllers.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Kate Novik.
 */
@Controller
public class ClientController {

    @RequestMapping("/")
    public String home(ModelMap model) {
        model.addAttribute("message", "Spring 3 MVC - Hello World");
        return "index";
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
}
