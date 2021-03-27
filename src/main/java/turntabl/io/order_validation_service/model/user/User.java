package turntabl.io.order_validation_service.model.user;


import turntabl.io.order_validation_service.model.DateAudit;
import turntabl.io.order_validation_service.model.order.Order;
import turntabl.io.order_validation_service.model.portfolio.Portfolio;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity(name = "User")
@Table(
        name="Users",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique", columnNames = "email")
        }
)
public class User extends DateAudit {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")

    @Column(name = "id", updatable = false)
    private int id;

    @Column (name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String first_name;

    @Column (name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String last_name;

    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name= "funds", nullable = false)
    private double funds;

    @Column(name = "authority", nullable = false)
    private String authority = "user";

    public User () {}

    public User(String first_name, String last_name, String password, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.email = email;
    }


    public Integer getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public Date getDate_created() {
        return created_at;
    }

    public Set<Portfolio> getPortfolio() {
        return portfolio;
    }

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @OneToMany(mappedBy = "user")
    private Set<Portfolio> portfolio;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
                ", funds=" + funds +
                ", date_created=" + created_at +
                ", authority=" + authority +
                '}';
    }
}
