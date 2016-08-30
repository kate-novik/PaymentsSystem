package by.it.novik.controller;

import by.it.novik.services.Service;
import by.it.novik.entities.User;

import javax.servlet.http.HttpServletRequest;

public class CommandRegistration implements ActionCommand {
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
        String passport = request.getParameter("passport");
        String address = request.getParameter("address");
        String login = request.getParameter("login");
        //Проверка на заполненность формы данными
        if (email == null || first_name == null || middle_name == null || last_name == null ||
            phone == null || password == null || passport == null || login == null || address == null) {
            return page;
        }
        //Валидация полей
        if (Validation.validDataFromForm(password, "password") && Validation.validDataFromForm(login, "login") &&
                Validation.validDataFromForm(email, "email") && Validation.validDataFromForm(address, "address")
                && Validation.validDataFromForm(phone, "phone") && Validation.validDataFromForm(passport, "passport")
                && Validation.validDataFromForm(first_name, "first_name") && Validation.validDataFromForm(middle_name, "middle_name")
                && Validation.validDataFromForm(last_name, "last_name")) {
            User user = new User();
            user.setFirstName(first_name);
            user.setMiddleName(middle_name);
            user.setLastName(last_name);
            user.setPassport(passport);
            user.setAddress(address);
            user.setPhone(phone);
            user.setEmail(email);
            user.setNickname(login);
            //Проверка на создание юзера
            if (Service.getService().getSecurityService().createUser(user)) {
                request.setAttribute(Action.msgMessage,"User was created. Enter data for authorization.");
                request.setAttribute("type","success");
                page = Action.REGISTRATION.okPage;
            } else {
                request.setAttribute(Action.msgMessage,"User wasn't created. Enter data .");
                request.setAttribute("type","danger");
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