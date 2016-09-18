package by.it.novik.controllers.rest;

import by.it.novik.pojos.Account;
import by.it.novik.services.AccountService;
import by.it.novik.util.ServiceException;
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
@RequestMapping(value = "/api/accounts")
public class AccountsController {

    @Autowired
    AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Account> findAll() throws ServiceException {
        return accountService.getAllAccounts();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Account findOne(@PathVariable Long id){
        Account account = new Account();
        account.setId(id);
        return account;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Account create(Account account){
        return account;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public Account update(@PathVariable Long id){
        Account account = new Account();
        account.setId(id);
        return account;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
    }
}
