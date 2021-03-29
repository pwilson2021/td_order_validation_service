package turntabl.io.order_validation_service.publish;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

public class Publisher {
    RedisTemplate<?, ?> template;
    ChannelTopic topic;

    public Publisher(RedisTemplate<?, ?> template, ChannelTopic topic) {
        this.template = template;
        this.topic = topic;
    }

    public void publish(byte[] msg ) throws JsonProcessingException {
//        LOGGER.info("Sending message to Receiver"+msg);
        template.convertAndSend(topic.getTopic(), msg);
    }
}
