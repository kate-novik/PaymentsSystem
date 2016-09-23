package by.it.novik.controllers.rest;

import by.it.novik.dto.Refill;
import by.it.novik.pojos.Account;
import by.it.novik.services.AccountService;
import by.it.novik.services.IAccountService;
import by.it.novik.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kate Novik.
 */
@RestController
@RequestMapping(value = "/api/accounts")
public class AccountsController {

    @Autowired
    IAccountService accountService;

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
    public Account create(Account account) throws ServiceException {
        accountService.saveOrUpdate(account);
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

    @RequestMapping(value="/{id}/refill", method = RequestMethod.POST)
    public Account refill(@PathVariable Long id, Refill refill) throws ServiceException {
        Account account = accountService.get(id);
        account.setBalance(account.getBalance() + refill.getAmount());
        accountService.saveOrUpdate(account);
        return account;
    }

    @RequestMapping(value="/{id}/refill", method = RequestMethod.GET)
    public Account refill(@PathVariable Long id,
                          @RequestParam Double amount) throws ServiceException {
        Account account = accountService.get(id);
        account.setBalance(account.getBalance() + amount);
        accountService.saveOrUpdate(account);
        return account;
    }

    @RequestMapping(value="/{id}/lock", method = RequestMethod.GET)
    public void lock(@PathVariable Long id) throws ServiceException {
        accountService.lockingAccount(id);
    }


}
