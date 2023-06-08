package com.example.ppaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.ppaproject.models.Guid;


public class CustomguidDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customguid_display);


        Button myButton2 = findViewById(R.id.button);
        myButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomguidDisplay.this,CustomGuid.class);
                startActivity(intent);
            }
        });
    }



}