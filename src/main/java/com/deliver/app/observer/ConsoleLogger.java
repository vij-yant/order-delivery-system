package com.deliver.app.observer;

import com.deliver.app.model.Agent;
import com.deliver.app.model.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleLogger implements OrderEventListener {
    @Override
    public void onPickup(Order order, Agent agent) {
        if(order.getScheduledTime() != null && order.getTimeToDeliverInMinutes() > 0) {
            LocalDateTime scheduled = order.getScheduledTime();
            String pickupTime = scheduled.format(DateTimeFormatter.ofPattern("hh:mm a, MMM dd, yyyy"));

            System.out.printf("🚚 %s has picked up %s at %s%n", agent.getAgentName(), order.getName(), pickupTime);
        } else {
            System.out.printf("🚚 %s has picked up %s%n", agent.getAgentName(), order.getName());
        }
    }

    @Override
    public void onDeliver(Order order, Agent agent) {
        if(order.getScheduledTime() != null && order.getTimeToDeliverInMinutes() > 0) {
            LocalDateTime deliveryTime = order.getScheduledTime().plusMinutes(order.getTimeToDeliverInMinutes());
            String deliverTime = deliveryTime.format(DateTimeFormatter.ofPattern("hh:mm a, MMM dd, yyyy"));

            System.out.printf("📦 %s has completed delivery of %s to %s at %s%n",
                    agent.getAgentName(), order.getName(), order.getPinCode(), deliverTime);
        } else {
            System.out.printf("📦 %s has delivered %s to %s%n",
                    agent.getAgentName(), order.getName(), order.getPinCode());
        }
    }
}
