package com.deliver.app.command;

import com.deliver.app.model.Agent;
import com.deliver.app.model.Order;
import com.deliver.app.observer.OrderEventListener;

public class DeliverCommand implements Command {
    private Order order;
    private Agent agent;
    private OrderEventListener listener;

    public DeliverCommand() {
        // No args constructor
    }

    public void setContext(Order order, Agent agent, OrderEventListener listener) {
        this.order = order;
        this.agent = agent;
        this.listener = listener;
    }

    @Override
    public void execute() {
        if (order == null || agent == null || listener == null) {
            throw new IllegalStateException("DeliverCommand not properly initialized");
        }
        listener.onDeliver(order, agent);
    }
}
