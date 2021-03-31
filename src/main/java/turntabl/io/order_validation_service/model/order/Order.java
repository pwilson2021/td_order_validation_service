package turntabl.io.order_validation_service.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import turntabl.io.order_validation_service.model.DateAudit;
import turntabl.io.order_validation_service.model.portfolio.Portfolio;
import turntabl.io.order_validation_service.model.product.Product;
import turntabl.io.order_validation_service.model.trade.Trade;
import turntabl.io.order_validation_service.model.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Order")
@Table(name="porders")
public class Order extends DateAudit {
    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )

    @Column(name = "id", updatable = false, nullable = false)
    private int id;
    private double price;
    private int quantity;
    private String order_type;

    private String order_status;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "portfolio_id", referencedColumnName = "id")
    private Portfolio portfolio;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @OneToMany(mappedBy = "order")
    private Set<Trade> trades;

    public Order(double price, int quantity, String order_type, String order_status, User user, Portfolio portfolio, Product product) {
        this.price = price;
        this.quantity = quantity;
        this.order_type = order_type;
        this.order_status = order_status;
        this.user = user;
        this.portfolio = portfolio;
        this.product = product;
    }

    public Order() {

    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_type() {
        return order_type;
    }

    public User getUser() {
        return user;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public Product getProduct() {
        return product;
    }
}