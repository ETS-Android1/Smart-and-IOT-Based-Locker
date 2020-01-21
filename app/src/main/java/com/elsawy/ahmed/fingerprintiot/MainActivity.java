package com.elsawy.ahmed.fingerprintiot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.elsawy.ahmed.fingerprintiot.Activities.AddDevice;
import com.elsawy.ahmed.fingerprintiot.Activities.LoginActivity;
import com.elsawy.ahmed.fingerprintiot.Adapters.DeviceAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private String TAG = "MainActivity";
    private RecyclerView devicesRecyclerView;
    private FloatingActionButton addDeviceFab;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

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


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        addDeviceFab = findViewById(R.id.add_device_fab);
        addDeviceFab.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddDevice.class)));

        DeviceAdapter deviceAdapter = new DeviceAdapter(MainActivity.this);

        devicesRecyclerView = findViewById(R.id.devices_recycler_view);
        devicesRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));
        devicesRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        devicesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        devicesRecyclerView.setAdapter(deviceAdapter);

        setupToolbar();
        setupDrawerLayout();

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
                signup();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);  // close DrawerLayout after click buttons
        return false;
    }

    private void signup() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}