package com.elsawy.ahmed.fingerprintiot.ui.device_detail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.fingerprintiot.data.Models.HistoryModel;
import com.elsawy.ahmed.fingerprintiot.R;
import com.elsawy.ahmed.fingerprintiot.utils.TimeHandle;

import butterknife.BindView;    //    @PropertyName("username")

import butterknife.ButterKnife;

public class UserHistoryViewHolder extends RecyclerView.ViewHolder {

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
        //TODO change remove the condition and else
//        if (currentUser.getUsername()!= null)
        usernameTV.setText(currentUser.getUsername());
//        else
//        usernameTV.setText(currentUser.getId());
        newStateTV.setText(currentUser.getNewState());
        timeTV.setText(TimeHandle.getTime(currentUser.getTimestamp()));
        dateTV.setText(TimeHandle.getDate(currentUser.getTimestamp()));
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
