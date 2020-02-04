package com.elsawy.ahmed.fingerprintiot.Activities;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.elsawy.ahmed.fingerprintiot.Models.HistoryModel;
import com.elsawy.ahmed.fingerprintiot.database.HistoryFirebaseQueryLiveData;

import java.util.ArrayList;

public class HistoryViewModel  extends ViewModel {

    private static final String TAG = "HistoryViewModel";

    @NonNull
    public LiveData<ArrayList<HistoryModel>> getHistoryListLiveData(String deviceKey) {
        final HistoryFirebaseQueryLiveData liveData = new HistoryFirebaseQueryLiveData(deviceKey);

        return liveData;
    }


}

