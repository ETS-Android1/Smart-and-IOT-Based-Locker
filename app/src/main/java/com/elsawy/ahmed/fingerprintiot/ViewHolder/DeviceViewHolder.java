package com.elsawy.ahmed.fingerprintiot.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.fingerprintiot.Models.Device;
import com.elsawy.ahmed.fingerprintiot.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.device_name)
    TextView deviceNameTV;
    @BindView(R.id.device_state)
    TextView deviceStateTV;
    @BindView(R.id.device_power_btn)
    ImageView devicePowerBtn;
    @BindView(R.id.item_device_card_view)
    CardView deviceCardView;

    public DeviceViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

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
