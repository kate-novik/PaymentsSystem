package by.it.novik.dao;
import by.it.novik.entities.*;
import by.it.novik.util.AccountState;
import by.it.novik.util.AccountType;

import java.sql.Date;

/**
 * Created by Kate Novik.
 */
public class BuilderEntity {

    /**
     * To build object User
     * @return Object User
     */
    public static User buildUser () {
        Address address = new Address();
        address.setCity("Minsk");
        address.setStreet("Kolasa");
        address.setHome("6");
        address.setFlat("105");

        Passport passport = new Passport();
        passport.setNumber("MP1234567");
        passport.setDateOfIssue(Date.valueOf("2014-05-15"));
        passport.setIssued("Minskiy ROVD");

        User user = new User();
        user.setFirstName("Anna");
        user.setMiddleName("Antonovna");
        user.setLastName("Ivanova");
        user.setAddress(address);
        user.setEmail("ret@mail.ru");
        user.setPassport(passport);
        user.setLogin("ter");
        user.setPassword("3af8212b2bee9ac54115a6fc5d455ca8");
        user.setPhone("375447547878");

        Role role = new Role();
        role.setRole("user");

        Account account = new Account();
        account.setBalance(256);
        account.setState(AccountState.WORKING);
        account.setTitle("My account");
        account.setType(AccountType.PERSONAL);
        account.setUser(user);

        Account accountSecond = new Account();
        accountSecond.setBalance(600);
        accountSecond.setState(AccountState.WORKING);
        accountSecond.setUser(user);

        user.getAccounts().add(account);
        user.getAccounts().add(accountSecond);
        user.setRole(role);
        address.setUser(user);
        passport.setUser(user);
        return user;
    }

    public static Payment buildPayment (User user){
        Payment payment = new Payment();
        payment.setDescription("Refilling");
        payment.setPayDate(Date.valueOf("2014-03-10"));
        payment.setAmountPayment(200);
        payment.setAccountSource(user.getAccounts().iterator().next());
        payment.setAccountDestination(user.getAccounts().iterator().next());
        return payment;
    }

    public static Role buildRole () {
        Role role = new Role();
        role.setRole("user");
        return role;
    }
}
