package by.it.novik.controllers.rest;

import by.it.novik.controller.Pagination;
import by.it.novik.valueObjects.AccountsFilter;
import by.it.novik.dto.MoneyTransfer;
import by.it.novik.dto.Refill;
import by.it.novik.entities.Account;
import by.it.novik.entities.User;
import by.it.novik.services.IAccountService;
import by.it.novik.services.IUserService;
import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Kate Novik.
 */
@RestController
@RequestMapping(value = "/api/accounts")
public class AccountsController {


    private static Logger log = Logger.getLogger (AccountsController.class);

    @Autowired
    IAccountService accountService;
    @Autowired
    IUserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Account> findAll(HttpSession session,
//                                 @RequestBody AccountsFilter accountsFilter,
                                 @RequestParam (value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber ,
                                 @RequestParam (value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                 @RequestParam (value = "orderState", required = false, defaultValue = "ASC") String orderState
    ) throws ServiceException {
        //Getting user from session
        User user = (User)session.getAttribute("user");
//        Integer totalCountAccounts = accountService.getTotalCountOfPayments(accountsFilter);
        Integer totalCountAccounts = 10; // hard code value
//        if (totalCountAccounts == null) {
//            return null;
//        }
        Pagination.checkPage(pageNumber,pageSize,totalCountAccounts);
        pageNumber = Pagination.pageResult;
        pageSize = Pagination.item_per_page_result;
        return accountService.getAccountsByUser(user.getId(),orderState, pageSize, Pagination.firstItem);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Account findOne(@PathVariable Long id){
        Account account = new Account();
        account.setId(id);
        return account;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Account create(HttpSession session) throws ServiceException {
        User user = (User)session.getAttribute("user");
        return accountService.create(user);
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

    @RequestMapping(value="/refill", method = RequestMethod.POST)
    public Account refill(@RequestBody Refill refill) throws ServiceException {
        return accountService.refill(refill.getIdAccount(), refill.getAmount());
    }

    @RequestMapping(value="/transfer", method = RequestMethod.POST)
    public void transfer(@RequestBody MoneyTransfer mt) throws ServiceException {
        accountService.moneyTransfer(mt.getAccountSource(), mt.getAccountDestination(), mt.getAmount());
    }

    @RequestMapping(value="/{id}/lock", method = RequestMethod.GET)
    public void lock(@PathVariable Long id) throws ServiceException {
        accountService.lockingAccount(id);
    }

    @RequestMapping(value="/{id}/unlock", method = RequestMethod.GET)
    public void unlock(@PathVariable Long id) throws ServiceException {
        accountService.unlockingAccount(id);
    }

//    /**
//     * Checking whether user has this account
//     * @param user Object User
//     * @param id_account Account ID
//     * @return true - user owns this account
//     */
//    private boolean checkAccountOfUser (User user, Long id_account) {
//        List <Account> accounts;
//        try {
//            //Getting list of accounts for this user
//            accounts = accountService.getAccountsByUser(user.getId(),"ASC");
//        } catch (ServiceException e) {
//            log.error("Error in getAccountsByUser" + e);
//            return false;
//        }
//        //Flag of the existence of the account
//        boolean flag = false;
//        if (accounts.size() != 0) {
//            for (Account account : accounts) {
//                if (account.getId().equals(id_account)) {
//                    flag = true;
//                    break;
//                }
//            }
//        }
//        return flag;
//    }


}
