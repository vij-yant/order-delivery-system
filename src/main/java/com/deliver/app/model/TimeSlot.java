package com.deliver.app.model;

import java.time.LocalDateTime;

public class TimeSlot {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public TimeSlot(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public boolean overlaps(LocalDateTime otherStart, LocalDateTime otherEnd) {
        return !(otherEnd.isBefore(start) || otherStart.isAfter(end));
    }
}
