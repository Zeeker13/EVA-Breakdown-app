package com.example.ppaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class VehicleService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_service);



        ImageButton myButton2 = findViewById(R.id.Tireservice);
        myButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VehicleService.this, TireService.class);
                startActivity(intent);
            }
        });

        ImageButton myButton3 = findViewById(R.id.garagebtn);
        myButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VehicleService.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        ImageButton myButton4 = findViewById(R.id.imageButongas);
        myButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VehicleService.this, MapsActivity.class);
                startActivity(intent);
            }
        });

    }
}