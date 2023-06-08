package com.example.ppaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TireServiceGuid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tire_service_guid);


        Button myButton2 = findViewById(R.id.punchurbt);
        myButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TireServiceGuid.this, TirePuncherHelp.class);
                startActivity(intent);
            }
        });


        Button myButton3 = findViewById(R.id.TirechangeBT);
        myButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TireServiceGuid.this, TireChangingguid.class);
                startActivity(intent);
            }
        });

        Button myButton4 = findViewById(R.id.customguid);
        myButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TireServiceGuid.this, CustomguidDisplay.class);
                startActivity(intent);
            }
        });


    }
}