package adminFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.roomaccountmanagement.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import firebasePackage.FirebaseUtils;
import firebasePackage.TotalMoneyCalculate;

@SuppressLint("ValidFragment")
public class MoneyCalculateFragment extends Fragment
{
    private Unbinder unbinder;
    private Context context;
    private TotalMoneyCalculate totalMoneyCalculate;

    @BindView(R.id.starting_date_picker)
    TextView startingDate;
    @BindView(R.id.ending_date_picker)
    TextView endingDate;
    @BindView(R.id.calculate_money_fragment)
    TextView calcualteMoeny;

    @SuppressLint("ValidFragment")
    public MoneyCalculateFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.money_calculate_fragment,container,false);
        initialView(view);
        return view;
    }

    private void initialView(View view)
    {
        unbinder= ButterKnife.bind(this,view);
        totalMoneyCalculate=new TotalMoneyCalculate(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
    @SuppressLint("NewApi")
    @OnClick(R.id.starting_date_picker)
    public void onClickEventOne(View view)
    {
        DatePickerDialog datePickerDialog=new DatePickerDialog(context);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String endingDateInString=String.valueOf(dayOfMonth+":"+(month+1)+":"+year);
                startingDate.setText(endingDateInString);
            }
        });
        datePickerDialog.show();
    }

    @SuppressLint("NewApi")
    @OnClick(R.id.ending_date_picker)
    public void onClickEventTwo(View view)
    {
        DatePickerDialog datePickerDialog=new DatePickerDialog(context);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String endingDateInString=String.valueOf(dayOfMonth+":"+(month+1)+":"+year);
                endingDate.setText(endingDateInString);
            }
        });
        datePickerDialog.show();
    }

    @OnClick(R.id.calculate_money_fragment)
    public void onClickThree(View view)
    {
        totalMoneyCalculate.dateChecking(FirebaseUtils.USERS_DETAILS_STRING,FirebaseUtils.USER_AMOUNT);
    }
}
