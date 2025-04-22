import java.time.LocalDateTime;
import java.util.UUID;


enum OrderStatus {
    CREATED,PICKED_UP,DELIVERED
}

public class Order {


    public Order(String orderName, String pinCode, LocalDateTime createdAt, int durationInMinutes) {
        this.orderName = orderName;
        this.orderId = UUID.randomUUID().toString();
        this.pinCode = pinCode;
        this.createdAt = createdAt;
        this.durationInMinutes = durationInMinutes;
        this.status = OrderStatus.CREATED;
    }

    public Order(String orderName, String pinCode) {
        this(orderName,pinCode,null,0);
    }

    String orderId;
    String orderName;
    String pinCode;
    LocalDateTime createdAt;
    OrderStatus status;
    int durationInMinutes;
    Agent assignedAgent;

    public String getOrderName() {
        return orderName;
    }

    public String getPinCode() {
        return pinCode;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public Agent getAssignedAgent() {
        return assignedAgent;
    }

    public void setAssignedAgent(Agent assignedAgent) {
        this.assignedAgent = assignedAgent;
    }
    public String getOrderId() {
        return orderId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


}
