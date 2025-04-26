package com.deliver.app.service;

import com.deliver.app.model.Agent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AgentService {
    private final Map<String, Agent> agentsById;
    private final Map<String, List<Agent>> agentsByPinCode;

    public AgentService(){
        agentsById = new ConcurrentHashMap<>();
        agentsByPinCode = new ConcurrentHashMap<>();
    }
    public void registerAgent(Agent agent) {
        agentsById.put(agent.getAgentId(),agent);
        for(String pinCode : agent.getPinCodes()) {
            agentsByPinCode.computeIfAbsent(pinCode,k->new LinkedList<>()).add(agent);
        }
    }

    public List<Agent> getAllAgentsByPinCode(String pinCode) {
        return agentsByPinCode.getOrDefault(pinCode,new ArrayList<>());
    }
}
