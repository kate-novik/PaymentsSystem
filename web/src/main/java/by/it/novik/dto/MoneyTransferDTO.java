package by.it.novik.dto;

/**
 * Created by Kate Novik.
 */
public class MoneyTransferDTO {

    private Long accountSource;
    private Long accountDestination;
    private Double amount;
    private String title;

    public MoneyTransferDTO() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
