package com.elsawy.ahmed.fingerprintiot.database;

import android.content.Context;
import android.util.Log;

import com.elsawy.ahmed.fingerprintiot.Activities.AddDevice;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DeviceFirebaseDataBase {

    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final FirebaseUser userData = mAuth.getCurrentUser();
    private static final DatabaseReference REF =
            FirebaseDatabase.getInstance().getReference();

    public static void updateDeviceState(final Context mContext, final String key, final String state) {
        REF.child("Devices").child(key).child("state").setValue(state);
        HistoryFirebaseDataBase.createNewHistory(mContext, key, state);
    }

    public static void insertNewDevice(String deviceName, String userID, String deviceType, String devicePhone, boolean isNewDevice, String existDeviceKey) {

        String key;
        if (!isNewDevice) {
            key = existDeviceKey;
        } else {
            key = REF.child("Devices").push().getKey();
        }

        Map<String, String> deviceInfo = new HashMap<>();
        deviceInfo.put("type", deviceType);
        deviceInfo.put("phoneNumber", devicePhone);
        deviceInfo.put("key", key);
        deviceInfo.put("state", "OFF");

        Map<String, String> userDevice = new HashMap<>();
        userDevice.put("deviceName", deviceName);
        userDevice.put("userID", userID);

        REF.child("userDevices").child(userData.getUid()).child(key).setValue(userDevice);

        if (isNewDevice)
            REF.child("Devices").child(key).setValue(deviceInfo);

    }

}