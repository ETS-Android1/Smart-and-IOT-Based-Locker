package com.elsawy.ahmed.fingerprintiot.ViewHolder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.fingerprintiot.Models.HistoryModel;
import com.elsawy.ahmed.fingerprintiot.R;
import com.elsawy.ahmed.fingerprintiot.Utilities;

import java.util.Date;

import butterknife.BindView;
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
        Date date = new Date(currentUser.getTimestamp());
//        Calendar calendar = newStateTV
        Log.i("Date", date.toString());

        usernameTV.setText(currentUser.getUsername());
        newStateTV.setText(currentUser.getNewState());
        timeTV.setText(Utilities.getTime(currentUser.getTimestamp()));
        dateTV.setText(Utilities.getDate(currentUser.getTimestamp()));
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
