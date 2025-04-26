package com.deliver.app.service;

import com.deliver.app.command.CommandFactory;
import com.deliver.app.command.DeliverCommand;
import com.deliver.app.command.PickUpCommand;
import com.deliver.app.model.Agent;
import com.deliver.app.model.Order;
import com.deliver.app.model.OrderStatus;
import com.deliver.app.observer.OrderEventListener;
import com.deliver.app.strategy.AssignmentContext;
import com.deliver.app.strategy.AssignmentStrategy;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DeliveryService {
    private final AssignmentStrategy strategy;
    private final AgentService agentService;
    private final OrderService orderService;
    private final OrderEventListener listener;

    public DeliveryService(AssignmentStrategy strategy, AgentService agentService, OrderService orderService, OrderEventListener listener) {
        this.strategy = strategy;
        this.agentService = agentService;
        this.orderService = orderService;
        this.listener = listener;
    }
    public void executeDeliveries() {
        Set<String> pinCodes = orderService.getAllPinCodes();
        ExecutorService executor = Executors.newFixedThreadPool(pinCodes.size());

        try {
            for (String pinCode : pinCodes) {
                executor.submit(() -> processDeliveriesForPinCode(pinCode));
            }
        } finally {
            executor.shutdown();
            try {
                boolean terminated = executor.awaitTermination(60, TimeUnit.SECONDS);
                if (!terminated) {
                    System.out.println("Not all tasks completed within the timeout.");
                } else {
                    System.out.println("All tasks completed successfully.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // reset interrupt flag
            }
        }
    }


    public void processDeliveriesForPinCode(String pinCode) {
        List<Order> orders = orderService.getAllOrdersByPinCode(pinCode);

        List<Agent> agents = agentService.getAllAgentsByPinCode(pinCode);

        for(Order order : orders) {
            if(!(order.getStatus() == OrderStatus.DELIVERED)) {
                processOrder(order,agents);
            }
        }
        // Clear the ThreadLocal after all orders are processed for the pinCode
        CommandFactory.clear();
    }

    public void processOrder(Order order, List<Agent> agents) {
        AssignmentContext context = new AssignmentContext(order,agents);
        Agent assignedAgent = strategy.assignAgent(context);

        if(assignedAgent != null && assignedAgent.tryAssign()){
            // Set order status -> pickedUp
            order.setStatus(OrderStatus.PICKED_UP);
            PickUpCommand pickUpCommand = CommandFactory.getPickUpCommand();
            pickUpCommand.setContext(order, assignedAgent, listener);
            pickUpCommand.execute();

            // For deliver
            // Set order status -> Delivered
            order.setStatus(OrderStatus.DELIVERED);
            DeliverCommand deliverCommand = CommandFactory.getDeliverCommand();
            deliverCommand.setContext(order, assignedAgent, listener);
            deliverCommand.execute();

            assignedAgent.release();
        }else{
            System.out.println("❌ No agent available for order " + order.getOrderId() + " in its pinCode.");
        }
    }
}
