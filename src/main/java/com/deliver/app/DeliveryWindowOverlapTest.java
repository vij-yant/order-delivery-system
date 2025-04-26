package com.deliver.app;

import com.deliver.app.model.Agent;
import com.deliver.app.model.Order;
import com.deliver.app.observer.ConsoleLogger;
import com.deliver.app.observer.OrderEventListener;
import com.deliver.app.service.AgentService;
import com.deliver.app.service.DeliveryService;
import com.deliver.app.service.OrderService;
import com.deliver.app.strategy.AssignmentStrategy;
import com.deliver.app.strategy.DeliveryWindowStrategy;

import java.time.LocalDateTime;
import java.util.Set;

public class DeliveryWindowOverlapTest {
    public static void main(String[] args) {
        // Services
        OrderService orderService = new OrderService();
        AgentService agentService = new AgentService();
        AssignmentStrategy strategy = new DeliveryWindowStrategy();
        OrderEventListener listener = new ConsoleLogger();

        // Create Agents
        agentService.registerAgent(new Agent.AgentBuilder()
                .withName("AgentA")
                .withPincodes(Set.of("560087"))
                .build());

        agentService.registerAgent(new Agent.AgentBuilder()
                .withName("AgentB")
                .withPincodes(Set.of("560087"))
                .build());

        // Create Orders
        // Order1: 10:00 - 10:30 (AgentA should be assigned)
        orderService.addOrder(new Order.OrderBuilder()
                .withName("Order1")
                .withPincode("560087")
                .withScheduledTime(LocalDateTime.of(2025, 4, 25, 10, 0))
                .withTimeToDeliver(30)
                .build());

        // Order2: 10:15 - 10:45 (AgentB should be assigned due to overlap)
        orderService.addOrder(new Order.OrderBuilder()
                .withName("Order2")
                .withPincode("560087")
                .withScheduledTime(LocalDateTime.of(2025, 4, 25, 10, 15))
                .withTimeToDeliver(30)
                .build());

        // Order3: 11:00 - 11:30 (Any agent can be assigned)
        orderService.addOrder(new Order.OrderBuilder()
                .withName("Order3")
                .withPincode("560087")
                .withScheduledTime(LocalDateTime.of(2025, 4, 25, 11, 0))
                .withTimeToDeliver(30)
                .build());

        // Use DeliveryWindowStrategy
        DeliveryService deliveryService = new DeliveryService(strategy,agentService,orderService,listener);

        // Run deliveries
        deliveryService.executeDeliveries();
    }
}
