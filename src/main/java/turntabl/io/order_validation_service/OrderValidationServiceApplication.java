package turntabl.io.order_validation_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import turntabl.io.order_validation_service.publish.Publisher;
import turntabl.io.order_validation_service.publish.ReportPublisher;
import turntabl.io.order_validation_service.service.OrderRunnable;

@SpringBootApplication
public class OrderValidationServiceApplication {
	private RedisTemplate<?,?> template;
	private RestTemplateBuilder restTemplateBuilder;
//    @Bean
//	HttpConnection connection(){
//    	return new HttpConnection(restTemplateBuilder);
//	}
	public static void main(String[] args) {
		SpringApplication.run(OrderValidationServiceApplication.class, args);
	}

//	@Bean
//	Publisher publishTrade(){return new Publisher(template,new ChannelTopic("validated-order"));}
//
//	@Bean
//	ReportPublisher reportPublisher(){return new ReportPublisher(template,new ChannelTopic("reporting-service"));}
//
//	OrderRunnable runnable =  new OrderRunnable();
//    Thread orderTrade = new Thread(runnable);
//	{
//		orderTrade.start();
//	}
}
