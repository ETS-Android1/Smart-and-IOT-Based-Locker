package com.elsawy.ahmed.fingerprintiot.data.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.elsawy.ahmed.fingerprintiot.data.Models.HistoryModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryFirebaseQueryLiveData extends LiveData<ArrayList<HistoryModel>> {

    private ArrayList<HistoryModel> historyModelList = new ArrayList<>();

    private Query historyQuery;
    private DatabaseReference deviceUserIdRef;

    public HistoryFirebaseQueryLiveData(String deviceKey) {
        historyQuery = FirebaseDatabase.getInstance().getReference("/devicesHistory")
                .child(deviceKey).orderByChild("timestamp").limitToLast(20);
        deviceUserIdRef = FirebaseDatabase.getInstance().getReference("/deviceUserId").child(deviceKey);

    }

    @Override
    protected void onActive() {
        historyQuery.addChildEventListener(historyChildEventListener);
    }

    @Override
    protected void onInactive() {
        historyQuery.removeEventListener(historyChildEventListener);
    }

    private ChildEventListener historyChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot userHistorySnapshot, @Nullable String s) {

            HistoryModel currentHistoryModel = userHistorySnapshot.getValue(HistoryModel.class);

            deviceUserIdRef.child(currentHistoryModel.getId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        String username = dataSnapshot.getValue(String.class);
                        currentHistoryModel.setUsername(username);
                    }
                    historyModelList.add(0, currentHistoryModel);
                    setValue(historyModelList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }

    };

}