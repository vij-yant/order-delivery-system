package com.deliver.app.strategy;

import com.deliver.app.model.Agent;
import com.deliver.app.model.Order;

import java.util.List;

public class DeliveryWindowStrategy implements AssignmentStrategy{
    @Override
    public Agent assignAgent(AssignmentContext context) {
        List<Agent> agents = context.agents();
        Order order = context.order();
        if (agents == null || agents.isEmpty())
            return null;
        for (Agent agent : agents) {
            if (agent.isAvailable(order.getPinCode(), order.getScheduledTime(), order.getTimeToDeliverInMinutes())) {
                agent.addToSchedule(order.getPinCode(), order.getScheduledTime(), order.getTimeToDeliverInMinutes());
                return agent;
            }
        }
        return null; // no available agent
    }
}
