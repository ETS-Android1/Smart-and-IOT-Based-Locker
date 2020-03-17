package com.elsawy.ahmed.fingerprintiot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elsawy.ahmed.fingerprintiot.Activities.AddDevice;
import com.elsawy.ahmed.fingerprintiot.Activities.DeviceViewModel;
import com.elsawy.ahmed.fingerprintiot.Activities.LoginActivity;
import com.elsawy.ahmed.fingerprintiot.Adapters.DeviceAdapter;
import com.elsawy.ahmed.fingerprintiot.Models.DeviceModel;
import com.elsawy.ahmed.fingerprintiot.Models.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = "MainActivity";

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
    ImageView RecyclerViewimageBackground;

    private FirebaseAuth mAuth;

    static {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

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
        ButterKnife.bind(this);

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        if (sharedPrefManager.isLoggedIn())            {
            putNavigationHeader();
            setupToolbar();
            setupDrawerLayout();
            setupRecyclerView();
        }
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

        DeviceViewModel viewModel = ViewModelProviders.of(this).get(DeviceViewModel.class);
        LiveData<ArrayList<DeviceModel>> liveData = viewModel.getDevicesListLiveData();
        liveData.observe(this, deviceModels -> {
            deviceAdapter.setDeviceModelList(deviceModels);

            if (deviceModels.size() > 0) {
                RecyclerViewimageBackground.setVisibility(View.GONE);
            } else {
                RecyclerViewimageBackground.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setupToolbar() {
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

    private void setupDrawerLayout() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                signOut();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);  // close DrawerLayout after click buttons
        return false;
    }

    private void signOut() {
        SharedPrefManager.getInstance(this).logout();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}