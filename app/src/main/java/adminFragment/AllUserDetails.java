package adminFragment;

import android.annotation.SuppressLint;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.roomaccountmanagement.R;

import java.util.ArrayList;

import Utils.CustomDialog;
import Utils.CustomProgressDialog;
import adminPackage.AdminRecyclerAdapter;
import adminPackage.AdminSideUserDetails;
import firebasePackage.FirebaseUtils;

@SuppressLint("ValidFragment")
public class AllUserDetails extends Fragment
{
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private Context context;
    private ArrayList<AdminSideUserDetails> adminSideUserDetails=new ArrayList<>();
    private CustomProgressDialog customProgressDialog;
    public AllUserDetails(Context context)
    {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.demo_list_view,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.user_details);
        customProgressDialog= CustomDialog.getCustomProgressDialog(context);
        customProgressDialog.show();
        gettingUserDetails();
        return view;
    }

    public void userDetails()
    {
        AdminRecyclerAdapter adminRecyclerAdapter=new AdminRecyclerAdapter((adminSideUserDetails),context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adminRecyclerAdapter);
        adminRecyclerAdapter.notifyDataSetChanged();
        customProgressDialog.dismiss();
    }

    public void gettingUserDetails()
    {
        firebaseAuth= FirebaseUtils.getFirebaseAuth();
        databaseReference=FirebaseUtils.getDatabaseReference();
        databaseReference.child("UserDetails").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //UserDetails userDetails1=dataSnapshot.child("userEmail").getValue(UserDetails.class);

                String email=dataSnapshot.child("userEmail").getValue(String.class);
                String name=dataSnapshot.child("userName").getValue(String.class);
                String phone=dataSnapshot.child("userPhoneNumber").getValue(String.class);
                adminSideUserDetails.add(new AdminSideUserDetails(name, email, phone));
                userDetails();
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

}
