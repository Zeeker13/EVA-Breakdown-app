package com.example.ppaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Get references to UI elements
        mEmailEditText = findViewById(R.id.editTextTextEmailAddress2);
        mPasswordEditText = findViewById(R.id.pass);
        mLoginButton = findViewById(R.id.buttonLog);

        mAuth = FirebaseAuth.getInstance();


        // Set up click listener for login button
        mLoginButton.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                // Get user input
                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();

                // Validate user input
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Dashboard.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Sign in user with Firebase authentication
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful() && task.getResult() != null) {
                                        // Display a success message and start the home activity
                                        Toast.makeText(Dashboard.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Dashboard.this, Home.class);
                                        startActivity(intent);
                                    } else {
                                        // Display an error message if the authentication fails
                                        Toast.makeText(Dashboard.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }
            }
        });

        // Set up click listener for create account button
        Button createAccountButton = findViewById(R.id.button4);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the sign-up activity
                Intent intent = new Intent(Dashboard.this, SignUp.class);
                startActivity(intent);
            }
        });


        Button forgetpw = findViewById(R.id.forgetBTN);
        forgetpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the sign-up activity
                Intent intent = new Intent(Dashboard.this, ForgetPassword.class);
                startActivity(intent);
            }
        });


    }


}
