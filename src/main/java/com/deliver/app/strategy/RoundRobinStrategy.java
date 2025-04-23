package com.deliver.app.strategy;

import com.deliver.app.model.Agent;
import com.deliver.app.model.Order;
import com.deliver.app.service.AgentService;

public class RoundRobinStrategy implements AssignmentStrategy {
    private final AgentService agentService = AgentService.getInstance();

    @Override
    public Agent assignAgent(Order order) {
        return agentService.getNextAvailableAgent(order.getPinCode());
    }
}
