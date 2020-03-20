package com.elsawy.ahmed.fingerprintiot.data.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.elsawy.ahmed.fingerprintiot.data.Models.HistoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryFirebaseQueryLiveData extends LiveData<ArrayList<HistoryModel>> {

    String LOG_TAG = "HistoryFirebaseQueryLiveData";

    private final HistoryValueEventListener historyListener = new HistoryValueEventListener();

    private ArrayList<HistoryModel> historyModelList = new ArrayList<>();

    private Query historyQuery;
    private DatabaseReference deviceUserIdRef;

    public HistoryFirebaseQueryLiveData(String deviceKey) {
        Log.d(LOG_TAG, deviceKey);
        historyQuery = FirebaseDatabase.getInstance().getReference("/devicesHistory")
                .child(deviceKey).orderByChild("timestamp").limitToLast(15);
        deviceUserIdRef = FirebaseDatabase.getInstance().getReference("/deviceUserId").child(deviceKey);

    }

    @Override
    protected void onActive() {
        Log.d(LOG_TAG, "onActive");
        historyQuery.addValueEventListener(historyListener);
    }

    @Override
    protected void onInactive() {
        Log.d(LOG_TAG, "onInactive");
        historyQuery.removeEventListener(historyListener);
    }

    private class HistoryValueEventListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.getValue() != null) {
                historyModelList.clear();
                Log.i(LOG_TAG,dataSnapshot.getChildren().toString());

                for (final DataSnapshot userHistorySnapshot : dataSnapshot.getChildren()) {
                    Log.i(LOG_TAG,userHistorySnapshot.toString());
                    HistoryModel currentHistoryModel = userHistorySnapshot.getValue(HistoryModel.class);

                    deviceUserIdRef.child(currentHistoryModel.getId()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                String username = dataSnapshot.getValue(String.class);
                                currentHistoryModel.setUsername(username);
                                Log.i("currentHistoryModel", dataSnapshot.toString());
                                Log.i("currentHistoryModel", username);
                            }
                            historyModelList.add(currentHistoryModel);
//                            Collections.reverse(historyModelList); // reverse list to show last change state first
                            setValue(historyModelList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

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