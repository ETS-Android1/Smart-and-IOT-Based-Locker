package com.elsawy.ahmed.fingerprintiot.Models;


import android.os.Parcel;
import android.os.Parcelable;

public class Device implements Parcelable {

    public String key;
    public String name;
    public String type;
    public String state;

    public Device() {
    }

    public Device(String key, String name, String type, String state) {
        this.key = key;
        this.name = name;
        this.type = type;
        this.state = state;
    }

    protected Device(Parcel in) {
        key = in.readString();
        name = in.readString();
        type = in.readString();
        state = in.readString();
    }

    public static final Creator<Device> CREATOR = new Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };

    public void setDevice(Device device){
        this.state = device.state;
        this.type = device.type;
        this.key = device.key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return key.equals(device.key);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeString(state);
    }
}
