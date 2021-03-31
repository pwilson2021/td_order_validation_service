package turntabl.io.order_validation_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import turntabl.io.order_validation_service.model.OrderListenerModel;
import turntabl.io.order_validation_service.model.ReportModel;
import turntabl.io.order_validation_service.publish.ReportPublisher;

public class OrderRunnable implements Runnable{
    String orderUrl ="http://localhost:8080";
//    @Autowired
//    private HttpConnection connection;
    @Autowired
    ReportPublisher reportPublisher;

    ReportModel report;

    @Override
    public void run() {
//        OrderListenerModel order = connection.getOrderFromClient(orderUrl);
//        OrderCheckService checkService = new OrderCheckService(order);
//        try {
//            checkService.sendValidatedOrder();
//        } catch (JsonProcessingException e) {
//            report.setReportTitle("Could not send validated order");
//            try {
//                reportPublisher.publish(report);
//            } catch (JsonProcessingException jsonProcessingException) {
//                jsonProcessingException.printStackTrace();
//            }
//            e.printStackTrace();
//        }
    }
}
