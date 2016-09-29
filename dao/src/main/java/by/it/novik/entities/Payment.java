package by.it.novik.entities;

import org.hibernate.annotations.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Kate Novik.
 */
@Entity
@Table(name = "payment")
@NamedQueries({
        @NamedQuery(name="getPaymentsByUser", query= Payment.QUERY_GET_PAYMENTS_BY_USER)
        //@NamedQuery(name="getPaymentsByAccount", query= Payment.QUERY_GET_PAYMENTS_BY_ACCOUNT)
       // @NamedQuery(name="getAllPayments", query= Payment.QUERY_GET_ALL_PAYMENTS)
})
public class Payment {
    private static final long serialVersionUID = 1L;

    static final String QUERY_GET_PAYMENTS_BY_USER = "SELECT p FROM Payment p JOIN p.accountSource a WHERE a.user= :user ORDER BY :orderState";
    //static final String QUERY_GET_PAYMENTS_BY_ACCOUNT = "FROM Payment p WHERE p.accountSource = :account ORDER BY :orderState";
   // static final String QUERY_GET_ALL_PAYMENTS = "FROM Payment ORDER BY :orderState";

    private Long id;
    @Id
    @GeneratedValue
    @Column(name="id_payment")
    @Type(type = "long")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    private String description;
    @Column(name="description", length = 120)
    @Type(type = "string")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    private Date payDate;
    @Column(name="pay_date")
    @Temporal(TemporalType.DATE)
    public Date getPayDate() {
        return payDate;
    }
    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    private double amountPayment;
    @Column(name="amount_payment")
    @Type(type = "double")
    public double getAmountPayment() {
        return amountPayment;
    }
    public void setAmountPayment(double amountPayment) {
        this.amountPayment = amountPayment;
    }

    private Account accountSource;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_account_source")
    public Account getAccountSource() {
        return accountSource;
    }
    public void setAccountSource(Account accountSource) {
        this.accountSource = accountSource;
    }

    private Account accountDestination;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_account_destination")
    public Account getAccountDestination() {
        return accountDestination;
    }
    public void setAccountDestination(Account accountDestination) {
        this.accountDestination = accountDestination;
    }


    public Payment() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (Double.compare(payment.amountPayment, amountPayment) != 0) return false;
        if (id != null ? !id.equals(payment.id) : payment.id != null) return false;
        if (description != null ? !description.equals(payment.description) : payment.description != null) return false;
        if (payDate != null ? !payDate.equals(payment.payDate) : payment.payDate != null) return false;
        if (accountSource != null ? !accountSource.equals(payment.accountSource) : payment.accountSource != null)
            return false;
        return accountDestination != null ? accountDestination.equals(payment.accountDestination) : payment.accountDestination == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (payDate != null ? payDate.hashCode() : 0);
        temp = Double.doubleToLongBits(amountPayment);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (accountSource != null ? accountSource.hashCode() : 0);
        result = 31 * result + (accountDestination != null ? accountDestination.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", payDate=" + payDate +
                ", amountPayment=" + amountPayment +
                ", accountSource=" + accountSource +
                ", accountDestination=" + accountDestination +
                '}' ;
    }
}
