package turntabl.io.order_validation_service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import turntabl.io.order_validation_service.model.OrderListenerModel;

import java.io.IOException;

public class TradeListener implements MessageListener {



    ObjectMapper mapper = new ObjectMapper();
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            OrderListenerModel msg = mapper.readValue(message.getBody(),OrderListenerModel.class);
//            OrderCheckService orderCheckService = new OrderCheckService(msg);
//            orderCheckService.sendValidatedOrder();
//            send this to the reporting service
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
