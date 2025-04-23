package com.deliver.app.strategy;

import com.deliver.app.model.Agent;
import com.deliver.app.model.Order;

import java.util.List;

public interface AssignmentStrategy {
    public Agent assignAgent(Order order);
}
