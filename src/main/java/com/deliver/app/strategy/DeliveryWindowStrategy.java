package com.deliver.app.strategy;

import com.deliver.app.model.Agent;
import com.deliver.app.model.Order;
import com.deliver.app.service.AgentService;

import java.util.List;

public class DeliveryWindowStrategy implements AssignmentStrategy{
    private  final AgentService agentService = AgentService.getInstance();
    @Override
    public Agent assignAgent(Order order) {
        List<Agent> agents = agentService.getAgentsByPinCode(order.getPinCode());

        if(agents == null || agents.isEmpty())
            return  null;
        for(Agent agent : agents) {
            if(agent.isAvailable(order.getPinCode(),order.getScheduledTime(),order.getTimeToDeliverInMinutes())){
                agent.addToSchedule(order.getPinCode(),order.getScheduledTime(),order.getTimeToDeliverInMinutes());
                return agent;
            }
        }
        return null; // no available agent
    }
}
