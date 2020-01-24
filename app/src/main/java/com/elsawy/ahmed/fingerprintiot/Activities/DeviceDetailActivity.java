package com.elsawy.ahmed.fingerprintiot.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elsawy.ahmed.fingerprintiot.Adapters.DeviceAdapter;
import com.elsawy.ahmed.fingerprintiot.Adapters.HistoryAdapter;
import com.elsawy.ahmed.fingerprintiot.Models.Device;
import com.elsawy.ahmed.fingerprintiot.Models.SharedPrefManager;
import com.elsawy.ahmed.fingerprintiot.Models.UserHistory;
import com.elsawy.ahmed.fingerprintiot.R;
import com.elsawy.ahmed.fingerprintiot.VerticalSpaceItemDecoration;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceDetailActivity extends AppCompatActivity {

    private Device currentDevice;

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
    @BindView(R.id.history_image_view_background)
    ImageView historyImageViewBackground;
    @BindView(R.id.device_detail_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);

        ButterKnife.bind(this);
        setupToolbar();

        currentDevice = (Device) getIntent().getParcelableExtra("deviceInfo");
        setDeviceInfo(currentDevice);
        setupRecyclerView(currentDevice.getKey());

    }

    private void setDeviceInfo(Device currentDevice) {
        stateTV.setText("State: " + currentDevice.getState());
        nameTV.setText(currentDevice.getName());
        keyTV.setText( "Key: " + currentDevice.getKey());
        typeTV.setText("Type: " + currentDevice.getType());
    }

    private void setupRecyclerView(String key){
        HistoryAdapter historyAdapter = new HistoryAdapter(key, count -> {
            if (count > 0) {
                historyImageViewBackground.setVisibility(View.GONE);
            } else {
                historyImageViewBackground.setVisibility(View.VISIBLE);
            }
        });

        usersRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(15));
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(DeviceDetailActivity.this));
        usersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        usersRecyclerView.setAdapter(historyAdapter);
    }

    private void setupToolbar(){
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

    @OnClick(R.id.message_fab)
    public void handleMessageBtn(){
        Toast.makeText(this,"handleMessageBtn",Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.power_fab)
    public void handlePowerBtn(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        UserHistory userHistory = new UserHistory();

        userHistory.setUsername(SharedPrefManager.getInstance(this).getUsername());
        userHistory.setTimestamp(System.currentTimeMillis() / 1000);
        userHistory.setChangedWay("mobile");

        if (currentDevice.getState().equals("ON")) {
            userHistory.setNewState("OFF");
            ref.child("Devices").child(currentDevice.getKey()).child("state").setValue("OFF");
            currentDevice.setState("OFF");
        } else if (currentDevice.getState().equals("OFF")) {
            userHistory.setNewState("ON");
            ref.child("Devices").child(currentDevice.getKey()).child("state").setValue("ON");
            currentDevice.setState("ON");
        }
        ref.child("devicesHistory").child(currentDevice.getKey()).push().setValue(userHistory);
        stateTV.setText("State: " + currentDevice.getState());
    }

}
