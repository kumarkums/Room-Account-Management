package adminPackage;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roomaccountmanagement.AdminHomeActivity;
import com.roomaccountmanagement.R;

import java.util.List;

import adminFragment.UserDetailsInFragment;

import static java.security.AccessController.getContext;

public class AdminRecyclerAdapter extends RecyclerView.Adapter<AdminRecyclerAdapter.MyViewHolder>
{
    private List<AdminSideUserDetails> adminSideUserDetails;
    private Context context;
    private View view;
    public AdminRecyclerAdapter(List<AdminSideUserDetails> adminSideUserDetails, Context context) {
        this.adminSideUserDetails = adminSideUserDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_details_to_admin,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position)
    {
        holder.name.setText(adminSideUserDetails.get(position).getName());
        holder.email.setText(adminSideUserDetails.get(position).getEmail());
        holder.phone.setText(adminSideUserDetails.get(position).getPhone());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d("UsersDetails"," "+adminSideUserDetails.get(position).getName());
                Fragment fragment1=new UserDetailsInFragment(context);
                AdminHomeActivity adminHomeActivity=(AdminHomeActivity)context;
                adminHomeActivity.
                        getSupportFragmentManager().beginTransaction().
                        replace(R.id.admin_content, fragment1).
                        addToBackStack(null).
                        commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return adminSideUserDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,email,phone;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.user_name_for_admin);
            email=(TextView)itemView.findViewById(R.id.user_email_for_admin);
            phone=(TextView)itemView.findViewById(R.id.user_phone_for_admin);
        }
    }
}
