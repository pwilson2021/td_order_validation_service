package turntabl.io.order_validation_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import turntabl.io.order_validation_service.model.OrderListenerModel;
import turntabl.io.order_validation_service.model.ReportModel;
import turntabl.io.order_validation_service.publish.Publisher;
import turntabl.io.order_validation_service.publish.ReportPublisher;
import turntabl.io.order_validation_service.validationLogic.ClientCheck;
import turntabl.io.order_validation_service.validationLogic.OrderCheck;
import turntabl.io.order_validation_service.validationLogic.PortfolioCheck;

import java.util.Objects;

@Service
public class OrderCheckService {


    @Autowired
    private Publisher validatedOrderPublisher;

    @Autowired
    private ReportPublisher reportPublisher;

    private ReportModel reportModel;

    private final OrderCheck orderCheck;
    private final ClientCheck clientCheck;
    private final PortfolioCheck portfolioCheck;
    public OrderCheckService(OrderListenerModel orderListener) {
        orderCheck = new OrderCheck(Objects.requireNonNull(orderListener).getOrder_id());
        clientCheck = new ClientCheck(Objects.requireNonNull(orderListener).getClient_id());
        portfolioCheck = new PortfolioCheck(Objects.requireNonNull(orderCheck).getOrder().getPortfolio_id());
    }

    public boolean verifyClientFunds(){
//        this is to check funds when order type is "BUY"
        if(clientCheck.getClient() != null){
            if(clientCheck.getClientFund().isNaN()){
//                report to service the client do not have funds
//                report back to client connectivity the client do not have funds
                return false;
            }else{
                //                    report to reporting service
                //                    forward order to trade engine
                return orderCheck.validateBuyPrice(clientCheck.getClientFund());
            }
        }
        return false;
    }

    public boolean verifyPortfolio(){
//        this is to verify portfolio
        //            report to reporting service
        return portfolioCheck.getPortfolio() != null;
    }
    public boolean verifyOrder(){
        return orderCheck.getOrder() != null && orderCheck.getAndValidateProduct();
    }
    public void sendValidatedOrder() throws JsonProcessingException {
        if(orderCheck.getOrderType().equals("SELL")){
            if(verifyOrder() && verifyPortfolio()){
//                use queue here and the redis to public to reporting service
               validatedOrderPublisher.publish(orderCheck.getOrder());
                reportModel.setValidatedOrder(orderCheck.getOrder());
                reportModel.setReportTitle("validated order with orderType=SELL ");
                reportPublisher.publish(reportModel);
            }
        }else if(orderCheck.getOrderType().equals("BUY")){
            if(verifyClientFunds()){
//                user queue to send the order to trade engine and pub-sub to send the verified order id to reporting service
                validatedOrderPublisher.publish(orderCheck.getOrder());
                reportModel.setValidatedOrder(orderCheck.getOrder());
                reportModel.setReportTitle("validated order with orderType=BUY ");
                reportPublisher.publish(reportModel);
            }
        }
    }
}
