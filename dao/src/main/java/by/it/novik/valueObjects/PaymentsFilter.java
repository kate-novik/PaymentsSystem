package by.it.novik.valueObjects;

import java.util.Date;

/**
 * Created by Kate Novik.
 */
//Class for sending data of payment's filter
public class PaymentsFilter {

    private Date payDate;
    private double minAmountPayment;
    private double maxAmountPayment;

    public PaymentsFilter() {
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public double getMinAmountPayment() {
        return minAmountPayment;
    }

    public void setMinAmountPayment(double minAmountPayment) {
        this.minAmountPayment = minAmountPayment;
    }

    public double getMaxAmountPayment() {
        return maxAmountPayment;
    }

    public void setMaxAmountPayment(double maxAmountPayment) {
        this.maxAmountPayment = maxAmountPayment;
    }


}
