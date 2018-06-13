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

import Utils.CustomDialog;
import Utils.CustomProgressDialog;
import interfacePackage.MoneyGetting;
import userDetails.UserDetails;

public class TotalMoneyCalculate implements MoneyGetting
{
    private Context context;
    private CustomProgressDialog customDialog;
    private FirebaseUtils firebaseUtils=new FirebaseUtils(context);
    private float totalAmount;;
    private ArrayList<String> userListAmount=new ArrayList<>();
    public TotalMoneyCalculate(Context context) {
        this.context = context;
    }

    public float gettingTotalMoney(String userDetails,ArrayList<String> arrayList)
    {
        userListAmount.clear();
        customDialog= CustomDialog.getCustomProgressDialog(context);
        DatabaseReference firebaseDatabase=FirebaseUtils.getDatabaseReference();
        for (int i=0;i<arrayList.size();i++)
        {
            firebaseDatabase.child(userDetails).child(arrayList.get(i)).child(FirebaseUtils.USER_AMOUNT).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String s1=dataSnapshot.getValue(String.class);
                    userListAmount.add(s1);
                    totalAmount=totalAmount+Float.parseFloat(s1);
                    Log.d("TotalAmountValues123"," "+userListAmount.size());
                    Log.d("TotalAmountValues123456"," "+totalAmount);
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
        }

        return 0;
    }
}
