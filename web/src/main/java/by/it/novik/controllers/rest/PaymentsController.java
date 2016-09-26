package by.it.novik.controllers.rest;

import by.it.novik.entities.Payment;
import by.it.novik.services.IPaymentService;
import by.it.novik.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Kate Novik.
 */
@RestController
@RequestMapping(value = "/api/payments")
public class PaymentsController {

    @Autowired
    IPaymentService paymentService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Payment> findAll() throws ServiceException {
        return paymentService.getAllPayments();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Payment findOne(@PathVariable Long id){
        Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Payment create(Payment payment){
        return payment;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public Payment update(@PathVariable Long id){
        Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
    }
}
