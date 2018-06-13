package adapterPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.roomaccountmanagement.R;

import java.util.ArrayList;
import java.util.List;

import adminFragment.MoneyAddingFragment;
import userDetails.UserDetails;


public class UserDetailsAdpater extends RecyclerView.Adapter<UserDetailsAdpater.MyHolder> {

    private ArrayList<UserDetails> strings;
    private Context context;
    float i=0;
    public UserDetailsAdpater(ArrayList<UserDetails> strings, Context context) {
        this.strings = strings;
        this.context = context;
    }

    @NonNull
    @Override
    public UserDetailsAdpater.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_check_box_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserDetailsAdpater.MyHolder holder, final int position) {
        holder.personTextView.setText(strings.get(position).getUserName());
        final boolean checkBox=strings.get(position).isCheckUser();
        final UserDetails userDetails=strings.get(position);
        if (checkBox)
        {
            holder.personCheckBox.setChecked(checkBox);
        }
        else
        {
            holder.personCheckBox.setChecked(checkBox);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                /*if (holder.personCheckBox.isChecked())
                {
                    strings.get(position).setCheckUser(false);
                    if (i>=0)
                    {
                        i--;
                        MoneyAddingFragment.amountTextView.setText(amountDividing(MoneyAddingFragment.amountHere,i));
                    }
                    else if (i==1)
                    {
                        i=0;
                        MoneyAddingFragment.amountTextView.setText(amountDividing(MoneyAddingFragment.amountHere,i));
                    }
                    notifyDataSetChanged();
                }
                else
                {
                    strings.get(position).setCheckUser(true);
                    Log.d("ItemsCount"," "+strings.get(position).isCheckUser());
                    MoneyAddingFragment.amountTextView.setText(amountDividing(MoneyAddingFragment.amountHere,i));
                }*/
                    if (checkBox == false)
                    {
                        strings.get(position).setCheckUser(true);
                        i++;
                        Log.d("ItemsCount"," "+strings.get(position).isCheckUser());
                        notifyDataSetChanged();
                        MoneyAddingFragment.amountTextView.setText(amountDividing(MoneyAddingFragment.amountHere,i));
                    }
                    else
                    {
                        if (i>=0)
                        {
                            i--;
                            MoneyAddingFragment.amountTextView.setText(amountDividing(MoneyAddingFragment.amountHere,i));
                        }
                        else if (i==1)
                        {
                            i=0;
                            MoneyAddingFragment.amountTextView.setText(amountDividing(MoneyAddingFragment.amountHere,i));
                        }
                        notifyDataSetChanged();
                        strings.get(position).setCheckUser(false);
                        Log.d("ItemsCount1"," "+i);
                        //
                    }
                    notifyDataSetChanged();
                    holder.personCheckBox.setChecked(userDetails.isCheckUser());
            }
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView personTextView;
        CheckBox personCheckBox;

        public MyHolder(View itemView) {
            super(itemView);

            personCheckBox = (CheckBox) itemView.findViewById(R.id.person_check_box);
            personTextView = (TextView) itemView.findViewById(R.id.person_text_view);
        }
    }

    public String amountDividing(String s,float strings)
    {
        int amount=Integer.parseInt(s);
        String s1=null;
        if (strings == 0)
        {
            s1=String.valueOf(amount);
        }
        else
        {
            float divide=amount/strings;
            s1=String.valueOf(divide);
        }
        return s1;
    }
}
