package turntabl.io.order_validation_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {
    @Id
    private int id;
    private double price;
    private int quantity;
    private String order_type;
    private String date_created;
    private String order_status;
    private int portfolio_id;
    private int product_id;
    private int client_id;

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOrder_type() {
        return order_type;
    }

    public String getDate_created() {
        return date_created;
    }

    public String getOrder_status() {
        return order_status;
    }

    public int getPortfolio_id() {
        return portfolio_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getClient_id() {
        return client_id;
    }
}
