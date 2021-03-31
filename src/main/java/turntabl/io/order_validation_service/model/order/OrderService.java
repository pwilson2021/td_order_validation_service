package turntabl.io.order_validation_service.model.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import turntabl.io.order_validation_service.model.user.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrders() { return orderRepository.findAll();}

    public void addNewOrder(Order order) {
        orderRepository.save(order);
    }

    public void deleteOrder(Integer id) {
        boolean exists = orderRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("order with id " + id + " doesn't exist");
        }
        orderRepository.deleteById(id);
    }

    @Transactional
    public void updateOrder(Integer orderId, Double price, int quantity, String order_status) {
        Order order =  orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException(
                        "Order with id "+ orderId + " does not exist"
                ));

        if (price != null &&  !Objects.equals(order.getPrice() , price)) {
            order.setPrice(price);
        }

        if (quantity != 0 &&  !Objects.equals(order.getQuantity() , quantity)) {
            order.setQuantity(quantity);
        }

        if (order_status != null &&  !Objects.equals(order.getOrder_status() , order_status)) {
            System.out.println("this is the new status  "+order_status );
            order.setOrder_status(order_status);
        }


    }
    public Order findOrderById (int id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderOptional.orElse(null);
    }

}
