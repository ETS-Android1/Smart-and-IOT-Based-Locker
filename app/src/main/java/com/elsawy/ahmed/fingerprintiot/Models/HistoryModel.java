package com.elsawy.ahmed.fingerprintiot.Models;

public class HistoryModel {

    private String username;
    private String newState;
    private long timestamp;
    private String changedWay;

    public HistoryModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getChangedWay() {
        return changedWay;
    }

    public void setChangedWay(String changedWay) {
        this.changedWay = changedWay;
    }
}
