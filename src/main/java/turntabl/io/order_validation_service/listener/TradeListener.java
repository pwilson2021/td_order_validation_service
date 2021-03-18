package turntabl.io.order_validation_service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class TradeListener implements MessageListener {

    ObjectMapper mapper = new ObjectMapper();
    @Override
    public void onMessage(Message message, byte[] pattern) {

    }
}
