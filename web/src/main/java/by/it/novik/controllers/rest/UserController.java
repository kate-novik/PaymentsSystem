package by.it.novik.controllers.rest;

import by.it.novik.pojos.User;
import by.it.novik.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kate Novik.
 */
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> findAll(){
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(123L);
        users.add(user);
        return users;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public User findOne(@PathVariable Long id){
        User user = new User();
        user.setId(id);
        return user;
    }

    @RequestMapping(method = RequestMethod.POST)
    public User create(User user){
        return user;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public User update(@PathVariable Long id){
        User user = new User();
        user.setId(id);
        return user;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
    }
}
