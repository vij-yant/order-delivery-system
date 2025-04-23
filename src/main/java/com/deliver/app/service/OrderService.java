package com.deliver.app.service;

import com.deliver.app.model.Order;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OrderService {
    private final Map<String, Order> ordersById = new ConcurrentHashMap<>();
    private final Map<String, List<Order>> ordersByPincode = new ConcurrentHashMap<>();
    private static final OrderService instance = new OrderService();
    private OrderService() {}

    public static OrderService getInstance() {
        return instance;
    }

    public void addOrder(Order order) {
        ordersById.put(order.getOrderId(),order);
        ordersByPincode.computeIfAbsent(order.getPinCode(),k ->new ArrayList<>()).add(order);
    }

    public Order getOrderById(String orderId) {
        return ordersById.get(orderId);
    }

    public List<Order> getAllOrdersByPinCode(String pinCode) {
        return ordersByPincode.getOrDefault(pinCode,new ArrayList<>());
    }

    public Set<String> getAllPinCodes() {
        return ordersByPincode.keySet();
    }

    public void clear() {
        ordersByPincode.clear();
        ordersById.clear();
    }
}
