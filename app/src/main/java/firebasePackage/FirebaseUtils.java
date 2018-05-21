package firebasePackage;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roomaccountmanagement.MyCommon;

import adminPackage.AdminDetails;

public class FirebaseUtils {
    public static FirebaseAuth firebaseAuth;
    public static FirebaseUser firebaseUser;
    public static DatabaseReference databaseReference;

    private Context context;

    public FirebaseUtils(Context mContext) {
        this.context = mContext;
    }

    public static FirebaseAuth getFirebaseAuth() {
        firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth;
    }

    public static FirebaseUser getFirebaseUser() {
        firebaseUser = firebaseAuth.getCurrentUser();
        return firebaseUser;
    }

    public static DatabaseReference getDatabaseReference() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        return databaseReference;
    }


    public void CheckAdmin(final AdminCheck adminCheck) {
        databaseReference = getDatabaseReference();
        databaseReference.child(MyCommon.ADMINDETAILS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    adminCheck.CheckAdimin(true, dataSnapshot);
                } else {
                    adminCheck.CheckAdimin(false, dataSnapshot);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                adminCheck.LoginError();
            }
        });
    }

    public void CheckAdminOrUser(final String email_str, final CheckUserAdmin checkUserAdmin) {
        databaseReference = getDatabaseReference();
        databaseReference.child("AdminDetails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AdminDetails adminDetails = dataSnapshot.getValue(AdminDetails.class);
                String adminEmail = adminDetails.getAdminEmail();
                if (adminEmail.equals(email_str)) {
                    checkUserAdmin.CheckUserOrAdmin("Admin");
                } else {
                    checkUserAdmin.CheckUserOrAdmin("User");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                checkUserAdmin.CheckUserOrAdmin("Error");
            }
        });
    }

}
