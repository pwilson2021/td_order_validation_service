package turntabl.io.order_validation_service.model;

import java.util.List;

public interface OrderCheckInterface {
    OrderModel getOrder();
    Double getExchangePrices(String ticker);
    boolean validatePrice();
    String getOrderType();

}
