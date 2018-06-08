package adminFragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.roomaccountmanagement.AdminHomeActivity;
import com.roomaccountmanagement.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Utils.CustomDialog;
import Utils.CustomProgressDialog;
import adapterPackage.UserDetailsAdpater;
import es.dmoral.toasty.Toasty;
import firebasePackage.FirebaseUtils;
import userDetails.UserDetails;

@SuppressLint("ValidFragment")
public class MoneyAddingFragment extends Fragment
{
    private Context context;
    private ArrayList<UserDetails> userDetails=new ArrayList<>();
    private TextView ok;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private CustomProgressDialog customProgressDialog;
    public static String amountHere;
    private ArrayList<String> userUid = new ArrayList<>();
    public static TextView amountTextView;

    public ArrayList<String> strings = new ArrayList<>();
    public ArrayList<String> stringsUid = new ArrayList<>();

    public MoneyAddingFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_with_check_box_list_view,container,false);
        initialView(view);
        return view;
    }

    private void initialView(View view)
    {
        customProgressDialog= CustomDialog.getCustomProgressDialog(context);
        firebaseAuth= FirebaseUtils.getFirebaseAuth();
        databaseReference=FirebaseUtils.getDatabaseReference();
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view_function);
        amountTextView=(TextView)view.findViewById(R.id.amount_textView);
        ok=(TextView)view.findViewById(R.id.user_check_box_ok);
        gettingUserDetails();
        amountHere=getArguments().getString("AMOUNT");
        amountTextView.setText(amountHere.toString());
    }

    public void usersShowingFunction()
    {
        final UserDetailsAdpater userDetailsAdpater=new UserDetailsAdpater(userDetails,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(userDetailsAdpater);
        userDetailsAdpater.notifyDataSetChanged();
        if (customProgressDialog.isShowing())
        {
            customProgressDialog.dismiss();
        }
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringsUid.clear();
                strings.clear();

                for (int i = 0; i < userDetails.size(); i++)
                {
                    if (userDetails.get(i).isCheckUser())
                    {
                        strings.add(userDetails.get(i).getUserName());
                        //getting selected user uid
                        stringsUid.add(userDetails.get(i).getUserUid());
                    }
                }

                if (strings.isEmpty())
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_DARK);
                    builder.setTitle("Waring...");
                    builder.setMessage("Select the person's");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
                else
                {
                    addAmount(stringsUid,FirebaseUtils.USERS_DETAILS_STRING);
                    Toasty.success(context,"Amount Added Successfully", Toast.LENGTH_LONG).show();
                    Fragment fragment=new AllUserDetails(context);
                    commonFragment(fragment);
                }
                Log.d("CheckedUserDetails", " " + stringsUid);

            }

        });
    }
    public void gettingUserDetails() {

        databaseReference.child(FirebaseUtils.USERS_DETAILS_STRING).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String name = dataSnapshot.child(FirebaseUtils.USER_NAME_STRING).getValue(String.class);
                String st=dataSnapshot.getKey();
                userDetails.add(new UserDetails(name, st,false));
                usersShowingFunction();
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

    @Override
    public void onStart() {
        super.onStart();
    }

    public void addAmount(final ArrayList<String> st, final String path)
    {
        databaseReference.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (int it=0;it<st.size();it++)
                {
                if (dataSnapshot.child(st.get(it).toString()).child(FirebaseUtils.USER_AMOUNT).child(ExpanseFragment.currentDate).exists())
                {
                    final int finalIt = it;
                    databaseReference.child(path).child(st.get(it).toString()).child(FirebaseUtils.USER_AMOUNT).child(ExpanseFragment.currentDate).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String amount=dataSnapshot.getValue(String.class);
                            float a=Float.parseFloat(amount);
                            float aa=Float.parseFloat(amountTextView.getText().toString());
                            float total=a+aa;
                            String finalAmount=String.valueOf(total);
                            databaseReference.child(path).child(st.get(finalIt)).child(FirebaseUtils.USER_AMOUNT).child(ExpanseFragment.currentDate).setValue(finalAmount);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                        databaseReference.child(path).child(st.get(it)).child(FirebaseUtils.USER_AMOUNT).child(ExpanseFragment.currentDate).setValue(amountTextView.getText().toString());
                }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }
    public void commonFragment(Fragment fragment)
    {
        AdminHomeActivity adminHomeActivity=((AdminHomeActivity)context);
        FragmentManager fragmentManager = adminHomeActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.admin_content, fragment);
        fragmentTransaction.commit();
    }
}
