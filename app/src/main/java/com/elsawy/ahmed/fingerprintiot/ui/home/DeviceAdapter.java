package com.elsawy.ahmed.fingerprintiot.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.fingerprintiot.ui.device_detail.DeviceDetailActivity;
import com.elsawy.ahmed.fingerprintiot.data.Models.DeviceModel;
import com.elsawy.ahmed.fingerprintiot.R;
import com.elsawy.ahmed.fingerprintiot.data.database.InsertUpdateDeviceFirebaseDB;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceAdapter  extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

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
                InsertUpdateDeviceFirebaseDB.updateDeviceState(mContext, currentDeviceModel.getKey(), currentDeviceModel.getUserID(), OFF);
            } else if (currentDeviceModel.getState().equals(OFF)) {
                holder.putPowerButtonColor(ON);
                InsertUpdateDeviceFirebaseDB.updateDeviceState(mContext, currentDeviceModel.getKey(),currentDeviceModel.getUserID(), ON);
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

    class DeviceViewHolder extends RecyclerView.ViewHolder {

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
}