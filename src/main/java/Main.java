import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        AgentService agentService = new AgentService();
        DeliveryService deliveryService = new DeliveryService(orderService, agentService);

        orderService.createOrder("Order A", "560087");
        orderService.createOrder("Order B", "560088");
        orderService.createOrder("Order C", "560089");
        orderService.createOrder("Order D", "560087");

        LocalDateTime scheduledTime = LocalDateTime.of(2025, 3, 22, 10, 30); // 10:30 AM, Mar 22, 2025
        orderService.createScheduledOrder("Order E", "560087", scheduledTime, 30);

        agentService.createAgent("Agent A", List.of("560087"));
        agentService.createAgent("Agent B", List.of("560088"));
        agentService.createAgent("Agent C", List.of("560089"));
        agentService.createAgent("Agent D", Arrays.asList("560087", "560088"));

        deliveryService.executeDeliveries();
    }
}
