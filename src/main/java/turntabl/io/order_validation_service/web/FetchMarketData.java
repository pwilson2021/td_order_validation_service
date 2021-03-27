package turntabl.io.order_validation_service.web;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import turntabl.io.order_validation_service.model.ExchangeMarketDataModel;

public class FetchMarketData {
    private String exchange1 = "https://exchange.matraining.com/";
    private String exchange2 = "https://exchange2.matraining.com/";
    WebClient client = WebClient.create();

    public FetchMarketData() {
    }

    public Mono<ExchangeMarketDataModel> fetchMarketDataByTicker (
             String ticker,
             int exchangeId
    ) {
        String exchange =  (exchangeId == 1 ) ? exchange1 : exchange2;
        return client.get().uri(exchange+"/md/"+ticker).retrieve().bodyToMono(ExchangeMarketDataModel.class);
    }

    public void test() {
        ExchangeMarketDataModel exchangeMarketDataModel = fetchMarketDataByTicker("AAPL", 1).block();
    }
}
