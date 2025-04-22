

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Agent {
    public Agent(String agentName, List<String> pinCodes) {
        AgentName = agentName;
        this.pinCodes = pinCodes;
        this.agentId = UUID.randomUUID().toString();
        this.assignedOrders = new ArrayList<>();
    }

    String agentId;
    String AgentName;
    List<String> pinCodes;
    List<Order> assignedOrders;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public void setAgentName(String agentName) {
        AgentName = agentName;
    }

    public void setPinCodes(List<String> pinCodes) {
        this.pinCodes = pinCodes;
    }

    public void setAssignedOrders(List<Order> assignedOrders) {
        this.assignedOrders = assignedOrders;
    }

    public String getAgentName() {
        return AgentName;
    }

    public List<String> getPinCodes() {
        return pinCodes;
    }

    public List<Order> getAssignedOrders() {
        return assignedOrders;
    }
}
