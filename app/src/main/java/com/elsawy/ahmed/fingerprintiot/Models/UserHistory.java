package com.elsawy.ahmed.fingerprintiot.Models;

public class UserHistory {

    public String username;
    public String newState;
    public long timestamp;
    public String changedWay;

    public UserHistory() {
    }

    public UserHistory(String username, String newState, long timestamp, String changedWay) {
        this.username = username;
        this.newState = newState;
        this.timestamp = timestamp;
        this.changedWay = changedWay;
    }
}
