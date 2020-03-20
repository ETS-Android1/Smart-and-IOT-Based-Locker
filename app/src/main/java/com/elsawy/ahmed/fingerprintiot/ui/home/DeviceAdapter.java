package com.elsawy.ahmed.fingerprintiot.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.fingerprintiot.ui.device_detail.DeviceDetailActivity;
import com.elsawy.ahmed.fingerprintiot.data.Models.DeviceModel;
import com.elsawy.ahmed.fingerprintiot.R;
import com.elsawy.ahmed.fingerprintiot.data.database.DeviceFirebaseDataBase;

import java.util.ArrayList;

public class DeviceAdapter  extends RecyclerView.Adapter<DeviceViewHolder> {

    private final String TAG = "DeviceAdapter";
    private final String ON = "ON";
    private final String OFF = "OFF";
    private Context mContext;
    private ArrayList<DeviceModel> deviceModelList;

    public DeviceAdapter(Context mContext) {
        this.mContext = mContext;
        deviceModelList = new ArrayList<>();
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeviceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {

        final DeviceModel currentDeviceModel = DeviceAdapter.this.deviceModelList.get(position);
        View.OnClickListener cardViewListener = view -> {
            Intent intent = new Intent(DeviceAdapter.this.mContext, DeviceDetailActivity.class);
            intent.putExtra("deviceInfo", currentDeviceModel);
            DeviceAdapter.this.mContext.startActivity(intent);
        };

        View.OnClickListener powerButtonListener = view -> {
            if (currentDeviceModel.getState().equals(ON)) {
                holder.putPowerButtonColor(OFF);
                DeviceFirebaseDataBase.updateDeviceState(mContext, currentDeviceModel.getKey(), currentDeviceModel.getUserID(), OFF);
            } else if (currentDeviceModel.getState().equals(OFF)) {
                holder.putPowerButtonColor(ON);
                DeviceFirebaseDataBase.updateDeviceState(mContext, currentDeviceModel.getKey(),currentDeviceModel.getUserID(), ON);
            }
        };

        holder.bindToDevice(currentDeviceModel, cardViewListener, powerButtonListener);
    }

    public void setDeviceModelList(ArrayList<DeviceModel> deviceModelList) {
        this.deviceModelList = deviceModelList;
        DeviceAdapter.this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return deviceModelList.size();
    }


}