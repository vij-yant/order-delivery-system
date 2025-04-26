package com.deliver.app.strategy;

import com.deliver.app.model.Agent;
import com.deliver.app.model.Order;

import java.util.List;

public record AssignmentContext(Order order, List<Agent> agents) {
}
