package com.elsawy.ahmed.fingerprintiot.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.elsawy.ahmed.fingerprintiot.Adapters.HistoryAdapter;
import com.elsawy.ahmed.fingerprintiot.Models.Device;
import com.elsawy.ahmed.fingerprintiot.R;
import com.elsawy.ahmed.fingerprintiot.VerticalSpaceItemDecoration;

public class DeviceDetailActivity extends AppCompatActivity {

    private TextView stateTV,nameTV,typeTV,keyTV;
    private RecyclerView usersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);

        Device currentDevice = (Device) getIntent().getParcelableExtra("deviceInfo");

        stateTV = (TextView) findViewById(R.id.device_detail_state);
        nameTV = (TextView) findViewById(R.id.device_detail_name);
        keyTV = (TextView) findViewById(R.id.device_detail_key);
        typeTV = (TextView) findViewById(R.id.device_detail_type);
        setDeviceInfo(currentDevice);

        HistoryAdapter historyAdapter = new HistoryAdapter(DeviceDetailActivity.this,currentDevice.key);

        usersRecyclerView = findViewById(R.id.users_history_recycler_view);
        usersRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(15));
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(DeviceDetailActivity.this));
        usersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        usersRecyclerView.setAdapter(historyAdapter);


    }

    private void setDeviceInfo(Device currentDevice) {
        stateTV.setText("State: " + currentDevice.state);
        nameTV.setText(currentDevice.name);
        keyTV.setText( "Key: " + currentDevice.key);
        typeTV.setText("Type: " + currentDevice.type);
    }
}
