package com.deliver.app.observer;

import com.deliver.app.model.Agent;
import com.deliver.app.model.Order;

public interface OrderEventListener {
    void onPickup(Order order, Agent agent);
    void onDeliver(Order order, Agent agent);
}
