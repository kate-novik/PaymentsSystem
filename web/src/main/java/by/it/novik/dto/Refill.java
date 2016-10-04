package by.it.novik.dto;

/**
 * Created by Kate Novik.
 */
// please add DTO suffix to this class name
public class Refill {

    private Double amount;

    private Long idAccount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }
}
