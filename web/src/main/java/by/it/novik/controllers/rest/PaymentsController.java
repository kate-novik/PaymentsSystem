package by.it.novik.controllers.rest;

import by.it.novik.dto.MoneyTransferDTO;
import by.it.novik.utils.Pagination;
import by.it.novik.dto.PagingTransferDTO;
import by.it.novik.entities.Payment;
import by.it.novik.services.IPaymentService;
import by.it.novik.util.ServiceException;
import by.it.novik.valueObjects.PaymentsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
    public PagingTransferDTO findAll(
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
        PagingTransferDTO pagingTransfer = new PagingTransferDTO();
        pagingTransfer.setPage(pageNumber);
        pagingTransfer.setItemPerPage(pageSize);
        long totalCountPayments = (Long) paymentService.getTotalCountOfPayments(paymentsFilter);
        pagingTransfer.setTotalCountItems(totalCountPayments);
        pagingTransfer = Pagination.calculatePage(pagingTransfer);
        List<Payment> payments = paymentService.getAllPayments(orderState, pagingTransfer.getItemPerPage(),
                pagingTransfer.getFirstItem(), paymentsFilter);
        pagingTransfer.setPayments(payments);
        return pagingTransfer;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody MoneyTransferDTO mt) throws ServiceException {
        paymentService.makePayment(mt.getAccountSource(), mt.getAccountDestination(), mt.getAmount(), mt.getTitle());
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
