package com.elsawy.ahmed.fingerprintiot.ui.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.fingerprintiot.data.Models.DeviceModel;
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

    public void bindToDevice(DeviceModel currentDeviceModel, View.OnClickListener cardViewListener, View.OnClickListener powerButtonListener) {

        deviceNameTV.setText(currentDeviceModel.getName());
        deviceStateTV.setText("State : " + currentDeviceModel.getState());
        deviceCardView.setOnClickListener(cardViewListener);
        devicePowerBtn.setOnClickListener(powerButtonListener);
//        deviceStateTV.setOnClickListener(powerButtonListener);
        putPowerButtonColor(currentDeviceModel.getState());

    }

    public void putPowerButtonColor(String state){
        if (state.equals("ON")){
            devicePowerBtn.setImageResource(R.drawable.ic_power_orange_24dp);
        }else if(state.equals("OFF")){
            devicePowerBtn.setImageResource(R.drawable.ic_power_gray_24dp);
        }
    }

}
