package turntabl.io.order_validation_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import turntabl.io.order_validation_service.model.*;

import java.util.Collections;
import java.util.List;

public class HttpConnection {
    private final RestTemplate restTemplate;
    public HttpConnection(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Value("${base.url}")
    private String baseUrl;

    public ClientUserModel getClient(String url, int id ){
        ResponseEntity<ClientUserModel> response = this.restTemplate.getForEntity(baseUrl+url,ClientUserModel.class,id);
        if (response.getStatusCode() == HttpStatus.OK){
            return response.getBody();
        }
        else{
            return null;
        }
    }

    public OrderModel getOrder(String url, int id){
        ResponseEntity<OrderModel> response = this.restTemplate.getForEntity(baseUrl+url, OrderModel.class,id);
        if (response.getStatusCode()==HttpStatus.OK){
            return response.getBody();
        }else{
            return null;
        }
    }
    public PortfolioModel getPortfolio(String url, int id){
        ResponseEntity<PortfolioModel> response = restTemplate.getForEntity(baseUrl+url,PortfolioModel.class, id);
        if (response.getStatusCode() == HttpStatus.OK){
            return response.getBody();
        }else {
            return null;
        }
    }
    public ProductModel getProduct(String url, int id){
        ResponseEntity<ProductModel> response = restTemplate.getForEntity(baseUrl+url,ProductModel.class,id);
        if (response.getStatusCode() == HttpStatus.OK){
            return response.getBody();

        }else{
            return null;
        }

    }

    public Double getExchange_prices(String url, String ticker){
        ResponseEntity<Double> response = this.restTemplate.getForEntity(url,Double.class,ticker);
        if (response.getStatusCode() == HttpStatus.OK){
            return response.getBody();

        }else{
            return null;
        }
    }
//    get order from client connectivity
    public OrderListenerModel getOrderFromClient(String url){
        ResponseEntity<OrderListenerModel> response = this.restTemplate.getForEntity(url,OrderListenerModel.class);
        if (response.getStatusCode() == HttpStatus.OK){
            return response.getBody();

        }else{
            return null;
        }
    }
}
