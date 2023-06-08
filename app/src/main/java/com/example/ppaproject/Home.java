package com.example.ppaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.location.LocationServices;
import android.telephony.SmsManager;


public class Home extends AppCompatActivity implements View.OnClickListener {

    private ImageButton healthButton;
    private ImageButton vehicleButton;
    private ImageButton recoverBut;
    private ImageButton emergencyButton;

    private DatabaseReference familyMemberRef;
    private static final int CALL_PERMISSION_REQUEST_CODE = 1;
    private String phoneNumber;
    private GoogleMap mMap;
    private LatLng latLng;
    private String test;

    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        findViewById(R.id.navi2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Initialize the Firebase database reference
        familyMemberRef = FirebaseDatabase.getInstance().getReference().child("users").child("JDvsmzjkGxaNZSm3yTjfKjj2VTi1");

        healthButton = findViewById(R.id.imageButton4);
        healthButton.setOnClickListener(this);

        vehicleButton = findViewById(R.id.vehicleSbt);
        vehicleButton.setOnClickListener(this);

        recoverBut = findViewById(R.id.Rvehbt);
        recoverBut.setOnClickListener(this);

        emergencyButton = findViewById(R.id.emerbt);
        emergencyButton.setOnClickListener(this);

        // Initialize the FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton4:
                showNearbyHospitals();
                break;
            case R.id.vehicleSbt:
                navigateToVehicleService();
                break;
            case R.id.Rvehbt:
                showNearbyGarage();
                break;
            case R.id.emerbt:
                retrieveFamilyMemberPhoneNumber();
                break;
        }
    }

    private void showNearbyHospitals() {
        Intent intent = new Intent(Home.this, MapsActivity.class);
        intent.putExtra("placeType", "hospital");
        startActivity(intent);
    }

    private void showNearbyGarage() {
        Intent intent = new Intent(Home.this, MapsActivity.class);
        intent.putExtra("placeType", "car_repair");
        startActivity(intent);
    }

    private void navigateToVehicleService() {
        Intent intent = new Intent(Home.this, VehicleService.class);
        startActivity(intent);
    }

    private void retrieveFamilyMemberPhoneNumber() {
        familyMemberRef.child("firstTrusteePhoneNumber").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    phoneNumber = dataSnapshot.getValue(String.class);
                    getCurrentLocation();
                } else {
                    Toast.makeText(Home.this, "Family member not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Home.this, "Error retrieving family member", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCurrentLocation() {
        // Check if the app has the necessary permission to access the location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission if it is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        // Get the last known location
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    String currentLocation = "Latitude: " + latitude + ", Longitude: " + longitude;
                    sendEmergencyMessage(currentLocation);
                } else {
                    Toast.makeText(Home.this, "Failed to get current location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendEmergencyMessage(String currentLocation) {
        // Check if the app has the necessary permission to make phone calls
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission if it is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
            return;
        }

        // Create the phone call intent with the retrieved phone number
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));

        // Start the phone call
        startActivity(callIntent);
    }

    public static void showNearbyPlaces(String response, GoogleMap mMap) {
        try {
            mMap.clear(); // Clear the map before adding new markers

            JSONObject jsonObject = new JSONObject(response);
            JSONArray results = jsonObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject place = results.getJSONObject(i);
                JSONObject location = place.getJSONObject("geometry").getJSONObject("location");
                String name = place.getString("name");
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");

                LatLng latLng = new LatLng(lat, lng);
                mMap.addMarker(new MarkerOptions().position(latLng).title(name));
            }
        } catch (JSONException e) {
            Log.e("MapsAPI", "Error parsing JSON", e);
        }
    }

    public static void currentLocation(GoogleMap mMap, double lat, double lng, String title) {
        mMap.clear(); // Clear the map before adding the marker

        LatLng latLng = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(latLng).title(title));
    }
}
