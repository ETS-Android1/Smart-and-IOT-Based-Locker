package com.elsawy.ahmed.fingerprintiot.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.fingerprintiot.Models.UserHistory;
import com.elsawy.ahmed.fingerprintiot.R;
import com.elsawy.ahmed.fingerprintiot.ViewHolder.UserHistoryViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<UserHistoryViewHolder> {

    private String TAG = "HistoryAdapter";
    private Context mContext;

    private FirebaseAuth mAuth;
    private FirebaseUser userData;
    private DatabaseReference ref;
    private ArrayList<UserHistory> usersList;

    public HistoryAdapter(Context mContext,String deviceKey) {
        this.mContext = mContext;
        mAuth = FirebaseAuth.getInstance();
        userData = this.mAuth.getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference();
        usersList = new ArrayList<>();

        if (this.userData != null) {
            HistoryAdapter.this.ref.child("devicesHistory").child(deviceKey).addValueEventListener(new UserHistoryListener());
            HistoryAdapter.this.ref.child("devicesHistory").child(deviceKey).keepSynced(true);
        }
    }

    @NonNull
    @Override
    public UserHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserHistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserHistoryViewHolder holder, int position) {
        final UserHistory currentUser = HistoryAdapter.this.usersList.get(position);
        holder.bindToUserHistory(currentUser);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class UserHistoryListener implements ValueEventListener {
        UserHistoryListener() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {

            if (dataSnapshot.getValue() != null) {
                usersList.clear();

                for (DataSnapshot userHistorySnapshot : dataSnapshot.getChildren()) {
                    UserHistory currentUserHistory = userHistorySnapshot.getValue(UserHistory.class);
                    Log.i(TAG,currentUserHistory.getTimestamp()+currentUserHistory.getNewState()+currentUserHistory.getUsername()+currentUserHistory.getChangedWay());
                    usersList.add(currentUserHistory);
                }
            }

            HistoryAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    }


}
