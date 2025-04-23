package com.deliver.app.service;

import com.deliver.app.strategy.AssignmentStrategy;

public class DeliveryTask implements Runnable{
    private final String pincode;
    private final AssignmentStrategy strategy;

    public DeliveryTask(String pincode, AssignmentStrategy strategy) {
        this.pincode = pincode;
        this.strategy = strategy;
    }

    @Override
    public void run() {
        DeliveryService deliveryService = new DeliveryService(strategy);
        deliveryService.processPinCodeDeliveries(pincode);
    }
}
