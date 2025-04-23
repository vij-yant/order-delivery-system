package com.deliver.app.service;

import com.deliver.app.model.Agent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AgentService {

    private final Map<String, Agent> agentsById = new ConcurrentHashMap<>();
    private final Map<String, Queue<Agent>> agentsByPinCode = new ConcurrentHashMap<>();
    private static final AgentService instance = new AgentService();
    private AgentService(){}
    public static AgentService getInstance() {
        return instance;
    }

    public void registerAgent(Agent agent) {
        agentsById.put(agent.getAgentId(),agent);
        for(String pinCode : agent.getPinCodes()) {
            agentsByPinCode.computeIfAbsent(pinCode,k->new LinkedList<>()).add(agent);
        }
    }

    public Agent getNextAvailableAgent(String pinCode) {
        Queue<Agent> queue = agentsByPinCode.get(pinCode);
        if (queue == null || queue.isEmpty()) return null;

        Agent agent = queue.poll();  // Round-robin: fetch head
        queue.add(agent);            // Push to back
        return agent;
    }

    public Agent getAgentById(String agentId) {
        return agentsById.get(agentId);
    }

    public List<Agent> getAgentsByPinCode(String pinCode) {
        return new ArrayList<>(agentsByPinCode.get(pinCode));
    }

    public void clear() {
        agentsById.clear();
        agentsByPinCode.clear();
    }
}
