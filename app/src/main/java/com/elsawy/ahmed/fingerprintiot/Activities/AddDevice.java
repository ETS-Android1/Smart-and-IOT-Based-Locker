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

import com.elsawy.ahmed.fingerprintiot.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddDevice extends AppCompatActivity {


    private EditText deviceNameET, deviceTypeET, deviceKeyET;
    private Button addDeviceBtn;
    private RadioButton newDeviceRadio, existDeviceRadio;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        setupToolbar();

        deviceNameET =  findViewById(R.id.device_name_et);
        deviceTypeET = findViewById(R.id.device_type_et);
        deviceKeyET = findViewById(R.id.device_key);
        addDeviceBtn = findViewById(R.id.add_device_btn);
        newDeviceRadio = findViewById(R.id.new_device_radio);
        existDeviceRadio = findViewById(R.id.exist_device_radio);

        mAuth = FirebaseAuth.getInstance();

        addDeviceBtn.setOnClickListener(view -> saveDeviceData());

    }

    private void setupToolbar(){
        Toolbar toolbar = findViewById(R.id.add_device_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
//        getSupportActionBar().setTitle("");
    }

    private void saveDeviceData() {

        String deviceName = deviceNameET.getText().toString();
        String deviceType = deviceTypeET.getText().toString();

        FirebaseUser user = AddDevice.this.mAuth.getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String key;
        if (existDeviceRadio.isChecked()) {
            key = deviceKeyET.getText().toString();
        } else {
            key = reference.child("Devices").push().getKey();
        }

        Map<String, String> deviceInfo = new HashMap<>();
//        deviceInfo.put("name", name);
        deviceInfo.put("type", deviceType);
        deviceInfo.put("key", key);
        deviceInfo.put("state", "OFF");

//        Map<String, String> userDevice = new HashMap<>();
//        userDevice.put("name", deviceName);
//        userDevice.put("key", key);

        reference.child("userDevices").child(user.getUid()).child(key).child("name").setValue(deviceName);

        if (newDeviceRadio.isChecked()) {
            reference.child("Devices").child(key).setValue(deviceInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    finish();
                }
            });
        }

    }

    public void onNewDeviceRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.new_device_radio:
                if (checked)
                    deviceKeyET.setVisibility(View.GONE);
                break;
            case R.id.exist_device_radio:
                if (checked)
                    deviceKeyET.setVisibility(View.VISIBLE);
                break;
        }
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