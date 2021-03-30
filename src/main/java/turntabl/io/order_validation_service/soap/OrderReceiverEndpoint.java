package turntabl.io.order_validation_service.soap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.turntabl.get_client_order.GetOrderRequest;
import io.turntabl.get_client_order.GetOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import turntabl.io.order_validation_service.model.ExchangeMarketDataModel;
import turntabl.io.order_validation_service.model.order.Order;
import turntabl.io.order_validation_service.model.order.OrderService;
import turntabl.io.order_validation_service.model.portfolio.PortfolioService;
import turntabl.io.order_validation_service.model.product.Product;
import turntabl.io.order_validation_service.model.product.ProductService;
import turntabl.io.order_validation_service.model.user.User;
import turntabl.io.order_validation_service.model.user.UserService;
import turntabl.io.order_validation_service.publish.Publisher;
import turntabl.io.order_validation_service.publish.ReportPublisher;
import turntabl.io.order_validation_service.web.FetchMarketData;

@Endpoint
public class OrderReceiverEndpoint {

    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    private final FetchMarketData fetchMarketData = new FetchMarketData();
    @Autowired
    private Publisher tradePublisher;
    @Autowired
    private ReportPublisher reportPublisher;
    ObjectMapper mapper = new ObjectMapper();

   @PayloadRoot(namespace="http://turntabl.io/get-client-order", localPart = "getOrderRequest")
   @ResponsePayload
   public GetOrderResponse getOrder(@RequestPayload GetOrderRequest request) throws JsonProcessingException {
       GetOrderResponse response = new GetOrderResponse();
       response.setIsOrderValidated(false);
       response.setStatus("REJECTED");
       response.setOrderId(request.getOrderId());
       response.setMessage("Order is not valid from beginning");
       System.out.println(request.getOrderId());
       System.out.println(request.getClientId());
       Order order = getOrder(request.getOrderId());
//       tradePublisher.publish(mapper.writeValueAsBytes(request.getOrderId()));


       Boolean userExist = verifyUser(request.getClientId());
       if(userExist){
           ExchangeMarketDataModel marketData = getMarketData(order.getProduct().getId());
           if(order.getOrder_type().equals("BUY")){
               System.out.println("order type is BUY");
               if(marketData != null){
                   Boolean hasFunds = validateClientFunds(getClientFunds(request.getClientId()),getOrder(request.getOrderId()).getPrice());
                   if(hasFunds){
                       Boolean priceAccepted = verifyForBuyOrderType(getMarketData(order.getProduct().getId()),order.getPrice());
                       Boolean buyLimitedAccepted = verifySellLimit(getMarketData(order.getProduct().getId()), order.getQuantity());
                       if(priceAccepted && buyLimitedAccepted){
                           response.setStatus("ACCEPTED");
                           response.setMessage("Order is validated and accepted");
                           response.setOrderId(request.getOrderId());
                           response.setIsOrderValidated(true);

                           System.out.println(request.getOrderId());
                           tradePublisher.publish(mapper.writeValueAsString(request.getOrderId()));
                           reportPublisher.publish(mapper.writeValueAsString("Order is Accepted:  "+request.getOrderId()));
                       }else {
                           response.setIsOrderValidated(false);
                           response.setMessage("Order Price and Quantity not accepted");
                           response.setOrderId(request.getOrderId());
                           response.setStatus("REJECTED");
                           System.out.println("Order Price and Quantity not accepted");
                           reportPublisher.publish(mapper.writeValueAsString("Order is rejected:  "+request.getOrderId()+ " : "+response.getMessage()));
                       }
                   }else{
                       response.setIsOrderValidated(false);
                       response.setMessage("Client has insufficient funds");
                       response.setOrderId(request.getOrderId());
                       response.setStatus("REJECTED");
                       System.out.println("Client has insufficient funds");
                       reportPublisher.publish(mapper.writeValueAsString("Order is rejected:  "+request.getOrderId()+ " : "+response.getMessage()));
                   }
               }else {
                   response.setIsOrderValidated(false);
                   response.setMessage("Product not on the market");
                   response.setOrderId(request.getOrderId());
                   response.setStatus("REJECTED");
                   System.out.println("Product not found on market data");
                   reportPublisher.publish(mapper.writeValueAsString("Order is rejected:  "+request.getOrderId()+ " : "+response.getMessage()));
               }
           }else if(order.getOrder_type().equals("SELL")){
               System.out.println("order type is Sell");
              if(order.getProduct() != null && order.getPortfolio() !=null){
                  Boolean priceAccepted = verifyForBuyOrderType(getMarketData(order.getProduct().getId()),order.getPrice());
                  Boolean sellLimitedAccepted = verifyBuyLimit(getMarketData(order.getProduct().getId()), order.getQuantity());

                  if(priceAccepted && sellLimitedAccepted){
                      response.setStatus("Accepted");
                      response.setIsOrderValidated(true);
                      response.setMessage("Order is validated");

                      System.out.println(request.getOrderId());
                      //                      send to reporting service
                      tradePublisher.publish(mapper.writeValueAsString(request.getOrderId()));
                      reportPublisher.publish(mapper.writeValueAsString("Order is Accepted:  "+request.getOrderId()));
                  }else{
                      response.setStatus("Rejected");
                      response.setIsOrderValidated(false);
                      response.setMessage("Order price or quantity is not valid");
                      System.out.println("Order price or quantity is not valid");
                      reportPublisher.publish(mapper.writeValueAsString("Order is rejected:  "+request.getOrderId()+ " : "+response.getMessage()));
                  }
                  response.setOrderId(request.getOrderId());
              }else {
                  response.setIsOrderValidated(false);
                  response.setMessage("Product or portfolio does not exist");
                  response.setOrderId(request.getOrderId());
                  response.setStatus("REJECTED");
                  reportPublisher.publish(mapper.writeValueAsString("Order is rejected:  "+request.getOrderId()+ " : "+response.getMessage()));
              }
           }else{
               response.setIsOrderValidated(false);
               response.setMessage("Order type not valid");
               response.setOrderId(request.getOrderId());
               response.setStatus("REJECTED");
               System.out.println("Order type not valid");
               reportPublisher.publish(mapper.writeValueAsString("Order is rejected:  "+request.getOrderId()+ " : "+response.getMessage()));
           }
       }
//       reportPublisher.publish(mapper.writeValueAsString("Order is rejected:  "+request.getOrderId()+ " : "+response.getMessage()));
       return response;
   }

