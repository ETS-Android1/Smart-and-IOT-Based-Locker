package com.elsawy.ahmed.fingerprintiot.database;

import android.content.Context;

import com.elsawy.ahmed.fingerprintiot.Models.HistoryModel;
import com.elsawy.ahmed.fingerprintiot.Models.SharedPrefManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HistoryFirebaseDataBase {

    private static final DatabaseReference REF =
            FirebaseDatabase.getInstance().getReference();

    public static void createNewHistory(Context mContext, final String key, final String state) {
        HistoryModel userHistory = new HistoryModel();
        //TODO Read id from firebase
        userHistory.setId("100");
        userHistory.setUsername(SharedPrefManager.getInstance(mContext).getUsername());
        userHistory.setTimestamp(System.currentTimeMillis() / 1000);
        userHistory.setChangedWay("mobile");
        userHistory.setNewState(state);

        REF.child("devicesHistory").child(key).push().setValue(userHistory);
    }

}
