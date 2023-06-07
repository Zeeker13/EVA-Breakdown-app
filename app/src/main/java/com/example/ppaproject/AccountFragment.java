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
import com.example.ppaproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {

    private EditText turstnedittext;


    private EditText trustpnedittext;

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
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);



        addressEditText = view.findViewById(R.id.editTextAddress);
        emailTextView = view.findViewById(R.id.emailTextView);
        fullNameEditText = view.findViewById(R.id.editTextTextPersonName3);
        updateButton = view.findViewById(R.id.updateButton);
        forgetButton = view.findViewById(R.id.forget);

        vehicleedittext= view.findViewById(R.id.editTextvehiclereg);
        insuedittext= view.findViewById(R.id.editTextvehiclins);
        insuehotedittext= view.findViewById(R.id.editTextvehiclehot);
        turstnedittext= view.findViewById(R.id.editTexttrustn);
        trustpnedittext= view.findViewById(R.id.editTexttrustpn);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFullName = fullNameEditText.getText().toString().trim();
                if (!newFullName.isEmpty()) {
                    userRef.child("fullName").setValue(newFullName);



                }
                String newFulladdress = addressEditText.getText().toString().trim();
                if (!newFulladdress.isEmpty()) {
                    userRef.child("address").setValue(newFulladdress);

                }

                String newvehicleRegNumber = vehicleedittext.getText().toString().trim();
                if (!newvehicleRegNumber.isEmpty()) {
                    userRef.child("vehicleRegNumber").setValue(newvehicleRegNumber);

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
                        String fullName = snapshot.child("fullName").getValue(String.class);
                        String address = snapshot.child("address").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);
                        String vehiclereg = snapshot.child("vehicleRegNumber").getValue(String.class);
                        String vehicleins = snapshot.child("insuranceExpiryDate").getValue(String.class);
                        String vehiclehot = snapshot.child("insuranceHotline").getValue(String.class);
                        String turstn = snapshot.child("firstTrusteeName").getValue(String.class);
                        String trustp = snapshot.child("firstTrusteePhoneNumber").getValue(String.class);





                        emailTextView.setText(email);
                        fullNameEditText.setText(fullName);
                        addressEditText.setText(address);
                        vehicleedittext.setText(vehiclereg);
                        insuedittext.setText(vehicleins);
                        insuehotedittext.setText(vehiclehot);
                        turstnedittext.setText(turstn);
                        trustpnedittext.setText(trustp);
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
