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

import userDetails.UserDetails;


public class UserDetailsAdpater extends RecyclerView.Adapter<UserDetailsAdpater.MyHolder> {

    private ArrayList<UserDetails> strings;
    private Context context;

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
                    if (checkBox == false)
                    {
                        strings.get(position).setCheckUser(true);

                    }
                    else
                    {
                        strings.get(position).setCheckUser(false);
                    }
                    notifyDataSetChanged();
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
            personCheckBox.setEnabled(false);
        }
    }
}
