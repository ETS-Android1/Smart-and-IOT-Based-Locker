package com.elsawy.ahmed.fingerprintiot.Models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

@IgnoreExtraProperties
public class HistoryModel {

    @PropertyName("ID")
    private String Id;
    @Exclude
    private String username;
    @PropertyName("state")
    private String newState;
    @PropertyName("timestamp")
    private Long timestamp;
    @PropertyName("changedWay")
    private String changedWay;

    public HistoryModel() {
    }

    @PropertyName("ID")
    public String getId() {
        return Id;
    }

    @PropertyName("ID")
    public void setId(String id) {
        Id = id;
    }

    @Exclude
    public String getUsername() {
        return username;
    }

    @Exclude
    public void setUsername(String username) {
        this.username = username;
    }

    @PropertyName("state")
    public String getNewState() {
        return newState;
    }

    @PropertyName("state")
    public void setNewState(String newState) {
        this.newState = newState;
    }

    @PropertyName("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    @PropertyName("timestamp")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @PropertyName("changedWay")
    public String getChangedWay() {
        return changedWay;
    }

    @PropertyName("changedWay")
    public void setChangedWay(String changedWay) {
        this.changedWay = changedWay;
    }

}