package firebasePackage;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roomaccountmanagement.MyCommon;

import java.util.ArrayList;

import adminPackage.AdminDetails;
import userDetails.UserDetails;

public class FirebaseUtils {
    public static FirebaseAuth firebaseAuth;
    public static FirebaseUser firebaseUser;
    public static DatabaseReference databaseReference;
    public static String ADMIN_DETAILS_STRING="AdminDetails";
    public static String USERS_DETAILS_STRING="UserDetails";
    public static String ADMIN_NAME_STRING="adminName";
    public static String ADMIN_EMAIL_STRING="adminEmail";
    public static String ADMIN_PHONENUMBER_STRING="adminPhoneNumber";
    public static String USER_NAME_STRING="userName";
    public static String USER_EMAIL_STRING="userEmail";
    public static String USER_PHONENUMBER_STRING="userPhoneNumber";
    public static String USER_AMOUNT="Amount";

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

   /* public ArrayList<String> gettingUserKey(String path)
    {
        final ArrayList<String> arrayList= new ArrayList<>();
        arrayList.clear();
        databaseReference = getDatabaseReference();
        databaseReference.child(path).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String userKey=dataSnapshot.getKey();
                final ArrayList<String> userDetails=new ArrayList<>();
                userDetails.add(userKey);
                arrayList.addAll(userDetails);
                Log.d("StringDateValues"," "+arrayList);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d("StringDateValues12"," "+arrayList);
        return arrayList;
    }*/

}
