package com.elsawy.ahmed.fingerprintiot.ViewHolder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.fingerprintiot.Models.UserHistory;
import com.elsawy.ahmed.fingerprintiot.R;
import com.elsawy.ahmed.fingerprintiot.Utilities;

import java.util.Date;

public class UserHistoryViewHolder extends RecyclerView.ViewHolder {

    private TextView usernameTV, newStateTV, timeTV, dateTV;
    private ImageView historyChangedWayImage;

    public UserHistoryViewHolder(@NonNull View itemView) {
        super(itemView);

        usernameTV = (TextView) itemView.findViewById(R.id.history_username);
        newStateTV = (TextView) itemView.findViewById(R.id.history_new_state);
        timeTV = (TextView) itemView.findViewById(R.id.history_time);
        dateTV = (TextView) itemView.findViewById(R.id.history_date);
        historyChangedWayImage = (ImageView) itemView.findViewById(R.id.history_image);
    }

    public void bindToUserHistory(UserHistory currentUser) {
        Date date = new Date(currentUser.timestamp);
//        Calendar calendar = newStateTV
        Log.i("Date", date.toString());

        usernameTV.setText(currentUser.username);
        newStateTV.setText(currentUser.newState);
        timeTV.setText(Utilities.getTime(currentUser.timestamp));
        dateTV.setText(Utilities.getDate(currentUser.timestamp));
        putChangedWayImage(currentUser.changedWay);
    }

    private void putChangedWayImage(String changedWay) {
        if (changedWay.equals("mobile")) {
            historyChangedWayImage.setImageResource(R.drawable.ic_phone);
        } else {
            historyChangedWayImage.setImageResource(R.drawable.ic_fingerprint);
        }
    }

}
