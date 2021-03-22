package turntabl.io.order_validation_service.validationLogic;

import org.springframework.beans.factory.annotation.Autowired;
import turntabl.io.order_validation_service.model.PortfolioCheckInterface;
import turntabl.io.order_validation_service.model.PortfolioModel;
import turntabl.io.order_validation_service.service.HttpConnection;

public class PortfolioCheck implements PortfolioCheckInterface {
    private final int portfolio_id;


    @Autowired
    HttpConnection connection;

    public PortfolioCheck(int portfolio_id) {
        this.portfolio_id = portfolio_id;
    }

    @Override
    public PortfolioModel getPortfolio() {
        String portfolioUrl = "";
        return connection.getPortfolio(portfolioUrl, portfolio_id);
    }

}
