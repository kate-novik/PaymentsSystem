package by.it.novik.controllers.rest;

import by.it.novik.pojos.Payment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Kate Novik.
 */
@Controller
@RequestMapping( value = "/api" )
public class PaymentsController {

    @RequestMapping( method = RequestMethod.GET )
    @ResponseBody
    public Payment findAll(){
        Payment payment = new Payment();
        payment.setDescription("dsfdsfgsdgsfdg");
        payment.setId(123L);
        payment.setAmountPayment(43.6);
        return payment;
    }
}
