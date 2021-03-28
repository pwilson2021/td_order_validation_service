package turntabl.io.order_validation_service.model.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import turntabl.io.order_validation_service.model.order.Order;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository) {this.portfolioRepository = portfolioRepository;}

    public List<Portfolio> getPortfolio() {
        return portfolioRepository.findAll();
    }

    public void addNewPortfolio(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }

    public void deletePortfolio(int portfolioId) {
        boolean exists = portfolioRepository.existsById(portfolioId);
        if (!exists) {
            throw new IllegalStateException("Portfolio with id " + portfolioId + " doesn't exists");
        }
        portfolioRepository.existsById(portfolioId);
    }

    @Transactional
    public void updatePortfolio(String name, int portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalStateException(
                        "Portfolio with id " + portfolioId + " doesn't exists"
                ));
        if (name != null) {
            portfolio.setName(name);
        }
    }

    public Portfolio findPortfolioById(int portfolio_id) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolio_id);
        return portfolio.get();
    }

    public void fetchStock (int portfolio_id) {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolio_id);
        Map<String, Integer> stocks = new HashMap<>();

          //  .collect(groupingBy(p -> p.age, mapping((Person p) -> p.name, toList())));

        Set<Order> orders = portfolio.get().getOrders();
        Map<String, List<Order>> groupByProduct  =
                orders.stream().collect(Collectors.groupingBy(order -> order.getProduct().getTicker()));

        System.out.println(groupByProduct);
    }
}

