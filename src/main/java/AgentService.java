import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentService {

    Map<String,Agent> agentMap = new HashMap<>();

    Map<String,List<Agent>> pincodeToAgents = new HashMap<>();

    public Agent createAgent(String agentName, List<String> pinCodes) {
        Agent agent = new Agent(agentName,pinCodes);
        registerAgent(agent);
        return agent;
    }

    private void registerAgent(Agent agent) {
        agentMap.putIfAbsent(agent.getAgentId(),agent);
        List<String> pinCodes = agent.getPinCodes();
        for(String pinCode : pinCodes) {
            pincodeToAgents.computeIfAbsent(pinCode,k-> new ArrayList<>()).add(agent);
        }
    }

    public List<Agent> getAgentsForPinCode(String pinCode) {
        return pincodeToAgents.getOrDefault(pinCode,new ArrayList<>());
    }

    public  Map<String,Agent> getAllAgents() {
        return agentMap;
    }

}
