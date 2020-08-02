package com.IOT.fingerprint.data.database;

import android.content.Context;

import com.IOT.fingerprint.data.Models.HistoryModel;
import com.IOT.fingerprint.data.SharedPrefManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HistoryFirebaseInsertDB {

    private static final DatabaseReference REF =
            FirebaseDatabase.getInstance().getReference();

    public static void InsertNewHistory(Context mContext, final String key, final String userID, final String state) {
        HistoryModel userHistory = new HistoryModel();
        userHistory.setId(userID);
        userHistory.setUsername(SharedPrefManager.getInstance(mContext).getUsername());
        userHistory.setTimestamp(System.currentTimeMillis() / 1000);
        userHistory.setChangedWay("mobile");
        userHistory.setNewState(state);

        REF.child("devicesHistory").child(key).push().setValue(userHistory);
    }

}
