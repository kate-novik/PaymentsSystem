package by.it.novik.entities;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by Kate Novik.
 */
@Entity
@Table(name = "address")
public class Address implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long id;
    @Id
    @GenericGenerator(  name="gen",
                        strategy="foreign",
                        parameters = @Parameter(name="property", value="user"))
    @GeneratedValue(generator="gen")
    @Column(name="id_user")
    @Type(type = "long")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s\\-]{2,20}$")
    private String city;
    @Column(name="city", length = 15)
    @Type(type = "string")
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @NotEmpty
    @Pattern(regexp = "^[1-9a-zA-Zа-яА-ЯёЁ\\s\\-]{2,20}$")
    private String street;
    @Column(name="street", length = 20)
    @Type(type = "string")
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    @NotEmpty
    @Pattern(regexp = "^[01-9]{1,6}[a-zA-Zа-яА-ЯёЁ]?$")
    private String home;
    @Column(name="home", length = 7)
    @Type(type = "string")
    public String getHome() {
        return home;
    }
    public void setHome(String home) {
        this.home = home;
    }

    @Pattern(regexp = "^[01-9]{1,6}[a-zA-Zа-яА-ЯёЁ]?$")
    private String flat;
    @Column(name="flat", length = 7)
    @Type(type = "string")
    public String getFlat() {
        return flat;
    }
    public void setFlat(String flat) {
        this.flat = flat;
    }

    private User user;
    @OneToOne
    @PrimaryKeyJoinColumn
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Address() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (id != null ? !id.equals(address.id) : address.id != null) return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (home != null ? !home.equals(address.home) : address.home != null) return false;
        return flat != null ? flat.equals(address.flat) : address.flat == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (home != null ? home.hashCode() : 0);
        result = 31 * result + (flat != null ? flat.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", home='" + home + '\'' +
                ", flat=" + flat + "}";
    }
}
