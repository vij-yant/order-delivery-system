package com.deliver.app.service;

import com.deliver.app.model.Order;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OrderService {
    private final Map<String, Order> ordersById;
    private final Map<String, List<Order>> ordersByPincode;
    public OrderService() {
        ordersById = new ConcurrentHashMap<>();
        ordersByPincode = new ConcurrentHashMap<>();
    }

    public void addOrder(Order order) {
        ordersById.put(order.getOrderId(),order);
        ordersByPincode.computeIfAbsent(order.getPinCode(),k ->new ArrayList<>()).add(order);
    }

    public List<Order> getAllOrdersByPinCode(String pinCode) {
        return ordersByPincode.getOrDefault(pinCode,new ArrayList<>());
    }

    public Set<String> getAllPinCodes() {
        return ordersByPincode.keySet();
    }
}
