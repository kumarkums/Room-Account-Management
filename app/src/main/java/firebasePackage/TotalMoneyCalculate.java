package firebasePackage;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import userDetails.UserDetails;

public class TotalMoneyCalculate
{
    private Context context;

    private FirebaseUtils firebaseUtils=new FirebaseUtils(context);
    public TotalMoneyCalculate(Context context) {
        this.context = context;
    }

    /*public void dateChecking(String userDetails,String path)
    {
        ArrayList<String> strings=new ArrayList<>();
        DatabaseReference firebaseDatabase=FirebaseUtils.getDatabaseReference();
        strings.addAll(firebaseUtils.gettingUserKey(userDetails));
        Log.d("StringDateValues1"," "+strings);
        *//*firebaseDatabase.child(userDetails).child("3wCyJ4Vw9Gd3vbeQu0l8kk1S47w2").child(path).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String s1=dataSnapshot.getKey();
             //   strings.add();

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
        });*//*

    }
*/}
