package adminFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.roomaccountmanagement.R;

import java.util.ArrayList;

import Utils.CustomDialog;
import Utils.CustomProgressDialog;
import adapterPackage.UserDetailsAdpater;
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
        ok=(TextView)view.findViewById(R.id.user_check_box_ok);
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
                final ArrayList<String> strings = new ArrayList<>();
                strings.clear();
                for (int i = 0; i < userDetails.size(); i++) {
                    if (userDetails.get(i).isCheckUser()) {
                        strings.add(userDetails.get(i).getUserName());
                        Log.d("CheckedUserDetails", " " + strings);
                    }
                }
            }

        });
    }
    public void gettingUserDetails() {
        userDetails.clear();
        databaseReference.child("UserDetails").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String name = dataSnapshot.child("userName").getValue(String.class);
                userDetails.add(new UserDetails(name, false));
                usersShowingFunction();
                Log.d("UserDetails"," "+userDetails);
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
        gettingUserDetails();
    }
}
