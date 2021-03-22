package turntabl.io.order_validation_service.validationLogic;

import org.springframework.beans.factory.annotation.Autowired;
import turntabl.io.order_validation_service.model.OrderCheckInterface;
import turntabl.io.order_validation_service.model.OrderModel;
import turntabl.io.order_validation_service.model.ProductModel;
import turntabl.io.order_validation_service.service.HttpConnection;

import java.util.ArrayList;
import java.util.List;

public class OrderCheck implements OrderCheckInterface {
    private final int order_id;
    private OrderModel order;

//    private List<Double> exchange_price = new ArrayList<>();

    @Autowired
    HttpConnection connection;

    public OrderCheck(int order_id) {
        this.order_id = order_id;
    }


    @Override
    public OrderModel getOrder() {
        String orderUrl = "http://localhost:8080/";
        this.order = connection.getOrder(orderUrl,this.order_id);
        return this.order;
    }

    @Override
    public Double getExchangePrices(String ticker) {
//        log exchange prices to reporting service
        String url = "https://exchange.matraining.com/md/{ticker}";
        return connection.getExchange_prices(url,ticker);
    }

    @Override
    public boolean validatePrice() {
//        compare selling or buying price to the lowest and highest price in the exchange price list

        return false;
    }

    @Override
    public String getOrderType() {
        return this.order.getOrder_type();
    }
    public boolean validateBuyPrice(double clientFund){
        if (this.order.getOrder_type().equals("BUY")){
            double value_of_order = this.order.getPrice()*this.order.getQuantity();
            return value_of_order >= clientFund;
        }
        return false;
    }
    public boolean getAndValidateProduct(){
        String productUrl = "";
        ProductModel product = connection.getProduct(productUrl,this.order.getProduct_id());
        return product != null;
    }
//    public boolean validateProductQuantity(){
//        if (getAndValidateProduct()){
//
//        }
//        return false;
//    }
}
