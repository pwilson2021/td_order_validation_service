package turntabl.io.order_validation_service.model.trade;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Integer> {
    Optional<Trade> findTradeByOrderId(String order_id);
}
