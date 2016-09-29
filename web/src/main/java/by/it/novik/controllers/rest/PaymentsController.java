package by.it.novik.controllers.rest;

import by.it.novik.controller.Pagination;
import by.it.novik.dto.PagingTransfer;
import by.it.novik.entities.Payment;
import by.it.novik.entities.User;
import by.it.novik.services.IPaymentService;
import by.it.novik.util.ServiceException;
import by.it.novik.valueObjects.PaymentsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
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
    public PagingTransfer findAll(
            HttpSession session,
            @RequestParam(value = "payDate", required = false) Date payDate,
            @RequestParam(value = "minAmountPayment", required = false, defaultValue = "0") double minAmountPayment,
            @RequestParam(value = "maxAmountPayment", required = false, defaultValue = "0") double maxAmountPayment,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber ,
            @RequestParam (value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam (value = "orderState", required = false, defaultValue = "ASC") String orderState
    ) throws ServiceException {
        PaymentsFilter paymentsFilter = new PaymentsFilter();
        paymentsFilter.setPayDate(payDate);
        paymentsFilter.setMinAmountPayment(minAmountPayment);
        paymentsFilter.setMaxAmountPayment(maxAmountPayment);
        Integer totalCountPayments = paymentService.getTotalCountOfPayments(paymentsFilter);
        //Integer totalCountAccounts = 10; // hard code value
//        if (totalCountAccounts == null) {
//            return null;
//        }
        Pagination.checkPage(pageNumber,pageSize,totalCountPayments);
        pageSize = Pagination.item_per_page_result;
        List<Payment> payments = paymentService.getAllPayments(orderState, pageSize, Pagination.firstItem, paymentsFilter);
        PagingTransfer pagingTransfer = new PagingTransfer();
        pagingTransfer.setPage(Pagination.pageResult);
        pagingTransfer.setItem_per_page(pageSize);
        pagingTransfer.setPayments(payments);
        pagingTransfer.setTotalCountItems(totalCountPayments);
        return pagingTransfer;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public PagingTransfer findPaymentsByAccount(
            @PathVariable Long id,
            @RequestParam(value = "payDate", required = false) Date payDate,
            @RequestParam(value = "minAmountPayment", required = false, defaultValue = "0") double minAmountPayment,
            @RequestParam(value = "maxAmountPayment", required = false, defaultValue = "0") double maxAmountPayment,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber ,
            @RequestParam (value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam (value = "orderState", required = false, defaultValue = "ASC") String orderState
    ) throws ServiceException {
        PaymentsFilter paymentsFilter = new PaymentsFilter();
        paymentsFilter.setPayDate(payDate);
        paymentsFilter.setMinAmountPayment(minAmountPayment);
        paymentsFilter.setMaxAmountPayment(maxAmountPayment);

        Integer totalCountPayments = paymentService.getTotalCountOfPayments(paymentsFilter,id);
        Pagination.checkPage(pageNumber,pageSize,totalCountPayments);
        pageSize = Pagination.item_per_page_result;
        List<Payment> payments = paymentService.getPaymentsByAccount(id, orderState, pageSize, Pagination.firstItem, paymentsFilter);
        PagingTransfer pagingTransfer = new PagingTransfer();
        pagingTransfer.setPage(Pagination.pageResult);
        pagingTransfer.setItem_per_page(pageSize);
        pagingTransfer.setPayments(payments);
        pagingTransfer.setTotalCountItems(totalCountPayments);
        return pagingTransfer;
    }

//    @RequestMapping(value="/{id}", method = RequestMethod.GET)
//    public Payment findOne(@PathVariable Long id){
//        Payment payment = new Payment();
//        payment.setId(id);
//        return payment;
//    }

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
