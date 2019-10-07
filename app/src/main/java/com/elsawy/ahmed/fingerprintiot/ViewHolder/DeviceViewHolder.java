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
    private View deviceCicleView;
    private CardView deviceCardView;

    public DeviceViewHolder(@NonNull View itemView) {
        super(itemView);
        deviceNameTV = (TextView) itemView.findViewById(R.id.device_name);
        deviceStateTV = (TextView) itemView.findViewById(R.id.device_state);
        devicePowerBtn = (ImageView) itemView.findViewById(R.id.device_power_btn);
        deviceCicleView = (View) itemView.findViewById(R.id.device_circle);
        deviceCardView = (CardView) itemView.findViewById(R.id.item_device_card_view);

    }

    public void bindToDevice(Device currentDevice, View.OnClickListener cardViewListener, View.OnClickListener powerButtonListener) {

        deviceNameTV.setText(currentDevice.name);
        deviceStateTV.setText("State : " + currentDevice.state);
        deviceCardView.setOnClickListener(cardViewListener);
        devicePowerBtn.setOnClickListener(powerButtonListener);
//        deviceStateTV.setOnClickListener(powerButtonListener);
        putCircleColor(currentDevice.state);

    }

    public void putCircleColor(String state){
        if (state.equals("ON")){
            deviceCicleView.setBackgroundResource(R.drawable.green_circle);
        }else if(state.equals("OFF")){
            deviceCicleView.setBackgroundResource(R.drawable.yellow_circle);
        }
    }

}
