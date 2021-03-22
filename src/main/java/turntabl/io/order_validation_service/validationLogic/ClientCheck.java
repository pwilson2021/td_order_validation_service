package turntabl.io.order_validation_service.validationLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import turntabl.io.order_validation_service.model.ClientCheckInterface;
import turntabl.io.order_validation_service.model.ClientUserModel;
import turntabl.io.order_validation_service.service.HttpConnection;

import java.net.http.HttpHeaders;

public class ClientCheck implements ClientCheckInterface {
    private final int client_id;
    private ClientUserModel client;

    @Autowired
    HttpConnection connection;


    public ClientCheck(int client_id) {
        this.client_id = client_id;
    }

    @Override
    public Double getClientFund() {
        return client.getFunds();
    }

    @Override
    public ClientUserModel getClient() {
//        log this result in the reporting service
        String url = "";
        this.client = connection.getClient(url,client_id);
        return this.client;
    }
}
