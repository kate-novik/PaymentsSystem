package by.it.novik.valueObjects;

import by.it.novik.util.AccountState;

/**
 * Created by Kate Novik.
 */
public class AccountsFilter {

    private AccountState state;
    private double minBalance;
    private double maxBalance;

    public AccountsFilter() {
    }

    public AccountState getState() {
        return state;
    }

    public void setState(AccountState state) {
        this.state = state;
    }

    public double getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(double minBalance) {
        this.minBalance = minBalance;
    }

    public double getMaxBalance() {
        return maxBalance;
    }

    public void setMaxBalance(double maxBalance) {
        this.maxBalance = maxBalance;
    }
}
