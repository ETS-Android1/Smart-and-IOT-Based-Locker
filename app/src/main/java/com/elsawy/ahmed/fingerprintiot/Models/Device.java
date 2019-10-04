package com.elsawy.ahmed.fingerprintiot.Models;

import java.util.Objects;

public class Device {

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


}
