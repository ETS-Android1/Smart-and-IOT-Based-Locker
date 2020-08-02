package com.elsawy.ahmed.fingerprintiot.ui.device_detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.fingerprintiot.data.Models.HistoryModel;
import com.elsawy.ahmed.fingerprintiot.R;
import com.elsawy.ahmed.fingerprintiot.utils.TimeHandle;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.UserHistoryViewHolder> {

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

    void setHistoryModelList(ArrayList<HistoryModel> historyModelList) {
        this.historyModelList = historyModelList;
        HistoryAdapter.this.notifyDataSetChanged();
    }
    class UserHistoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.history_username)
        TextView usernameTV;
        @BindView(R.id.history_new_state)
        TextView newStateTV;
        @BindView(R.id.history_time)
        TextView timeTV;
        @BindView(R.id.history_date)
        TextView dateTV;
        @BindView(R.id.history_image)
        ImageView historyChangedWayImage;

        public UserHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindToUserHistory(HistoryModel currentUser) {
            usernameTV.setText(currentUser.getUsername());
            newStateTV.setText(currentUser.getNewState());
            timeTV.setText(TimeHandle.convertTimestampToTime(currentUser.getTimestamp()));
            dateTV.setText(TimeHandle.convertTimestampToDate(currentUser.getTimestamp()));
            putChangedWayImage(currentUser.getChangedWay());
        }

        private void putChangedWayImage(String changedWay) {
            if (changedWay.equals("mobile")) {
                historyChangedWayImage.setImageResource(R.drawable.ic_phone);
            } else {
                historyChangedWayImage.setImageResource(R.drawable.ic_fingerprint);
            }
        }

    }
}
