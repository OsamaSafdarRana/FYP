package com.exercise.caraugmentedreality.Model;

public class Reminder {
    private String ServiceType, KMs;

    public Reminder() {
    }

    public Reminder(String ServiceType, String KMs) {
        this.ServiceType = ServiceType;
        this.KMs = KMs;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String ServiceType) {
        this.ServiceType = ServiceType;
    }

    public String getKMs() {
        return KMs;
    }

    public void setKMs(String KMs) {
        this.KMs = KMs;
    }
}
