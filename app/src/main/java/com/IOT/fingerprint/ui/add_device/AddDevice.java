package com.IOT.fingerprint.ui.add_device;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.IOT.fingerprint.data.SharedPrefManager;
import com.IOT.fingerprint.R;
import com.IOT.fingerprint.data.database.DeviceFirebaseInsertUpdateDB;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddDevice extends AppCompatActivity {

    @BindView(R.id.device_name_et)
    EditText deviceNameET;
    @BindView(R.id.user_id_et)
    EditText userIdET;
    @BindView(R.id.device_type_et)
    EditText deviceTypeET;
    @BindView(R.id.device_key_et)
    EditText deviceKeyET;
    @BindView(R.id.device_phone_et)
    EditText devicePhoneET;
    @BindView(R.id.device_key_tv)
    TextView deviceKeyTV;
    @BindView(R.id.device_phone_tv)
    TextView devicePhoneTV;
    @BindView(R.id.new_device_radio)
    RadioButton newDeviceRadio;
    @BindView(R.id.exist_device_radio)
    RadioButton existDeviceRadio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        ButterKnife.bind(this);
        setupToolbar();

    }

    @OnClick(R.id.add_device_btn)
    public void addDeviceBtn() {
        String deviceName = deviceNameET.getText().toString();
        String userID = userIdET.getText().toString();
        String deviceType = deviceTypeET.getText().toString();
        String devicePhone = devicePhoneET.getText().toString().trim();
        String key = deviceKeyET.getText().toString().trim();
        boolean isNewDevice = newDeviceRadio.isChecked();

        if (isValidData(deviceName, userID, devicePhone, key, isNewDevice)) {
            String myUsername = SharedPrefManager.getInstance(getApplicationContext()).getUsername();
            DeviceFirebaseInsertUpdateDB.insertNewDevice(myUsername, deviceName, userID, deviceType, devicePhone, isNewDevice, key);
            finish();
        }
    }

    private boolean isValidData(String deviceName,String userID, String devicePhone, String key, boolean isNewDevice) {
        boolean valid = true;
        if (deviceName.length() < 2) {
            deviceNameET.setError("At least two characters");
            valid = false;
        }
        if (userID.length() < 1) {
            userIdET.setError("required");
            valid = false;
        }
        if (isNewDevice && devicePhone.length() != 11) {
            devicePhoneET.setError("enter a valid phone");
            valid = false;
        }
        if (!isNewDevice && key.length() <= 15) {
            devicePhoneET.setError("enter a valid key");
            valid = false;
        }
        return valid;
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.add_device_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
    }

    public void onNewDeviceRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.new_device_radio:
                if (checked) {
                    deviceKeyET.setVisibility(View.GONE);
                    deviceKeyTV.setVisibility(View.GONE);
                    devicePhoneET.setVisibility(View.VISIBLE);
                    devicePhoneTV.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.exist_device_radio:
                if (checked) {
                    deviceKeyET.setVisibility(View.VISIBLE);
                    deviceKeyTV.setVisibility(View.VISIBLE);
                    devicePhoneET.setVisibility(View.GONE);
                    devicePhoneTV.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}