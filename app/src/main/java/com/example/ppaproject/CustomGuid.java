package com.example.ppaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ppaproject.models.Guid;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomGuid extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewGuid;
    private Button createGuidButton;
    private DatabaseReference guidRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_guid);

        // Initialize views
        textViewTitle = findViewById(R.id.editTextTextPersonName);
        textViewGuid = findViewById(R.id.editTextguid);
        createGuidButton = findViewById(R.id.updatebtn);

        // Get the Firebase database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        guidRef = database.getReference("guid");

        createGuidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read the entered title and guid
                String title = textViewTitle.getText().toString();
                String guid = textViewGuid.getText().toString();

                // Create a new Guid object
                Guid newGuid = new Guid(title, guid);

                // Store the new Guid object in the Firebase database
                guidRef.setValue(newGuid);

                // Clear the text fields
                textViewTitle.setText("");
                textViewGuid.setText("");
            }
        });
    }
}
