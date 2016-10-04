package by.it.novik.dto;

/**
 * Created by Kate Novik.
 */
// please add DTO suffix to this class name
public class MoneyTransfer {

    private Long accountSource;
    private Long accountDestination;
    private Double amount;

    public MoneyTransfer() {
    }

    public Long getAccountSource() {
        return accountSource;
    }

    public void setAccountSource(Long accountSource) {
        this.accountSource = accountSource;
    }

    public Long getAccountDestination() {
        return accountDestination;
    }

    public void setAccountDestination(Long accountDestination) {
        this.accountDestination = accountDestination;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
