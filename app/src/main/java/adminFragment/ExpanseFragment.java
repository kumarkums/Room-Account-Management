package adminFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.roomaccountmanagement.AdminHomeActivity;
import com.roomaccountmanagement.R;

import java.util.ArrayList;

import Utils.CustomDialog;
import Utils.CustomProgressDialog;
import adapterPackage.UserDetailsAdpater;
import adminPackage.AdminSideUserDetails;
import firebasePackage.FirebaseUtils;
import userDetails.UserDetails;

@SuppressLint("ValidFragment")
public class ExpanseFragment extends Fragment implements View.OnClickListener
{
    private Context context;
    private TextView morningExpansive,afternoonExpansive,eveningExpanse;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private CustomProgressDialog customProgressDialog;
    public ExpanseFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.expanse_layout,container,false);
        initialView(view);
        return view;
    }

    private void initialView(View view)
    {
        firebaseAuth= FirebaseUtils.getFirebaseAuth();
        databaseReference=FirebaseUtils.getDatabaseReference();
        customProgressDialog= CustomDialog.getCustomProgressDialog(context);
        morningExpansive=(TextView)view.findViewById(R.id.expanse_mor_persons);
        morningExpansive.setOnClickListener(this);
        eveningExpanse=(TextView)view.findViewById(R.id.expanse_eve_persons);
        eveningExpanse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.expanse_mor_persons:
            //    customProgressDialog.show();
                Fragment fragment1=new MoneyAddingFragment(context);
                commonFragment(fragment1);
                break;
            default:
                break;
        }
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
