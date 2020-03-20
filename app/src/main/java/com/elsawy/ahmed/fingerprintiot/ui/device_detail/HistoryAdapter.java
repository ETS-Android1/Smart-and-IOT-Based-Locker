package com.elsawy.ahmed.fingerprintiot.ui.device_detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.fingerprintiot.data.Models.HistoryModel;
import com.elsawy.ahmed.fingerprintiot.R;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryAdapter extends RecyclerView.Adapter<UserHistoryViewHolder> {

    private String TAG = "HistoryAdapter";
    private ArrayList<HistoryModel> historyModelList = new ArrayList<>();

    @NonNull
    @Override
    public UserHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserHistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserHistoryViewHolder holder, int position) {
        final HistoryModel currentUser = HistoryAdapter.this.historyModelList.get(position);
        holder.bindToUserHistory(currentUser);
    }

    @Override
    public int getItemCount() {
        return historyModelList.size();
    }

    public void setHistoryModelList(ArrayList<HistoryModel> historyModelList) {
        this.historyModelList = historyModelList;
        Collections.sort(this.historyModelList,Collections.reverseOrder());
        HistoryAdapter.this.notifyDataSetChanged();
    }

}
