package by.it.novik.pojos;

import org.hibernate.annotations.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kate Novik.
 */
@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name="findByLogin", query= User.QUERY_FIND_BY_LOGIN),
        @NamedQuery(name="findByLoginAndPass", query= User.QUERY_FIND_BY_LOGIN_AND_PASS),
        @NamedQuery(name="getAllUsers", query= User.QUERY_GET_ALL_USERS)
})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    static final String QUERY_FIND_BY_LOGIN = "FROM User u WHERE u.login = :login";
    static final String QUERY_FIND_BY_LOGIN_AND_PASS = "FROM User u WHERE u.login = :login AND u.password = :password";
    static final String QUERY_GET_ALL_USERS = "FROM User";

    private Long id;
    @Id
    @GeneratedValue
    @Column(name="id_user")
    @Type(type = "long")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    private String firstName;
    @Column(name="first_name", length = 15)
    @Type(type = "string")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name="middle_name", length = 15)
    @Type(type = "string")
    private String middleName;
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    private String lastName;
    @Column(name="last_name", length = 20)
    @Type(type = "string")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private Passport passport;
    @OneToOne(mappedBy = "user", cascade=CascadeType.ALL)
    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    private Address address;
    @OneToOne(mappedBy = "user", cascade=CascadeType.ALL)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    private String phone;
    @Column(name="phone", length=12)
    @Type(type = "string")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String email;
    @Column(name="email", length=20)
    @Type(type = "string")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String login;
    @Column(name="login", length=10, unique = true)
    @Type(type = "string")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    private String password;
    @Column(name="password", length=15)
    @Type(type = "string")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String salt;
    @Column(name="salt")
    @Type(type = "string")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    private Role role;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_role")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    private Set<Account> accounts = new HashSet<Account>();
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    //@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @Fetch(FetchMode.SELECT)
    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public User() {
        super();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (middleName != null ? !middleName.equals(user.middleName) : user.middleName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        //if (passport != null ? !passport.equals(user.passport) : user.passport != null) return false;
        //if (address != null ? !address.equals(user.address) : user.address != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (salt != null ? !salt.equals(user.salt) : user.salt != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        return accounts != null ? accounts.equals(user.accounts) : user.accounts == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (passport != null ? passport.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
       // result = 31 * result + (role != null ? role.hashCode() : 0);
        //result = 31 * result + (accounts != null ? accounts.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ",  phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
