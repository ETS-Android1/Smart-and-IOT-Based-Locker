package com.elsawy.ahmed.fingerprintiot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.elsawy.ahmed.fingerprintiot.Activities.AddDevice;
import com.elsawy.ahmed.fingerprintiot.Activities.LoginActivity;
import com.elsawy.ahmed.fingerprintiot.Activities.SignupActivity;
import com.elsawy.ahmed.fingerprintiot.Adapters.DeviceAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private RecyclerView devicesRecyclerView;

    private FirebaseAuth mAuth;


    @Override
    public void onStart() {
        super.onStart();

        this.mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            Log.d(TAG, "You are not SignIn");
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SignupActivity.class)));

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));

        Button newDevice = (Button) findViewById(R.id.new_device);
        newDevice.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddDevice.class)));


        DeviceAdapter deviceAdapter = new DeviceAdapter(MainActivity.this);

        devicesRecyclerView = findViewById(R.id.devices_recycler_view);
//        devicesRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(10));
        devicesRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        devicesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        devicesRecyclerView.setAdapter(deviceAdapter);

    }
}
