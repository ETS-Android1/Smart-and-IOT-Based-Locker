package com.elsawy.ahmed.fingerprintiot.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.fingerprintiot.Activities.DeviceDetailActivity;
import com.elsawy.ahmed.fingerprintiot.Models.Device;
import com.elsawy.ahmed.fingerprintiot.Models.RecyclerViewItemCount;
import com.elsawy.ahmed.fingerprintiot.Models.SharedPrefManager;
import com.elsawy.ahmed.fingerprintiot.Models.UserHistory;
import com.elsawy.ahmed.fingerprintiot.R;
import com.elsawy.ahmed.fingerprintiot.ViewHolder.DeviceViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeviceAdapter  extends RecyclerView.Adapter<DeviceViewHolder> {

    private String TAG = "DeviceAdapter";
    private Context mContext;

    private FirebaseAuth mAuth;
    private FirebaseUser userData;
    private DatabaseReference ref;
    private ArrayList<Device> deviceList;
    private RecyclerViewItemCount recyclerViewItemCount;

    public DeviceAdapter(Context mContext, RecyclerViewItemCount recyclerViewItemCount) {
        this.mContext = mContext;
        this.recyclerViewItemCount = recyclerViewItemCount;

        mAuth = FirebaseAuth.getInstance();
        userData = this.mAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        ref = firebaseDatabase.getReference();
        deviceList = new ArrayList<>();

        if (this.userData != null) {
            DeviceAdapter.this.ref.child("userDevices").child(DeviceAdapter.this.userData.getUid()).addValueEventListener(new DeviceListener());
            DeviceAdapter.this.ref.child("userDevices").child(DeviceAdapter.this.userData.getUid()).keepSynced(true);
        }

    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeviceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {

        final Device currentDevice = DeviceAdapter.this.deviceList.get(position);
        View.OnClickListener cardViewListener = view -> {
            Intent intent = new Intent(DeviceAdapter.this.mContext, DeviceDetailActivity.class);
            intent.putExtra("deviceInfo", currentDevice);
            DeviceAdapter.this.mContext.startActivity(intent);
        };

        View.OnClickListener powerButtonListener = view -> {
            UserHistory userHistory = new UserHistory();
            
            userHistory.setUsername(SharedPrefManager.getInstance(mContext).getUsername());
            userHistory.setTimestamp(System.currentTimeMillis() / 1000);
            userHistory.setChangedWay("mobile");

            if (currentDevice.getState().equals("ON")) {
                userHistory.setNewState("OFF");
                DeviceAdapter.this.ref.child("Devices").child(currentDevice.getKey()).child("state").setValue("OFF");
            } else if (currentDevice.getState().equals("OFF")) {
                userHistory.setNewState("ON");
                DeviceAdapter.this.ref.child("Devices").child(currentDevice.getKey()).child("state").setValue("ON");
            }
            holder.putPowerButtonColor(userHistory.getNewState());
            DeviceAdapter.this.ref.child("devicesHistory").child(currentDevice.getKey()).push().setValue(userHistory);

        };

        holder.bindToDevice(currentDevice, cardViewListener, powerButtonListener);

    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    class DeviceListener implements ValueEventListener {
        DeviceListener() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {

            if (dataSnapshot.getValue() != null) {
                deviceList.clear();

                for (DataSnapshot userDeviceSnapshot : dataSnapshot.getChildren()) {
                    String key = userDeviceSnapshot.getKey();

                    Device newDevice = new Device();

                    String deviceName = userDeviceSnapshot.getChildren().iterator().next().getValue().toString();

                    newDevice.setName(deviceName);
                    newDevice.setKey(key);

                    DeviceAdapter.this.ref.child("Devices").child(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getValue() != null) {
                                Log.i(TAG, dataSnapshot.toString());

                                Device changedDevice = dataSnapshot.getValue(Device.class);
                                int deviceIndex = deviceList.indexOf(changedDevice);
                                if (deviceIndex == -1) {
                                    newDevice.setDevice(changedDevice);
                                    deviceList.add(newDevice);
                                } else {
                                    deviceList.get(deviceIndex).setState(changedDevice.getState());
                                }
                            }
                            DeviceAdapter.this.notifyDataSetChanged();
                            recyclerViewItemCount.setRecyclerViewCount(deviceList.size());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    }

}

