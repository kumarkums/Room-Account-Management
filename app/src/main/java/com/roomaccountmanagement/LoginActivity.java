package com.roomaccountmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import userDetails.UserDetails;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEditText, passwordEditText;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private TextView signInTextView, signUpTextView;
    private CustomProgressDialog customProgressDialog;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initial();
    }

    private void initial() {
        firebaseAuth = FirebaseUtils.getFirebaseAuth();
        databaseReference = FirebaseUtils.getDatabaseReference();
        customProgressDialog = CustomDialog.getCustomProgressDialog(this);
        emailEditText = (EditText) findViewById(R.id.login_page_email_id);
        passwordEditText = (EditText) findViewById(R.id.login_page_password_id);
        signInTextView = (TextView) findViewById(R.id.login_page_sign_in_id);
        signInTextView.setOnClickListener(this);
        signUpTextView = (TextView) findViewById(R.id.login_page_sign_up_id);
        signUpTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_page_sign_in_id:
                customProgressDialog.show();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                userLoginFunction(email, password, "AdminDetails");
                break;

            case R.id.login_page_sign_up_id:
                userRegistrationDialog();
                break;
            default:
                break;
        }
    }

    public void userRegistrationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        View view = View.inflate(getApplicationContext(), R.layout.sign_up_layout, null);
        builder.setCancelable(false);
        builder.setView(view);
        alertDialog = builder.create();
        CircleImageView adminPhoto;
        final EditText adminNameEditText, adminEmailEditText, adminPhoneNumber, adminPasswordEditText;
        TextView adminRegisterTextView, backTextView;

        adminPhoto = (CircleImageView) view.findViewById(R.id.profile_upload_img);
        adminNameEditText = (EditText) view.findViewById(R.id.profile_upload_name);
        adminPhoneNumber = (EditText) view.findViewById(R.id.profile_upload_phone);
        adminEmailEditText = (EditText) view.findViewById(R.id.profile_upload_email);
        adminPasswordEditText = (EditText) view.findViewById(R.id.profile_upload_password);
        adminRegisterTextView = (TextView) view.findViewById(R.id.profile_register);
        backTextView = (TextView) view.findViewById(R.id.profile_back);

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
                                    userRegistrationDetails(email, password, phone, name);
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

    public void userRegistrationDetails(final String email, String password, final String phoneNumber, final String name) {
        customProgressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toasty.success(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
                UserDetails userDetails = new UserDetails(name, email, phoneNumber);
                databaseReference = FirebaseUtils.getDatabaseReference();
                if (firebaseAuth.getCurrentUser() != null) {
                    databaseReference.child(MyCommon.USERDETAILS).child(FirebaseUtils.getFirebaseUser().getUid()).setValue(userDetails);
                }
                customProgressDialog.dismiss();
                alreadyLoggedIn();
            }
        });
    }

    public void userLoginFunction(final String email, final String password, final String path) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                adminOrUserEmailChecking(path, email);
            }
        });
    }

    private void adminOrUserEmailChecking(final String path, final String email) {
        databaseReference.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                AdminDetails adminDetails = dataSnapshot.getValue(AdminDetails.class);
                String adminEmail = adminDetails.getAdminEmail();

                if (adminEmail.equals(email)) {
                    customProgressDialog.dismiss();
                    startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
                    finish();
                } else {
                    customProgressDialog.dismiss();
                    startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void alreadyLoggedIn() {
        if (customProgressDialog.isShowing()) {
            customProgressDialog.dismiss();
        }
        if (firebaseAuth.getCurrentUser() != null) {
            String s = FirebaseUtils.getFirebaseUser().getEmail();
            adminOrUserEmailChecking(MyCommon.ADMINDETAILS, s);
        }
    }

}
