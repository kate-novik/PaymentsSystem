package by.it.novik.controllers.rest;

import by.it.novik.entities.User;
import by.it.novik.services.IUserService;
import by.it.novik.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
    IUserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> findAll() throws ServiceException {
        return userService.getAllUsers();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public User findOne(@PathVariable Long id) throws ServiceException {
        return userService.get(id);
    }

    @Secured(value = "admin")
    @RequestMapping(method = RequestMethod.POST)
    public User create(User user){
        return user;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id) throws ServiceException {
        User user = userService.get(id);
        userService.saveOrUpdate(user);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) throws ServiceException {
        userService.delete(id);
    }
}
