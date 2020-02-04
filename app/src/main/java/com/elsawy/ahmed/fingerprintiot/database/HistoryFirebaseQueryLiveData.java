package com.elsawy.ahmed.fingerprintiot.database;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.elsawy.ahmed.fingerprintiot.Models.HistoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryFirebaseQueryLiveData extends LiveData<ArrayList<HistoryModel>> {

    String LOG_TAG = "HistoryFirebaseQueryLiveData";

    private final HistoryValueEventListener historyListener = new HistoryValueEventListener();

    ArrayList<HistoryModel> historyModelList = new ArrayList<>();

    private Query HistoryQuery;


    public HistoryFirebaseQueryLiveData(String deviceKey) {
        HistoryQuery = FirebaseDatabase.getInstance().getReference("/devicesHistory")
                .child(deviceKey).orderByChild("timestamp").limitToLast(15);
    }

    @Override
    protected void onActive() {
        Log.d(LOG_TAG, "onActive");
        HistoryQuery.addValueEventListener(historyListener);
    }

    @Override
    protected void onInactive() {
        Log.d(LOG_TAG, "onInactive");
        HistoryQuery.removeEventListener(historyListener);
    }

    private class HistoryValueEventListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.getValue() != null) {
                historyModelList.clear();

                for (DataSnapshot userHistorySnapshot : dataSnapshot.getChildren()) {
                    HistoryModel currentHistoryModel = userHistorySnapshot.getValue(HistoryModel.class);
                    historyModelList.add(currentHistoryModel);
                }
                Collections.reverse(historyModelList); // reverse list to show last change state first
                setValue(historyModelList);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    }
}