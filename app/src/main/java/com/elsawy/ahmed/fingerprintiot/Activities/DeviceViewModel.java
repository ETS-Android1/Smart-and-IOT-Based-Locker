package com.elsawy.ahmed.fingerprintiot.Activities;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.elsawy.ahmed.fingerprintiot.Models.DeviceModel;
import com.elsawy.ahmed.fingerprintiot.database.DeviceFirebaseQueryLiveData;


import java.util.ArrayList;
import java.util.List;

public class DeviceViewModel extends ViewModel {

    private static final String TAG = "DeviceViewModel";

    private final DeviceFirebaseQueryLiveData liveData = new DeviceFirebaseQueryLiveData();

    MutableLiveData<List<DeviceModel>> DevicesNameMutableLiveData = new MutableLiveData<>();


    @NonNull
    public LiveData<ArrayList<DeviceModel>> getDevicesListLiveData() {
        return liveData;
    }


}
