package turntabl.io.order_validation_service.publish;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import turntabl.io.order_validation_service.model.OrderModel;
import turntabl.io.order_validation_service.model.ReportModel;

public class ReportPublisher {
    RedisTemplate<?, ?> template;
    ChannelTopic topic;

    public ReportPublisher(RedisTemplate<?, ?> template, ChannelTopic topic) {
        this.template = template;
        this.topic = topic;
    }

    public void publish(ReportModel msg ) throws JsonProcessingException {
//        LOGGER.info("Sending message to Receiver"+msg);
        template.convertAndSend(topic.getTopic(), msg);
    }
}
