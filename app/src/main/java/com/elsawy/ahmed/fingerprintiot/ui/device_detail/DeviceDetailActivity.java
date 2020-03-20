package com.elsawy.ahmed.fingerprintiot.ui.device_detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elsawy.ahmed.fingerprintiot.data.Models.DeviceModel;
import com.elsawy.ahmed.fingerprintiot.data.Models.HistoryModel;
import com.elsawy.ahmed.fingerprintiot.R;
import com.elsawy.ahmed.fingerprintiot.utils.VerticalSpaceItemDecoration;
import com.elsawy.ahmed.fingerprintiot.data.database.DeviceFirebaseDataBase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceDetailActivity extends AppCompatActivity {

    private DeviceModel currentDeviceModel;
    private final String ON = "ON";
    private final String OFF = "OFF";

    @BindView(R.id.device_detail_state)
    TextView stateTV;
    @BindView(R.id.device_detail_name)
    TextView nameTV;
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

        currentDeviceModel = (DeviceModel) getIntent().getParcelableExtra("deviceInfo");
        setDeviceInfo(currentDeviceModel);
        setupRecyclerView(currentDeviceModel.getKey());

    }

    private void setDeviceInfo(DeviceModel currentDeviceModel) {
        stateTV.setText("State: " + currentDeviceModel.getState());
        nameTV.setText(currentDeviceModel.getName());
        keyTV.setText( "Key: " + currentDeviceModel.getKey());
    }

    private void setupRecyclerView(String deviceKey) {
        HistoryAdapter historyAdapter = new HistoryAdapter();

        usersRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(15));
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(DeviceDetailActivity.this));
        usersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        usersRecyclerView.setAdapter(historyAdapter);


        HistoryViewModel historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        LiveData<ArrayList<HistoryModel>> liveData = historyViewModel.getHistoryListLiveData(deviceKey);
        liveData.observe(this, historyModelList -> {
            historyAdapter.setHistoryModelList(historyModelList);
            if (historyModelList.size() > 0) {
                historyImageViewBackground.setVisibility(View.GONE);
            } else {
                historyImageViewBackground.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setTitle("");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.message_fab)
    public void handleMessageBtn(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.send_sms_dialogs);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(false);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final Button onBtn = dialog.findViewById(R.id.on_sms_btn);
        final Button offBtn = dialog.findViewById(R.id.off_sms_btn);

        onBtn.setOnClickListener(v -> {sendSMS(ON);dialog.dismiss();});
        offBtn.setOnClickListener(v -> {sendSMS(OFF);dialog.dismiss();});

        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
    }

    @OnClick(R.id.power_fab)
    public void handlePowerBtn() {

        if (currentDeviceModel.getState().equals(ON)) {
            DeviceFirebaseDataBase.updateDeviceState(this, currentDeviceModel.getKey(),currentDeviceModel.getUserID(), OFF);
            currentDeviceModel.setState(OFF);
        } else if (currentDeviceModel.getState().equals(OFF)) {
            DeviceFirebaseDataBase.updateDeviceState(this, currentDeviceModel.getKey(),currentDeviceModel.getUserID(), ON);
            currentDeviceModel.setState(ON);
        }
        stateTV.setText("State: " + currentDeviceModel.getState());

    }

    private void sendSMS(String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(currentDeviceModel.getPhoneNumber(), null, message, null, null);
        Log.i("Finished sending SMS...", "");
    }

}
