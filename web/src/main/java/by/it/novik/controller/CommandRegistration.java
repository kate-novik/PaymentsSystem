package by.it.novik.controller;

import by.it.novik.pojos.Address;
import by.it.novik.pojos.Passport;
import by.it.novik.pojos.Role;
import by.it.novik.pojos.User;
import by.it.novik.services.Service;
import by.it.novik.util.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandRegistration implements ActionCommand {
    private static Logger log = Logger.getLogger (CommandRegistration.class);
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public String execute(HttpServletRequest request) {
        //Стартовая страница регистрации
        String page = Action.REGISTRATION.inPage;
        //Получаем данные из запроса
        String email = request.getParameter("email");
        String first_name = request.getParameter("first_name");
        String middle_name = request.getParameter("middle_name");
        String last_name = request.getParameter("last_name");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String flat = request.getParameter("flat");
        String home = request.getParameter("home");
        String numberOfPassport = request.getParameter("numberOfPassport");
        String issued = request.getParameter("issued");
        String date = request.getParameter("date");
        String login = request.getParameter("login");
        //Проверка на заполненность формы данными
        if (email == null || first_name == null || middle_name == null || last_name == null ||
            phone == null || password == null || login == null || city == null || street == null ||
                flat == null || home == null || numberOfPassport == null || issued == null ||
                date == null) {
            request.setAttribute(Action.msgMessage, "Complete all fields.");
            request.setAttribute("type", "danger");
            return page;
        }
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfPassport = null;
        try {
            dateOfPassport = formatDate.parse(date);
        } catch (ParseException e) {
            log.error("Error parsing Date in CommandRegistration." + e);
            request.setAttribute(Action.msgMessage, "Error parsing date of passport in CommandRegistration.");
            request.setAttribute("type", "danger");
            page = Action.REGISTRATION.inPage;
        }
        //Валидация полей
        if (Validation.validDataFromForm(password, "password") && Validation.validDataFromForm(login, "login") &&
                Validation.validDataFromForm(email, "email") && Validation.validDataFromForm(city, "city")
                && Validation.validDataFromForm(street, "street") && Validation.validDataFromForm(flat, "flat")
                && Validation.validDataFromForm(home, "home") && Validation.validDataFromForm(numberOfPassport, "numberOfPassport")
                && Validation.validDataFromForm(issued, "issued") && Validation.validDataFromForm(phone, "phone")
                && Validation.validDataFromForm(first_name, "first_name") && Validation.validDataFromForm(middle_name, "middle_name")
                && Validation.validDataFromForm(last_name, "last_name")) {
            User user = new User();
            user.setFirstName(first_name);
            user.setMiddleName(middle_name);
            user.setLastName(last_name);
            user.setPhone(phone);
            user.setEmail(email);
            user.setLogin(login);
            user.setPassword(password);

            Address address = new Address();
            address.setCity(city);
            address.setStreet(street);
            address.setHome(home);
            address.setFlat(flat);
            address.setUser(user);

            Passport passport = new Passport();
            passport.setNumber(numberOfPassport);
            passport.setDateOfIssue(dateOfPassport);
            passport.setIssued(issued);
            passport.setUser(user);

            user.setAddress(address);
            user.setPassport(passport);

            String hashPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPassword);
            Role role = null;
            try {
                role = Service.getService().getRoleService().getRoleByName("user");
            } catch (ServiceException e) {
                log.error("Error in getting role in CommandRegistration." + e);
                request.setAttribute(Action.msgMessage, "Error in getting role in CommandRegistration." + e.getMessage());
                request.setAttribute("type", "danger");
                page = Action.REGISTRATION.inPage;
            }
            user.setRole(role);

            try {
                Service.getService().getUserService().saveOrUpdate(user);
                    request.setAttribute(Action.msgMessage, "User was created. Enter data for authorization.");
                    request.setAttribute("type", "success");
                    page = Action.REGISTRATION.okPage;

            }
            catch (ServiceException e){
                log.error("Error in CommandRegistration. User wasn't create."+ e);
                request.setAttribute(Action.msgMessage, "User wasn't created. Enter data ." + e.getMessage());
                request.setAttribute("type", "danger");
                page = Action.REGISTRATION.inPage;
            }
        }
        else {
            request.setAttribute(Action.msgMessage,"Not valid data! Repeat, please, input.");
            request.setAttribute("type","danger");
            page = Action.REGISTRATION.inPage;
        }
        return page;
    }
}