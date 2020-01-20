package com.elsawy.ahmed.fingerprintiot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elsawy.ahmed.fingerprintiot.Activities.AddDevice;
import com.elsawy.ahmed.fingerprintiot.Activities.LoginActivity;
import com.elsawy.ahmed.fingerprintiot.Activities.SignupActivity;
import com.elsawy.ahmed.fingerprintiot.Adapters.DeviceAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private RecyclerView devicesRecyclerView;
    private FloatingActionButton addDeviceFab;

    private Toolbar toolbar;
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

        setupToolbar();

        addDeviceFab = findViewById(R.id.add_device_fab);
        addDeviceFab.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddDevice.class)));

        DeviceAdapter deviceAdapter = new DeviceAdapter(MainActivity.this);

        devicesRecyclerView = findViewById(R.id.devices_recycler_view);
        devicesRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));
        devicesRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        devicesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        devicesRecyclerView.setAdapter(deviceAdapter);
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.sign_up_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_device) {
            startActivity(new Intent(MainActivity.this, AddDevice.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}