   public Boolean verifyUser(int id){
       User user = userService.findUserById(id);
       return user != null;
   }

   public Double getClientFunds(int id){
       User user = userService.findUserById(id);
       if(user  != null){
           return user.getFunds();
       }
       return null;
   }

   public ExchangeMarketDataModel getMarketData(int id){
       Product product = productService.findProductById(id);
       if(product !=null){
           String ticker = product.getTicker();
           System.out.println(ticker);
           ExchangeMarketDataModel data =fetchMarketData.fetchMarketDataByTicker(ticker,1).block();
           assert data != null;
           System.out.println(data.toString());
           return data;
       }
       return null;
   }

   public Boolean verifyForBuyOrderType(ExchangeMarketDataModel data, double price){
       double upperLimit = 1.0 + data.getAsk_price();
       double lowerLimit = 1.0 - data.getAsk_price();
       return price >= lowerLimit && price <= upperLimit;

   }

   public Boolean verifyForSellOrderType(ExchangeMarketDataModel data,double price){
       double upperLimit = 1.0 + data.getBid_price();
       double lowerLimit = 1.0 - data.getBid_price();

       double reasonableIncrement = (1.0 + data.getBid_price())*1.10;
       return (price >= lowerLimit && price <= upperLimit) || price <= reasonableIncrement;
   }
   public Boolean verifySellLimit(ExchangeMarketDataModel data, int quantity){
//       this is to check sell limit when order type is BUY
       return quantity <= data.getSell_limit();
   }

   public Boolean verifyBuyLimit(ExchangeMarketDataModel data, int quantity){
//       this is check for buy limit when the order type is sell
       return quantity <= data.getBuy_limit();
   }
   public Order getOrder(int id){
       return orderService.findOrderById(id);
   }
    public Boolean validateClientFunds(Double funds, Double price){
        return funds >= price;
    }
}
