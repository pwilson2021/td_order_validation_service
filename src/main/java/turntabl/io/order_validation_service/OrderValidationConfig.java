package turntabl.io.order_validation_service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import turntabl.io.order_validation_service.listener.TradeListener;
import turntabl.io.order_validation_service.model.OrderListenerModel;
import turntabl.io.order_validation_service.publish.Publisher;
import turntabl.io.order_validation_service.publish.ReportPublisher;

@Configuration
public class OrderValidationConfig {
    @Bean
    public JedisConnectionFactory connectionFactory(){
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);

        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object > template(){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));

        return template;
    }

    @Bean
    public ChannelTopic tradeTopic() {return new ChannelTopic("ovs-trade-service");}
    @Bean
    public ChannelTopic reportTopic() {return new ChannelTopic("reporting-service");}

    @Bean
    Publisher tradePublisher(){
        return new Publisher(template(),tradeTopic());
    }

    @Bean
    ReportPublisher reportPublisher(){return new ReportPublisher(template(),reportTopic());}

//    @Bean
//    public MessageListenerAdapter messageListenerAdapter(){
////        pass the receiver class to the message listener adapter
//        return new MessageListenerAdapter(new TradeListener());
//    }

//    @Bean
//    public RedisMessageListenerContainer redisContainer(){
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory());
//        container.addMessageListener(messageListenerAdapter(), tradeTopic());
//
//        return container;
//    }
//
//    @Bean
//    OrderListenerModel orderListener(){return new OrderListenerModel();}

}
