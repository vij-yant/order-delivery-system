package com.deliver.app;

import com.deliver.app.model.Agent;
import com.deliver.app.model.Order;
import com.deliver.app.service.AgentService;
import com.deliver.app.service.DeliveryTask;
import com.deliver.app.service.OrderService;
import com.deliver.app.strategy.AssignmentStrategy;
import com.deliver.app.strategy.RoundRobinStrategy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RoundRobinConcurrencyTest {
    public static void main(String[] args) {

        AssignmentStrategy strategy = new RoundRobinStrategy();
        // Get service instances
        OrderService orderService = OrderService.getInstance();
        AgentService agentService = AgentService.getInstance();

        // Agents
        agentService.registerAgent(new Agent.AgentBuilder().withName("AgentA").withPincode("560087").build());
        agentService.registerAgent(new Agent.AgentBuilder().withName("AgentB").withPincode("560088").build());
        agentService.registerAgent(new Agent.AgentBuilder().withName("AgentC").withPincode("560089").build());

        // Orders
        orderService.addOrder(new Order.OrderBuilder().withName("Order1").withPincode("560087").build());
        orderService.addOrder(new Order.OrderBuilder().withName("Order2").withPincode("560087").build());
        orderService.addOrder(new Order.OrderBuilder().withName("Order3").withPincode("560088").build());
        orderService.addOrder(new Order.OrderBuilder().withName("Order4").withPincode("560088").build());
        orderService.addOrder(new Order.OrderBuilder().withName("Order5").withPincode("560089").build());
        orderService.addOrder(new Order.OrderBuilder().withName("Order6").withPincode("560089").build());

        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (String pinCode : orderService.getAllPinCodes()) {
            executor.submit(new DeliveryTask(pinCode, strategy));
        }

        executor.shutdown();
    }
}
