package com.roomaccountmanagement;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import Utils.CustomDialog;
import Utils.CustomProgressDialog;
import adminPackage.AdminDetails;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import firebasePackage.FirebaseUtils;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private CustomProgressDialog customProgressDialog;
    private TextView adminTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initial();
    }

    private void initial() {
        firebaseAuth = FirebaseUtils.getFirebaseAuth();
        databaseReference = FirebaseUtils.getDatabaseReference();
        adminTextView = findViewById(R.id.admin_register_id);
        adminTextView.setOnClickListener(this);
        customProgressDialog = CustomDialog.getCustomProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        if (v == adminTextView) {
            adminRegistrationDialog();
        }
    }

    public void adminRegistrationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
        View view = View.inflate(getApplicationContext(), R.layout.sign_up_layout, null);
        builder.setCancelable(false);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        CircleImageView adminPhoto;
        final EditText adminNameEditText, adminEmailEditText, adminPhoneNumber, adminPasswordEditText;
        TextView adminRegisterTextView, backTextView;

        adminPhoto = view.findViewById(R.id.profile_upload_img);
        adminNameEditText = view.findViewById(R.id.profile_upload_name);
        adminPhoneNumber = view.findViewById(R.id.profile_upload_phone);
        adminEmailEditText = view.findViewById(R.id.profile_upload_email);
        adminPasswordEditText = view.findViewById(R.id.profile_upload_password);
        adminRegisterTextView = view.findViewById(R.id.profile_register);
        backTextView = view.findViewById(R.id.profile_back);

        adminRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = adminEmailEditText.getText().toString().trim();
                String name = adminNameEditText.getText().toString().trim();
                String phone = adminPhoneNumber.getText().toString().trim();
                String password = adminPasswordEditText.getText().toString().trim();
                if (!name.isEmpty()) {
                    if (!email.isEmpty()) {
                        if (!phone.isEmpty()) {
                            if (!password.isEmpty() && password.length() >= 6) {
                                if (!email.isEmpty() && !name.isEmpty() && !password.isEmpty() && password.length() >= 6 && !phone.isEmpty() && phone.length() == 10) {
                                    adminRegistrationDetails(email, password, phone, name);
                                    Toasty.success(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                                } else {
                                    Toasty.warning(getApplicationContext(), "Somethings else check properly", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                if (password.isEmpty()) {
                                    Toasty.warning(getApplicationContext(), "Password is Empty", Toast.LENGTH_LONG).show();
                                } else {
                                    Toasty.warning(getApplicationContext(), "Password size is at least 6 ", Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            if (phone.isEmpty()) {
                                Toasty.warning(getApplicationContext(), "Phone Number is Empty", Toast.LENGTH_LONG).show();
                            } else {
                                Toasty.warning(getApplicationContext(), "Phone Number is Invalid", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Toasty.warning(getApplicationContext(), "Enter the Email", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toasty.warning(getApplicationContext(), "Enter the Name" + name, Toast.LENGTH_LONG).show();
                }

            }
        });

        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void adminRegistrationDetails(final String email, String password, final String phoneNumber, final String name) {
        customProgressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                AdminDetails adminDetails = new AdminDetails(name, phoneNumber, email);
                databaseReference = FirebaseUtils.getDatabaseReference();
                databaseReference.child(MyCommon.ADMINDETAILS).setValue(adminDetails);
                customProgressDialog.dismiss();
                startActivity(new Intent(AdminActivity.this, AdminHomeActivity.class));
                finish();
            }
        });
    }
}
