package com.deliver.app.service;

public class DeliveryTask implements Runnable{
    private final String pincode;
    private final DeliveryService deliveryService;

    public DeliveryTask(String pincode, DeliveryService deliveryService) {
        this.pincode = pincode;
        this.deliveryService = deliveryService;
    }

    @Override
    public void run() {
        deliveryService.processDeliveriesForPinCode(pincode);
    }
}
