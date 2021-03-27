package turntabl.io.order_validation_service.model.portfolio;


import com.fasterxml.jackson.annotation.JsonIgnore;
import turntabl.io.order_validation_service.model.order.Order;
import turntabl.io.order_validation_service.model.user.User;


import javax.persistence.*;
import java.util.Set;

@Entity(name = "Portfolio")
@Table(name = "Portfolios")
public class Portfolio {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private int id;
    private String name;

    @OneToMany(mappedBy = "portfolio")
    private Set <Order> orders;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Portfolio() {}

    public Portfolio(String name) {
        this.name = name;
    }

    public Portfolio(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public User getUser() {
        return user;
    }
}
