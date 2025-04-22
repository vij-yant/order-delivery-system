
import java.time.LocalDateTime;
import java.util.*;

public class OrderService {

    Map<String,Order> orders = new HashMap<>();
    Map<String, Queue<Order>> pinCodeToOrders = new HashMap<>();

    public Order createOrder(String name, String pinCode) {
        Order order = new Order(name,pinCode);
        saveOrder(order);
        return order;
    }

    private void saveOrder(Order order) {
        orders.put(order.getOrderId(), order);
        pinCodeToOrders.computeIfAbsent(order.getPinCode(), k -> new LinkedList<>()).add(order);
    }

    public Order createScheduledOrder(String name, String pinCode, LocalDateTime createdAt, int duration) {
        Order order = new Order(name,pinCode,createdAt,duration);
        saveOrder(order);
        return order;
    }

    public Order getNextOrderForPincode(String pincode) {
        Queue<Order> queue = pinCodeToOrders.get(pincode);
        return (queue != null) ? queue.poll() : null;
    }
    public void assignOrderToAgent(Order order,Agent agent) {
        order.setAssignedAgent(agent);
        order.setStatus(OrderStatus.PICKED_UP);
        agent.getAssignedOrders().add(order);
    }

    public Map<String, Order> getAllOrders() {
        return orders;
    }
}
