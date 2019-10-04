package com.elsawy.ahmed.fingerprintiot.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.ahmed.fingerprintiot.Models.Device;
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


    public DeviceAdapter(Context mContext) {
        this.mContext = mContext;
        mAuth = FirebaseAuth.getInstance();
        userData = this.mAuth.getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference();
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
        holder.bindToDevice(currentDevice);

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

                    newDevice.name = deviceName;
                    newDevice.key = key;

                    Log.i(TAG, deviceName);

                    DeviceAdapter.this.ref.child("Devices").child(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getValue() != null) {

                                Device changedDevice = dataSnapshot.getValue(Device.class);
                                int deviceIndex = deviceList.indexOf(changedDevice);
                                if (deviceIndex == -1) {
                                    newDevice.setDevice(changedDevice);
                                    deviceList.add(newDevice);
                                } else {
                                    deviceList.get(deviceIndex).state = changedDevice.state;
                                }
                            }
                            DeviceAdapter.this.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
//            DeviceAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    }
}