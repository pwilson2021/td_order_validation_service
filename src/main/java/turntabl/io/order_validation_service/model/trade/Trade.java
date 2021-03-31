package turntabl.io.order_validation_service.model.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import turntabl.io.order_validation_service.model.DateAudit;
import turntabl.io.order_validation_service.model.order.Order;

import javax.persistence.*;

@Entity
@Table(name="Trade")
public class Trade extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private double price;
    @Column(name = "exchange_order_id", nullable = false)
    private String exchange_order_id;
    private double quantity;
    private String status;
    private int exchange_id;

    @JsonIgnore
    @ManyToOne
    private Order order;

    public Trade(int id, double price, String exchange_order_id, double quantity, String status, int exchange_id, Order order) {
        this.id = id;
        this.price = price;
        this.exchange_order_id = exchange_order_id;
        this.quantity = quantity;
        this.status = status;
        this.exchange_id = exchange_id;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getExchange_order_id() {
        return exchange_order_id;
    }

    public void setExchange_order_id(String exchange_order_id) {
        this.exchange_order_id = exchange_order_id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(int exchange_id) {
        this.exchange_id = exchange_id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
