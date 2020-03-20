package com.elsawy.ahmed.fingerprintiot.data.database;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.elsawy.ahmed.fingerprintiot.data.Models.DeviceModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeviceFirebaseQueryLiveData extends LiveData<ArrayList<DeviceModel>> {

    String LOG_TAG = "DeviceFirebaseQueryLiveData";

    private final DeviceValueEventListener listener = new DeviceValueEventListener();

    private ArrayList<DeviceModel> DevicesList = new ArrayList<>();

    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final FirebaseUser userData = mAuth.getCurrentUser();
    private static final DatabaseReference DEVICE_NAME_REF =
            FirebaseDatabase.getInstance().getReference("/userDevices").child(userData.getUid());
    private static final DatabaseReference DEVICE_Data_REF =
            FirebaseDatabase.getInstance().getReference("/Devices");

    public DeviceFirebaseQueryLiveData() {}

    @Override
    protected void onActive() {
        Log.d(LOG_TAG, "onActive");
        DEVICE_NAME_REF.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(LOG_TAG, "onInactive");
        DEVICE_NAME_REF.removeEventListener(listener);
    }

    private class DeviceValueEventListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            Log.i("mvvm","onDataChange");
            if (dataSnapshot.getValue() != null) {
                DevicesList.clear();

                for (DataSnapshot userDeviceSnapshot : dataSnapshot.getChildren()) {
                    String key = userDeviceSnapshot.getKey();

                    DeviceModel newDevice = new DeviceModel();
                    String deviceName = userDeviceSnapshot.child("deviceName").getValue(String.class);
                    String userID = userDeviceSnapshot.child("userID").getValue(String.class);

                    newDevice.setName(deviceName);
                    newDevice.setUserID(userID);
                    newDevice.setKey(key);
                    Log.i("userIDm",deviceName+userID);

                    DEVICE_Data_REF.child(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getValue() != null) {
                                Log.i("TAG", dataSnapshot.toString());

                                DeviceModel changedDeviceModel = dataSnapshot.getValue(DeviceModel.class);

                                int deviceIndex = DevicesList.indexOf(changedDeviceModel);
                                if (deviceIndex == -1) {
                                    newDevice.setDevice(changedDeviceModel);
                                    Log.i("TAG2", DevicesList.size()+"");
                                    DevicesList.add(newDevice);
                                    Log.i("TAG3", DevicesList.size()+"");
                                } else {
                                    DevicesList.get(deviceIndex).setState(changedDeviceModel.getState());
                                }
                            }
                            Log.i("TAG4", DevicesList.size()+"");
                            setValue(DevicesList);
                            Log.i("mvvm","onDataChange" + DevicesList.size());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.i("mvvm","onCancelled" + DevicesList.size());

                        }
                    });
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    }
}