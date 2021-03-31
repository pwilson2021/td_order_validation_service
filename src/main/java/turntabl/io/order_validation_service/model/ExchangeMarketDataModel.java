package turntabl.io.order_validation_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeMarketDataModel {
    private Double last_traded_price;
    private Double bid_price;
    private Integer sell_limit;
    private Integer max_price_shift;
    private String ticker;
    private Double ask_price;
    private Integer buy_limit;

    public ExchangeMarketDataModel(
            @JsonProperty("LAST_TRADED_PRICE") Double last_traded_price,
            @JsonProperty("BID_PRICE") Double bid_price,
            @JsonProperty("SELL_LIMIT") Integer sell_limit,
            @JsonProperty("MAX_PRICE_SHIFT") Integer max_price_shift,
            @JsonProperty("TICKER") String ticker,
            @JsonProperty("ASK_PRICE")Double ask_price,
            @JsonProperty("BUY_LIMIT") Integer buy_limit) {
        this.last_traded_price = last_traded_price;
        this.bid_price = bid_price;
        this.sell_limit = sell_limit;
        this.max_price_shift = max_price_shift;
        this.ticker = ticker;
        this.ask_price = ask_price;
        this.buy_limit = buy_limit;
    }

    public Double getLast_traded_price() {
        return last_traded_price;
    }

    public void setLast_traded_price(Double last_traded_price) {
        this.last_traded_price = last_traded_price;
    }

    public Double getBid_price() {
        return bid_price;
    }

    public void setBid_price(Double bid_price) {
        this.bid_price = bid_price;
    }

    public Integer getSell_limit() {
        return sell_limit;
    }

    public void setSell_limit(Integer sell_limit) {
        this.sell_limit = sell_limit;
    }

    public Integer getMax_price_shift() {
        return max_price_shift;
    }

    public void setMax_price_shift(Integer max_price_shift) {
        this.max_price_shift = max_price_shift;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Double getAsk_price() {
        return ask_price;
    }

    public void setAsk_price(Double ask_price) {
        this.ask_price = ask_price;
    }

    public Integer getBuy_limit() {
        return buy_limit;
    }

    public void setBuy_limit(Integer buy_limit) {
        this.buy_limit = buy_limit;
    }
}
