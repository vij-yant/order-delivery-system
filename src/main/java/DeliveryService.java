import java.time.format.DateTimeFormatter;
import java.util.List;

public class DeliveryService {

    private final OrderService orderService;
    private final AgentService agentService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a, MMM dd, yyyy");


    public DeliveryService(OrderService orderService, AgentService agentService) {
        this.agentService = agentService;
        this.orderService = orderService;
    }

    public void executeDeliveries() {
        for(String pinCode : orderService.getAllOrders().values().stream().map(Order::getPinCode).toList()) {
            Order order;
            while((order = orderService.getNextOrderForPincode(pinCode)) != null) {
                List<Agent> agents = agentService.getAgentsForPinCode(pinCode);
                if(agents.isEmpty()) {
                    System.out.println("No agents available to deliver for pincode : "+ pinCode);
                    continue;
                }
                Agent assignedAgent = agents.get(0);
                orderService.assignOrderToAgent(order,assignedAgent);

                if (order.getCreatedAt() != null) {
                    String startTime = formatter.format(order.getCreatedAt());
                    String endTime = formatter.format(order.getCreatedAt().plusMinutes(order.getDurationInMinutes()));
                    System.out.println(assignedAgent.getAgentName() + " has picked up " + order.getOrderName() + " at " + startTime);
                    System.out.println(assignedAgent.getAgentName() + " has completed delivery of " + order.getOrderName() +
                            " to " + order.getPinCode() + " at " + endTime);
                } else {
                    System.out.println(assignedAgent.getAgentName() + " has picked up " + order.getOrderName());
                    System.out.println(assignedAgent.getAgentName() + " has delivered " + order.getOrderName() +
                            " to " + order.getPinCode());
                }

                order.setStatus(OrderStatus.DELIVERED);
            }
        }
    }
}
