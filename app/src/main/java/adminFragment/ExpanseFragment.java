package adminFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.roomaccountmanagement.AdminHomeActivity;
import com.roomaccountmanagement.R;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private TextView expansiveTextView,eveAmountTextView;
    private EditText eveningEditText,morningAmountTextView;
    private String amount;
    public static String currentDate;
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
        morningAmountTextView=(EditText) view.findViewById(R.id.morning_amount_id);
        eveningEditText=(EditText)view.findViewById(R.id.evening_amount_id);
        eveAmountTextView=(TextView)view.findViewById(R.id.expanse_eve_persons);
        eveAmountTextView.setOnClickListener(this);
        expansiveTextView=(TextView)view.findViewById(R.id.expansive_date_textView);

        DateFormat dateFormat=new SimpleDateFormat("dd:MM:yyyy");
        Date date=new Date();
        currentDate=dateFormat.format(date);
        expansiveTextView.setText(currentDate);
        Log.d("DateMonthYear"," "+currentDate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.expanse_mor_persons:
            //    customProgressDialog.show();
                Fragment fragment1=new MoneyAddingFragment(context);
                if (morningAmountTextView.getText().toString().trim().length() == 0)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_DARK);
                    builder.setTitle("Waring...");
                    builder.setMessage("Enter the amount");
                    builder.setCancelable(false);
                    final AlertDialog alertDialog=builder.create();
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                        }
                    });

                    builder.show();
                }
                else {
                    amount=morningAmountTextView.getText().toString().trim();
                    Bundle bundle=new Bundle();
                    bundle.putString("AMOUNT",amount);
                    fragment1.setArguments(bundle);
                    commonFragment(fragment1);
                    Log.d("amountTextViewText"," "+morningAmountTextView.getText().toString());
                }
                break;

            case R.id.expanse_eve_persons:
                //    customProgressDialog.show();
                Fragment fragment2=new MoneyAddingFragment(context);
                if (eveningEditText.getText().toString().trim().length() == 0)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_DARK);
                    builder.setTitle("Waring...");
                    builder.setMessage("Enter the amount");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.show();
                }
                else {
                    amount=eveningEditText.getText().toString().trim();
                    Bundle bundle=new Bundle();
                    bundle.putString("AMOUNT",amount);
                    fragment2.setArguments(bundle);
                    commonFragment(fragment2);
                    Log.d("amountTextViewText"," "+eveningEditText.getText().toString());
                }
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
