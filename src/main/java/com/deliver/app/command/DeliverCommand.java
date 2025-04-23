package com.deliver.app.command;

import com.deliver.app.model.Agent;
import com.deliver.app.model.Order;
import com.deliver.app.observer.OrderEventListener;

public class DeliverCommand implements Command{
    private final Order order;
    private final Agent agent;
    private final OrderEventListener listener;

    public DeliverCommand(Order order, Agent agent, OrderEventListener listener) {
        this.order = order;
        this.agent = agent;
        this.listener = listener;
    }

    @Override
    public void execute() {
        listener.onDeliver(order,agent);
    }
}
