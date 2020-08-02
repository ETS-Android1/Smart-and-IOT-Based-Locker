package com.elsawy.ahmed.fingerprintiot.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elsawy.ahmed.fingerprintiot.R;
import com.elsawy.ahmed.fingerprintiot.data.database.DeviceFirebaseQueryLiveData;
import com.elsawy.ahmed.fingerprintiot.utils.VerticalSpaceItemDecoration;
import com.elsawy.ahmed.fingerprintiot.ui.add_device.AddDevice;
import com.elsawy.ahmed.fingerprintiot.authentication.LoginActivity;
import com.elsawy.ahmed.fingerprintiot.data.Models.DeviceModel;
import com.elsawy.ahmed.fingerprintiot.data.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.devices_recycler_view)
    RecyclerView devicesRecyclerView;
    @BindView(R.id.add_device_fab)
    FloatingActionButton addDeviceFab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.sign_up_toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_activity_background)
    ImageView RecyclerViewImageBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        putNavigationHeader();
        setupToolbar();
        setupDrawerLayout();
        setupRecyclerView();
        addDeviceFab.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddDevice.class)));
    }

    private void putNavigationHeader() {
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);

        View header = navigationView.getHeaderView(0);
        TextView navigationHeaderUsername = header.findViewById(R.id.navigation_header_username);
        TextView navigationHeaderEmail = header.findViewById(R.id.navigation_header_email);
        navigationHeaderUsername.setText(sharedPrefManager.getUsername());
        navigationHeaderEmail.setText(sharedPrefManager.getEmail());
    }

    public void setupRecyclerView() {
        DeviceAdapter deviceAdapter = new DeviceAdapter(MainActivity.this);
        devicesRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));
        devicesRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        devicesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        devicesRecyclerView.setAdapter(deviceAdapter);

        LiveData<ArrayList<DeviceModel>> devicesListLiveData = new DeviceFirebaseQueryLiveData();
        devicesListLiveData.observe(this, deviceModels -> {
            deviceAdapter.setDeviceModelList(deviceModels);

            if (deviceModels.size() > 0) {
                RecyclerViewImageBackground.setVisibility(View.GONE);
            } else {
                RecyclerViewImageBackground.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    }

    private void setupDrawerLayout() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_logout) {
            signOut();
        }

        drawerLayout.closeDrawer(GravityCompat.START);  // close DrawerLayout after click buttons
        return false;
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        SharedPrefManager.getInstance(this).logout();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}