package com.example.ppaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private ImageButton healthButton;
    private ImageButton vehicleButton;
    private ImageButton recoverBut;
    private ImageButton emergencyButton;

    private DatabaseReference familyMemberRef; // Firebase database reference for family members

    private static final int CALL_PERMISSION_REQUEST_CODE = 1;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize the Firebase database reference
        familyMemberRef = FirebaseDatabase.getInstance().getReference().child("family_members");

        healthButton = findViewById(R.id.imageButton4);
        healthButton.setOnClickListener(this);

        vehicleButton = findViewById(R.id.vehicleSbt);
        vehicleButton.setOnClickListener(this);

        recoverBut = findViewById(R.id.Rvehbt);
        recoverBut.setOnClickListener(this);

        emergencyButton = findViewById(R.id.emerbt);
        emergencyButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageButton4) {
            showNearbyHospitals();
        } else if (v.getId() == R.id.vehicleSbt) {
            navigateToVehicleService();
        } else if (v.getId() == R.id.Rvehbt) {
            showNearbyGarage();
        } else if (v.getId() == R.id.emerbt) {
            retrieveFamilyMemberPhoneNumber();
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
        familyMemberRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    phoneNumber = dataSnapshot.child("firstTrusteePhoneNumber:").getValue(String.class);
                    makeEmergencyCall();
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

    private void makeEmergencyCall() {
        if (ContextCompat.checkSelfPermission(Home.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            initiateCall();
        } else {
            ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initiateCall();
            } else {
                Toast.makeText(Home.this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initiateCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }
}
