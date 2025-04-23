package com.deliver.app.service;

import com.deliver.app.model.Agent;
import com.deliver.app.model.Order;
import com.deliver.app.model.OrderStatus;
import com.deliver.app.observer.ConsoleLogger;
import com.deliver.app.observer.OrderEventListener;
import com.deliver.app.strategy.AssignmentStrategy;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeliveryService {

    private final AssignmentStrategy strategy;
    private final OrderService orderService = OrderService.getInstance();
    private final OrderEventListener listener = new ConsoleLogger();

    public DeliveryService(AssignmentStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeDeliveries() {
        Set<String> pinCodes = OrderService.getInstance().getAllPinCodes();

        ExecutorService executor = Executors.newFixedThreadPool(pinCodes.size());

        for(String pin : pinCodes) {
            executor.submit(() -> processPinCodeDeliveries(pin));
        }

        executor.shutdown();
    }

    public void processPinCodeDeliveries(String pinCode) {
        List<Order> orders = orderService.getAllOrdersByPinCode(pinCode);

        for(Order order : orders) {
            if(!(order.getStatus() == OrderStatus.DELIVERED)) {
                Agent agent = strategy.assignAgent(order);
                if(agent != null) {
                    processOrder(order,agent);
                }else{
                    System.out.println("❗ No available agent for pincode: " + pinCode);
                }
            }
        }
    }

    public void processOrder(Order order, Agent agent) {
        if(agent.tryAssign()) {
            // Set order status -> pickedUp
            order.setStatus(OrderStatus.PICKED_UP);
            listener.onPickup(order,agent);

            // Set order status -> Delivered
            order.setStatus(OrderStatus.DELIVERED);
            listener.onDeliver(order,agent);
        }
        agent.release();
    }
}
