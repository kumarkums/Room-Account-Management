package com.roomaccountmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import firebasePackage.AdminCheck;
import firebasePackage.CheckUserAdmin;
import firebasePackage.FirebaseUtils;

public class SpleshActivity extends AppCompatActivity {

    ProgressBar progressBar_cyclic;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // Removes title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splesh);
        inItview();
    }

    private void inItview() {
        progressBar_cyclic = findViewById(R.id.progressBar_cyclic);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar_cyclic.setVisibility(View.VISIBLE);
        adminChecking();
    }

    private void adminChecking() {
        firebaseAuth = FirebaseUtils.getFirebaseAuth();
        FirebaseUtils checkutile = new FirebaseUtils(this);
        checkutile.CheckAdmin(new AdminCheck() {
            @Override
            public void CheckAdimin(boolean chack, DataSnapshot User) {
                if (chack) {
                    if (firebaseAuth.getCurrentUser() != null) {
                        String email_str = FirebaseUtils.getFirebaseUser().getEmail();
                        adminOrUserEmailChecking(email_str);
                    } else {
                        startActivity(new Intent(SpleshActivity.this, LoginActivity.class));
                        finish();
                    }
                } else {
                    startActivity(new Intent(SpleshActivity.this, AdminActivity.class));
                    finish();
                }
            }

            @Override
            public void LoginError() {

            }
        });
    }

    private void adminOrUserEmailChecking(String email_str) {
        FirebaseUtils checkutile = new FirebaseUtils(this);
        checkutile.CheckAdminOrUser(email_str, new CheckUserAdmin() {
            @Override
            public void CheckUserOrAdmin(String adminDetails) {
                if (adminDetails.equals("Admin"))
                {
                    startActivity(new Intent(SpleshActivity.this, AdminHomeActivity.class));
                    finish();
                } else if (adminDetails.equals("User"))
                {
                    startActivity(new Intent(SpleshActivity.this, UserHomeActivity.class));
                    finish();
                }
            }
        });
    }
}
