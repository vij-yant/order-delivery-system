package com.deliver.app.model;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Agent {

    private final String agentId;
    private final String agentName;
    private Set<String> pinCodes = new HashSet<>();
    private final List<TimeSlot> deliverySchedule = new ArrayList<>();

    private final Lock lock = new ReentrantLock();

    public boolean tryAssign() {
        return lock.tryLock();
    }
    public void release() {
        lock.unlock();
    }

    public String getAgentName() {
        return agentName;
    }

    public List<TimeSlot> getDeliverySchedule() {
        return deliverySchedule;
    }

    public Set<String> getPinCodes() {
        return pinCodes;
    }

    public String getAgentId() {
        return agentId;
    }

    private Agent(AgentBuilder agentBuilder) {
        this.agentId = UUID.randomUUID().toString();
        this.agentName = agentBuilder.agentName;
        this.pinCodes = agentBuilder.pinCodes;
    }

    public static class AgentBuilder {
        private String agentName;
        private final Set<String> pinCodes = new HashSet<>();

        public AgentBuilder withName(String name) {
            this.agentName = name;
            return this;
        }

        public AgentBuilder withPincode(String pinCode) {
            this.pinCodes.add(pinCode);
            return this;
        }

        public AgentBuilder withPincodes(Set<String> pinCodes) {
            this.pinCodes.addAll(pinCodes);
            return this;
        }

        public Agent build() {
            return new Agent(this);
        }
    }

    public boolean isAvailable(String pinCode, LocalDateTime newStart, int duration) {
        if (!pinCodes.contains(pinCode)) {
            return false; // This agent can't deliver to the given pinCode
        }

        LocalDateTime newEnd = newStart.plusMinutes(duration);
        for (TimeSlot slot : deliverySchedule) {
            if (slot.overlaps(newStart, newEnd)) {
                return false; // Time overlap with another delivery
            }
        }
        return true;
    }

    public void addToSchedule(String pinCode, LocalDateTime start, int duration) {
        if(pinCodes.contains(pinCode)) {
            deliverySchedule.add(new TimeSlot(start,start.plusMinutes(duration)));
        }
    }
}
