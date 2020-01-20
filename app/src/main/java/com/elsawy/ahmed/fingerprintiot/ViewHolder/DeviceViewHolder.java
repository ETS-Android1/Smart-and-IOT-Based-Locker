package com.elsawy.ahmed.fingerprintiot.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.fingerprintiot.Models.Device;
import com.elsawy.ahmed.fingerprintiot.R;

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    private TextView deviceNameTV,deviceStateTV;
    private ImageView devicePowerBtn;
    private CardView deviceCardView;

    public DeviceViewHolder(@NonNull View itemView) {
        super(itemView);
        deviceNameTV = itemView.findViewById(R.id.device_name);
        deviceStateTV = itemView.findViewById(R.id.device_state);
        devicePowerBtn = itemView.findViewById(R.id.device_power_btn);
        deviceCardView = itemView.findViewById(R.id.item_device_card_view);

    }

    public void bindToDevice(Device currentDevice, View.OnClickListener cardViewListener, View.OnClickListener powerButtonListener) {

        deviceNameTV.setText(currentDevice.name);
        deviceStateTV.setText("State : " + currentDevice.state);
        deviceCardView.setOnClickListener(cardViewListener);
        devicePowerBtn.setOnClickListener(powerButtonListener);
//        deviceStateTV.setOnClickListener(powerButtonListener);
        putPowerButtonColor(currentDevice.state);

    }

    public void putPowerButtonColor(String state){
        if (state.equals("ON")){
            devicePowerBtn.setImageResource(R.drawable.ic_power_orange_24dp);
        }else if(state.equals("OFF")){
            devicePowerBtn.setImageResource(R.drawable.ic_power_gray_24dp);
        }
    }

}
