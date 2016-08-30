package by.it.novik.pojos;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kate Novik.
 */
@Entity
@Table(name = "passport")
public class Passport implements Serializable {
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

    private String number;
    @Column(name="number", length = 15)
    @Type(type = "string")
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    private Date dateOfIssue;
    @Column(name="date_of_issue")
    @Temporal(TemporalType.DATE)
    public Date getDateOfIssue() {
        return dateOfIssue;
    }
    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    private String issued;
    @Column(name="issued", length = 50)
    @Type(type = "string")
    public String getIssued() {
        return issued;
    }
    public void setIssued(String issued) {
        this.issued = issued;
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

    public Passport() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Passport passport = (Passport) o;

        if (id != null ? !id.equals(passport.id) : passport.id != null) return false;
        if (number != null ? !number.equals(passport.number) : passport.number != null) return false;
        if (dateOfIssue != null ? !dateOfIssue.equals(passport.dateOfIssue) : passport.dateOfIssue != null)
            return false;
        return issued != null ? issued.equals(passport.issued) : passport.issued == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (dateOfIssue != null ? dateOfIssue.hashCode() : 0);
        result = 31 * result + (issued != null ? issued.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", dateOfIssue=" + dateOfIssue +
                ", issued='" + issued + '\'' +
                '}';
    }
}
