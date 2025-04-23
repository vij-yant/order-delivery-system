package com.deliver.app.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {
    private final String orderId;
    private final String name;
    private final String pinCode;
    private final LocalDateTime scheduledTime;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    private OrderStatus status;
    private final int timeToDeliverInMinutes;

    public String getOrderId() {
        return orderId;
    }

    public int getTimeToDeliverInMinutes() {
        return timeToDeliverInMinutes;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getName() {
        return name;
    }

    private Order(OrderBuilder orderBuilder) {
        this.orderId = UUID.randomUUID().toString();
        this.name = orderBuilder.name;
        this.pinCode = orderBuilder.pinCode;
        this.scheduledTime = orderBuilder.scheduledTime;
        this.timeToDeliverInMinutes = orderBuilder.timeToDeliverInMinutes;
        setStatus(OrderStatus.CREATED);
    }

    public static class OrderBuilder {
        private String name;
        private String pinCode;
        private LocalDateTime scheduledTime;
        private int timeToDeliverInMinutes;

        public OrderBuilder withName(String name) {
            this.name = name;
            return this;
        }
        public OrderBuilder withPincode(String pinCode) {
            this.pinCode = pinCode;
            return this;
        }
        public OrderBuilder withScheduledTime(LocalDateTime scheduledTime){
            this.scheduledTime = scheduledTime;
            return this;
        }

        public OrderBuilder withTimeToDeliver(int timeToDeliverInMinutes) {
            this.timeToDeliverInMinutes = timeToDeliverInMinutes;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
