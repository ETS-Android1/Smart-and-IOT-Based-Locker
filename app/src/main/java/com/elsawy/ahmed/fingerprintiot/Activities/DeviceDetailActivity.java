package com.elsawy.ahmed.fingerprintiot.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.elsawy.ahmed.fingerprintiot.Adapters.HistoryAdapter;
import com.elsawy.ahmed.fingerprintiot.Models.Device;
import com.elsawy.ahmed.fingerprintiot.R;
import com.elsawy.ahmed.fingerprintiot.VerticalSpaceItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceDetailActivity extends AppCompatActivity {

    @BindView(R.id.device_detail_state)
    TextView stateTV;
    @BindView(R.id.device_detail_name)
    TextView nameTV;
    @BindView(R.id.device_detail_type)
    TextView typeTV;
    @BindView(R.id.device_detail_key)
    TextView keyTV;
    @BindView(R.id.users_history_recycler_view)
    RecyclerView usersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);

        ButterKnife.bind(this);
        setupToolbar();

        Device currentDevice = (Device) getIntent().getParcelableExtra("deviceInfo");

        setDeviceInfo(currentDevice);

        HistoryAdapter historyAdapter = new HistoryAdapter(DeviceDetailActivity.this,currentDevice.getKey());

        usersRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(15));
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(DeviceDetailActivity.this));
        usersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        usersRecyclerView.setAdapter(historyAdapter);

    }

    private void setDeviceInfo(Device currentDevice) {
        stateTV.setText("State: " + currentDevice.getState());
        nameTV.setText(currentDevice.getName());
        keyTV.setText( "Key: " + currentDevice.getKey());
        typeTV.setText("Type: " + currentDevice.getType());
    }

    private void setupToolbar(){
        Toolbar toolbar = findViewById(R.id.device_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setTitle("");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
