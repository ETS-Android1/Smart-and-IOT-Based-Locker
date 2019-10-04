package com.elsawy.ahmed.fingerprintiot.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.fingerprintiot.Models.Device;
import com.elsawy.ahmed.fingerprintiot.R;

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    private TextView deviceNameTV,deviceStateTV;

    public DeviceViewHolder(@NonNull View itemView) {
        super(itemView);
        deviceNameTV = (TextView) itemView.findViewById(R.id.device_name);
        deviceStateTV = (TextView) itemView.findViewById(R.id.device_state);

    }

    public void bindToDevice(Device currentDevice) {

        deviceNameTV.setText(currentDevice.name);
        deviceStateTV.setText(currentDevice.state);

    }

}
