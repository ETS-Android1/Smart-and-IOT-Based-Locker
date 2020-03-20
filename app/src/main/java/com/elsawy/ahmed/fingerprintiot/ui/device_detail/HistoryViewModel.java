package com.elsawy.ahmed.fingerprintiot.ui.device_detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.elsawy.ahmed.fingerprintiot.data.Models.HistoryModel;
import com.elsawy.ahmed.fingerprintiot.data.database.HistoryFirebaseQueryLiveData;

import java.util.ArrayList;

public class HistoryViewModel  extends ViewModel {

    private static final String TAG = "HistoryViewModel";

    @NonNull
    public LiveData<ArrayList<HistoryModel>> getHistoryListLiveData(String deviceKey) {
        final HistoryFirebaseQueryLiveData liveData = new HistoryFirebaseQueryLiveData(deviceKey);

        return liveData;
    }


}

