package com.elsawy.ahmed.fingerprintiot.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.elsawy.ahmed.fingerprintiot.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddDevice extends AppCompatActivity {

    @BindView(R.id.device_name_et)
    EditText deviceNameET;
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
//    @BindView(R.id.add_device_btn)
//    Button addDeviceBtn;
    @BindView(R.id.new_device_radio)
    RadioButton newDeviceRadio;
    @BindView(R.id.exist_device_radio)
    RadioButton existDeviceRadio;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        ButterKnife.bind(this);
        setupToolbar();

        mAuth = FirebaseAuth.getInstance();

    }

    @OnClick(R.id.add_device_btn)
    public void addDeviceBtn(){
        String deviceName = deviceNameET.getText().toString();
        String deviceType = deviceTypeET.getText().toString();
        String devicePhone = devicePhoneET.getText().toString();

        if (isValid(deviceName, devicePhone))
            saveDeviceData(deviceName, deviceType, devicePhone);
    }

    private boolean isValid(String deviceName, String devicePhone) {
        boolean valid = true;
        if (deviceName.length() <= 0) {
            deviceNameET.setError("required");
            valid = false;
        }

        if (devicePhone.length() != 11) {
            devicePhoneET.setError("enter a valid phone");
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

    private void saveDeviceData(String deviceName, String deviceType, String devicePhone) {

        FirebaseUser user = AddDevice.this.mAuth.getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String key;
        if (existDeviceRadio.isChecked()) {
            key = deviceKeyET.getText().toString();
        } else {
            key = reference.child("Devices").push().getKey();
        }

        Map<String, String> deviceInfo = new HashMap<>();
        deviceInfo.put("type", deviceType);
        deviceInfo.put("phoneNumber", devicePhone);
        deviceInfo.put("key", key);
        deviceInfo.put("state", "OFF");

        reference.child("userDevices").child(user.getUid()).child(key).child("name").setValue(deviceName);

        if (newDeviceRadio.isChecked()) {
            reference.child("Devices").child(key).setValue(deviceInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    finish();
                }
            });
        } else {
            finish();
        }

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
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}