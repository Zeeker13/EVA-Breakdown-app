package com.example.ppaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ppaproject.models.Ashen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private EditText mPhoneNumberEditText;
    private EditText mFullNameEditText;
    private EditText mAddressEditText;
    private EditText mNationalIDEditText;
    private EditText mVehicleRegEditText;
    private EditText mInsuranceHotlineEditText;
    private EditText mInsuranceExpDateEditText;
    private EditText mFirstTrusteeNameEditText;
    private EditText mFirstTrusteePhoneEditText;

    private Button mSubmitButton;

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button myButton2 = findViewById(R.id.haveanaccount);
        myButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Dashboard.class);
                startActivity(intent);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mEmailEditText = findViewById(R.id.txt_Email);
        mPasswordEditText = findViewById(R.id.etRegisterPassword);
        mConfirmPasswordEditText = findViewById(R.id.cpw);
        mPhoneNumberEditText = findViewById(R.id.txt_num);
        mFullNameEditText = findViewById(R.id.txt_Fullname);
        mAddressEditText = findViewById(R.id.txt_add);
        mNationalIDEditText = findViewById(R.id.txt_nic);
        mVehicleRegEditText = findViewById(R.id.txt_VN);
        mInsuranceHotlineEditText = findViewById(R.id.txt_INO);
        mInsuranceExpDateEditText = findViewById(R.id.txt_IENO);
        mFirstTrusteeNameEditText = findViewById(R.id.txt_T1);
        mFirstTrusteePhoneEditText = findViewById(R.id.txt_PNO);

        mSubmitButton = findViewById(R.id.SignUpbtn);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();
                String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();
                String phoneNumber = mPhoneNumberEditText.getText().toString().trim();
                String fullName = mFullNameEditText.getText().toString().trim();
                String address = mAddressEditText.getText().toString().trim();
                String nationalID = mNationalIDEditText.getText().toString().trim();
                String vehicleReg = mVehicleRegEditText.getText().toString().trim();
                String insuranceHotline = mInsuranceHotlineEditText.getText().toString().trim();
                String insuranceExpDate = mInsuranceExpDateEditText.getText().toString().trim();
                String firstTrusteeName = mFirstTrusteeNameEditText.getText().toString().trim();
                String firstTrusteePhone = mFirstTrusteePhoneEditText.getText().toString().trim();


                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phoneNumber.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();

                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Ashen ashen = new Ashen(email,password, fullName, address, phoneNumber, nationalID, vehicleReg, insuranceHotline, insuranceExpDate, firstTrusteeName, firstTrusteePhone);
                                        String userID = mAuth.getCurrentUser().getUid();
                                        mDatabase.child("users").child(userID).setValue(ashen);

                                        Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(SignUp.this, Dashboard.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(SignUp.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }
            }
        });
    }
}

