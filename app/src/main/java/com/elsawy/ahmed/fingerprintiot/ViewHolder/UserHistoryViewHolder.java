package com.elsawy.ahmed.fingerprintiot.ViewHolder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.fingerprintiot.Models.UserHistory;
import com.elsawy.ahmed.fingerprintiot.R;
import com.elsawy.ahmed.fingerprintiot.Utilities;

import java.util.Calendar;
import java.util.Date;

public class UserHistoryViewHolder extends RecyclerView.ViewHolder {

    private TextView usernameTV, newStateTV,timeTV,dateTV,changedWayTV;

    public UserHistoryViewHolder(@NonNull View itemView) {
        super(itemView);

        usernameTV = (TextView) itemView.findViewById(R.id.history_username);
        newStateTV = (TextView) itemView.findViewById(R.id.history_new_state);
        timeTV = (TextView) itemView.findViewById(R.id.history_time);
        dateTV = (TextView) itemView.findViewById(R.id.history_date);
        changedWayTV = (TextView) itemView.findViewById(R.id.history_changed_way);
    }

    public void bindToUserHistory(UserHistory currentUser){
        Date date=new Date(currentUser.timestamp);
//        Calendar calendar = newStateTV
        Log.i("Date",date.toString());

        usernameTV.setText(currentUser.username);
        newStateTV.setText(currentUser.newState);
        timeTV.setText(Utilities.getTime(currentUser.timestamp));
        dateTV.setText(Utilities.getDate(currentUser.timestamp));
        changedWayTV.setText(currentUser.changedWay);
    }

}
