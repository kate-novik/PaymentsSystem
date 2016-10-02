package by.it.novik.valueObjects;

import by.it.novik.util.AccountState;

/**
 * Created by Kate Novik.
 */
public class AccountsFilter {

    private AccountState state;
    private double MinBalance;
    private double MaxBalance;

    public AccountsFilter() {
    }

    public AccountState getState() {
        return state;
    }

    public void setState(AccountState state) {
        this.state = state;
    }

    public double getMinBalance() {
        return MinBalance;
    }

    public void setMinBalance(double minBalance) {
        MinBalance = minBalance;
    }

    public double getMaxBalance() {
        return MaxBalance;
    }

    public void setMaxBalance(double maxBalance) {
        MaxBalance = maxBalance;
    }
}
