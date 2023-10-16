package org.ogzhnulucay.model.common;

import java.time.LocalTime;

public class StructuredSession {

    private LocalTime scheduleTime;
    private String fullDescription; //Full description
    private String length;
    private int lengthMinutes;

    public StructuredSession(String fullDescription, String length) {
        this.fullDescription = fullDescription;
        this.length = length;
    }

    public StructuredSession(String fullDescription, String length, int lengthMinutes) {
        this.fullDescription = fullDescription;
        this.length = length;
        this.lengthMinutes = lengthMinutes;
    }

    public LocalTime getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(LocalTime scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public int getLengthMinutes() {
        return lengthMinutes;
    }

    public void setLengthMinutes(int lengthMinutes) {
        this.lengthMinutes = lengthMinutes;
    }
}
