package by.it.novik.pojos;


import by.it.novik.util.AccountState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Kate Novik.
 */
@Entity
@Table(name = "account")
@NamedQueries({
        @NamedQuery(name="getAccountsByUser", query= Account.QUERY_GET_ACCOUNTS_BY_USER),
        @NamedQuery(name="getLockedAccounts", query= Account.QUERY_GET_LOCKED_ACCOUNTS),
        @NamedQuery(name="getAllAccounts", query= Account.QUERY_GET_ALL_ACCOUNTS)
})
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    static final String QUERY_GET_ACCOUNTS_BY_USER = "FROM Account a WHERE a.user = :user ORDER BY :orderState";
    static final String QUERY_GET_ALL_ACCOUNTS = "FROM Account";
    static final String QUERY_GET_LOCKED_ACCOUNTS = "FROM Account a WHERE a.state = 'LOCKED'";

    private Long id;
    @Id
    @GeneratedValue
    @Column(name="id_account")
    @Type(type = "long")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    private AccountState state;
    @Column(name="account_state", columnDefinition = "enum('LOCKED', 'WORKING','DELETED')")
    @Enumerated(EnumType.STRING)
    public AccountState getState() {
        return state;
    }
    public void setState(AccountState state) {
        this.state = state;
    }

    private double balance;
    @Column(name="balance")
    @Type(type = "double")
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn(name="id_user")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Account() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (Double.compare(account.balance, balance) != 0) return false;
        if (id != null ? !id.equals(account.id) : account.id != null) return false;
        return state == account.state;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        temp = Double.doubleToLongBits(balance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", balance=" + balance +
                '}';
    }
}
