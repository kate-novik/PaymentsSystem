package by.it.novik.controllers.pages;

import by.it.novik.entities.Role;
import by.it.novik.entities.User;
import by.it.novik.services.IUserService;
import by.it.novik.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

/**
 * Created by Kate Novik.
 */
@Controller
public class LoginController {

    @Autowired
    IUserService userService;

    @RequestMapping("/")
    public String home(ModelMap model, Principal principal, HttpSession session, RedirectAttributes redirectAttr) throws ServiceException {
        //Getting user from session
        User user = userService.findByLogin(principal.getName());
        if (user == null) {
            return "login";
        } else {
            session.setAttribute("user", user);
        }
        //Getting role for user in session
        Role role = user.getRole();
        Map<String, ?> mapAttr = redirectAttr.getFlashAttributes();
        if (!mapAttr.isEmpty()) {
            model.addAttribute("message", mapAttr.get("message"));
            model.addAttribute("type", mapAttr.get("type"));
        }
        if (role.getRole().equals("admin")){
            return "admin/dashboard";
        }
        return "client/accounts";
    }
}
