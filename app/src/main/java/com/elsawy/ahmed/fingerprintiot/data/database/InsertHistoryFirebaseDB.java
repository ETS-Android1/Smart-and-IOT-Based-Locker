package com.elsawy.ahmed.fingerprintiot.data.database;

import android.content.Context;

import com.elsawy.ahmed.fingerprintiot.data.Models.HistoryModel;
import com.elsawy.ahmed.fingerprintiot.data.SharedPrefManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertHistoryFirebaseDB{

    private static final DatabaseReference REF =
            FirebaseDatabase.getInstance().getReference();

    public static void InsertNewHistory(Context mContext, final String key, final String userID, final String state) {
        HistoryModel userHistory = new HistoryModel();
        //TODO Read id from firebase
        userHistory.setId(userID);
        //TODO remove this line
        userHistory.setUsername(SharedPrefManager.getInstance(mContext).getUsername());
        userHistory.setTimestamp(System.currentTimeMillis() / 1000);
        userHistory.setChangedWay("mobile");
        userHistory.setNewState(state);

        REF.child("devicesHistory").child(key).push().setValue(userHistory);
    }

}
