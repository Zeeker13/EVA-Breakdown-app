package com.example.ppaproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.Task;

import com.example.ppaproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {

    private EditText turstnedittext;
    private EditText trustpnedittext;

    private Button deleteButton;
    private EditText vehicleedittext;
    private EditText insuedittext;
    private EditText insuehotedittext;
    private EditText addressEditText;
    private TextView emailTextView;
    private EditText fullNameEditText;
    private Button updateButton;
    private Button forgetButton;

    private DatabaseReference userRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String currentUserId = currentUser.getUid();
            userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        addressEditText = view.findViewById(R.id.editTextAddress);
        emailTextView = view.findViewById(R.id.emailTextView);
        fullNameEditText = view.findViewById(R.id.editTextTextPersonName3);
        updateButton = view.findViewById(R.id.updateButton);
        forgetButton = view.findViewById(R.id.forget);
        deleteButton = view.findViewById(R.id.deleteButton);
        vehicleedittext = view.findViewById(R.id.editTextvehiclereg);
        insuedittext = view.findViewById(R.id.editTextvehiclins);
        insuehotedittext = view.findViewById(R.id.editTextvehiclehot);
        turstnedittext = view.findViewById(R.id.editTexttrustn);
        trustpnedittext = view.findViewById(R.id.editTexttrustpn);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the new values from EditText fields
                String newFullName = fullNameEditText.getText().toString().trim();
                String newFullAddress = addressEditText.getText().toString().trim();
                String newVehicleRegNumber = vehicleedittext.getText().toString().trim();
                String newVehicleInsExpiry = insuedittext.getText().toString().trim();
                String newVehiclehot = insuehotedittext.getText().toString().trim();
                String newTrusteename = turstnedittext.getText().toString().trim();
                String newTrusteenumber = trustpnedittext.getText().toString().trim();


                // Check if the fields are not empty
                if (!newFullName.isEmpty()) {
                    // Update the "fullName" value in the userRef
                    userRef.child("fullName").setValue(newFullName);
                }

                if (!newFullAddress.isEmpty()) {
                    // Update the "address" value in the userRef
                    userRef.child("address").setValue(newFullAddress);
                }

                if (!newVehicleRegNumber.isEmpty()) {
                    // Update the "vehicleRegNumber" value in the userRef
                    userRef.child("vehicleRegNumber").setValue(newVehicleRegNumber);
                }

                if (!newVehicleInsExpiry.isEmpty()) {
                    // Update the "insuranceExpiryDate" value in the userRef
                    userRef.child("insuranceExpiryDate").setValue(newVehicleInsExpiry);
                }

                if (!newVehiclehot.isEmpty()) {
                    // Update the "insuranceExpiryDate" value in the userRef
                    userRef.child("insuranceHotline").setValue(newVehiclehot);
                }


                if (!newTrusteename.isEmpty()) {
                    // Update the "firstTrusteeName" value in the userRef
                    userRef.child("firstTrusteeName").setValue(newTrusteename);
                }
                if (!newTrusteenumber.isEmpty()) {
                    // Update the "firstTrusteePhoneNumber" value in the userRef
                    userRef.child("firstTrusteePhoneNumber").setValue(newTrusteenumber);
                }

            }
        });

        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(getActivity(), ForgetPassword.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete user account and log out
                if (currentUser != null) {
                    currentUser.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // User account deleted successfully, log out
                                        firebaseAuth.signOut();
                                        // Redirect to login or home screen
                                        // For example:
                                        Intent intent = new Intent(getActivity(), Dashboard.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        // Error occurred while deleting user account
                                        // Handle the error or display a message to the user
                                    }
                                }
                            });
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (userRef != null) {
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Retrieve values from snapshot
                        String fullName = snapshot.child("fullName").getValue(String.class);
                        String address = snapshot.child("address").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);
                        String vehicleReg = snapshot.child("vehicleRegNumber").getValue(String.class);
                        String insuranceExpiry = snapshot.child("insuranceExpiryDate").getValue(String.class);
                        String insuranceHotline = snapshot.child("insuranceHotline").getValue(String.class);
                        String trusteeName = snapshot.child("firstTrusteeName").getValue(String.class);
                        String trusteePhoneNumber = snapshot.child("firstTrusteePhoneNumber").getValue(String.class);

                        // Set the retrieved values to the respective fields
                        emailTextView.setText(email);
                        fullNameEditText.setText(fullName);
                        addressEditText.setText(address);
                        vehicleedittext.setText(vehicleReg);
                        insuedittext.setText(insuranceExpiry);
                        insuehotedittext.setText(insuranceHotline);
                        turstnedittext.setText(trusteeName);
                        trustpnedittext.setText(trusteePhoneNumber);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error
                }
            });
        }
    }
}
