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
import turntabl.io.order_validation_service.model.portfolio.Portfolio;
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
    PortfolioService portfolioService;
    ProductService productService;
    UserService userService;
    private FetchMarketData fetchMarketData;
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
       Boolean userExist = verifyUser(request.getOrder().getUserId());
       if(userExist){
           if (request.getOrder().getOrderType().equals("BUY")){
            ExchangeMarketDataModel marketDataExist = getMarketData(request.getOrder().getProductId());
            if(marketDataExist !=null){
                Boolean hasFunds = validateClientFunds(getClientFunds(request.getOrder().getUserId()),request.getOrder().getPrice());
                if(hasFunds){
                    boolean priceAccepted = verifyForBuyOrderType(marketDataExist,request.getOrder().getPrice());
                    boolean buyLimitAccepted = verifySellLimit(marketDataExist,request.getOrder().getQuantity());
                    if(priceAccepted && buyLimitAccepted){
                        response.setIsOrderValidated(true);
                        response.setStatus("VALID");
//                      publish validated order to trade engine and reporting service
                        tradePublisher.publish(mapper.writeValueAsBytes(request.getOrder()));
                        reportPublisher.publish(mapper.writeValueAsString("order validated"+request.getOrder()));
                    }
                }else{
                    response.setStatus("REJECTED");
                    reportPublisher.publish(mapper.writeValueAsString("order Rejected"+request.getOrder()));
//               need to add order message
                }
            }
           }else if(request.getOrder().getOrderType().equals("SELL")){
               Boolean productExist = verifyProduct(request.getOrder().getProductId());
               Boolean portfolioExist = verifyPortfolio(request.getOrder().getPortfolioId());
               if(productExist && portfolioExist){
                   boolean priceAccepted = verifyForSellOrderType(getMarketData(request.getOrder().getProductId()),request.getOrder().getPrice());
                   boolean sellLimitAccepted = verifyBuyLimit(getMarketData(request.getOrder().getProductId()),request.getOrder().getQuantity());
                   if(priceAccepted && sellLimitAccepted){
                       response.setIsOrderValidated(true);
                       response.setStatus("VALID");
//                       send to trade engine
                       tradePublisher.publish(mapper.writeValueAsBytes(request.getOrder()));
                       reportPublisher.publish(mapper.writeValueAsString("order validated"+request.getOrder()));
                   }
               }else {
                   response.setIsOrderValidated(false);
                   response.setStatus("REJECTED");
                   reportPublisher.publish(mapper.writeValueAsString("order Rejected"+request.getOrder()));
               }
           }else{
               response.setIsOrderValidated(false);
               response.setStatus("REJECTED");
               reportPublisher.publish(mapper.writeValueAsString("order Rejected"+request.getOrder()));
           }
       }else{
           response.setStatus("USER DOES NOT EXIST");
           response.setIsOrderValidated(false);
           reportPublisher.publish(mapper.writeValueAsString("order Rejected"+request.getOrder()));
       }
       reportPublisher.publish(mapper.writeValueAsString("order Rejected"+request.getOrder()));
       return response;
   }
   public Boolean validateClientFunds(Double funds, Double price){
       return funds >= price;
   }
   public Boolean verifyPortfolio(int id){
       Portfolio portfolio=portfolioService.findPortfolioById(id);
       return portfolio != null;
   }
   public Boolean verifyProduct(int id){
       Product product= productService.findProductById(id);
       return product != null;
   }
   public Boolean verifyUser(int id){
       User user = userService.findUserById(id);
       return user != null;
   }
   public Double getClientFunds(int id){
       User user = userService.findUserById(id);
       if (user != null){
           return user.getFunds();
       }
       return null;
   }
   public ExchangeMarketDataModel getMarketData(int id){
       Product product = productService.findProductById(id);
       if (product != null){
           String ticker = product.getTicker();

           return fetchMarketData.fetchMarketDataByTicker(ticker,1).block();
       }
       return null;
   }
   public Boolean verifyForBuyOrderType(ExchangeMarketDataModel data, double price){
       double upperLimit = 1.0 + data.getAsk_price();
       double lowerLimit = 1.0 - data.getAsk_price();
       return price >= lowerLimit && price <= upperLimit;
   }
   public Boolean verifyForSellOrderType(ExchangeMarketDataModel data,double price){
       double lowerLimit = 1.0 - data.getBid_price();
       double upperLimit = 1.0 + data.getBid_price();
       return price >= lowerLimit && price <=upperLimit;
   }
   public Boolean verifySellLimit(ExchangeMarketDataModel data, int quantity){
//       this is to check sell limit when order type is BUY
       return quantity <= data.getSell_limit();
   }
   public Boolean verifyBuyLimit(ExchangeMarketDataModel data, int quantity){
//       this is to check for buy limit when order type is sell
       return quantity <= data.getBuy_limit();
   }
}
