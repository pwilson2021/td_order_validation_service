package turntabl.io.order_validation_service.model.product;


import turntabl.io.order_validation_service.model.order.Order;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(
        name="Product",
        uniqueConstraints = {
                @UniqueConstraint(name = "ticker", columnNames = "ticker")
        })
public class Product {
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    private Integer id;

    @Column(
            name="ticker",
            nullable = false
    )
    private String ticker;

    @OneToMany(mappedBy = "product")
    private Set<Order> orders;

    public Product( String ticker) {
        this.ticker = ticker;
    }

    public Product() { }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker =  ticker;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", ticker='" + ticker + '\'' +
                ", orders=" + orders +
                '}';
    }
}
