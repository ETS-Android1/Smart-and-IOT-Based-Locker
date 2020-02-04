package com.elsawy.ahmed.fingerprintiot.Models;


import android.os.Parcel;
import android.os.Parcelable;

public class DeviceModel implements Parcelable {

    private String key;
    private String name;
    private String type;
    private String phoneNumber;
    private String state;

    public DeviceModel() {
    }

    protected DeviceModel(Parcel in) {
        key = in.readString();
        name = in.readString();
        type = in.readString();
        phoneNumber = in.readString();
        state = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(phoneNumber);
        dest.writeString(state);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DeviceModel> CREATOR = new Creator<DeviceModel>() {
        @Override
        public DeviceModel createFromParcel(Parcel in) {
            return new DeviceModel(in);
        }

        @Override
        public DeviceModel[] newArray(int size) {
            return new DeviceModel[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDevice(DeviceModel deviceModel){
        this.state = deviceModel.state;
        this.type = deviceModel.type;
        this.key = deviceModel.key;
        this.phoneNumber = deviceModel.phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceModel deviceModel = (DeviceModel) o;
        return key.equals(deviceModel.key);
    }

}
