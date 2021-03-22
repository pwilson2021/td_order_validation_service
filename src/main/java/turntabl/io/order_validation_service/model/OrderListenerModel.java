package turntabl.io.order_validation_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListenerModel {
    private int order_id;
    private int client_id;

    public int getOrder_id() {
        return order_id;
    }

    public int getClient_id() {
        return client_id;
    }
}
