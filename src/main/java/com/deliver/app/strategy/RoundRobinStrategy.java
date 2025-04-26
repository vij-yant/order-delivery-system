package com.deliver.app.strategy;

import com.deliver.app.model.Agent;
import com.deliver.app.model.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundRobinStrategy implements AssignmentStrategy {

    private final Map<String, Integer> lastAssignedIndex = new HashMap<>();
    @Override
    public Agent assignAgent(AssignmentContext context) {
        List<Agent> agents = context.agents();
        Order order = context.order();
        if (agents.isEmpty()) {
            return null;
        }
        String pincode = order.getPinCode();
        int index = lastAssignedIndex.getOrDefault(pincode, -1);
        index = (index + 1) % agents.size();
        lastAssignedIndex.put(pincode, index);
        return agents.get(index);
    }
}
