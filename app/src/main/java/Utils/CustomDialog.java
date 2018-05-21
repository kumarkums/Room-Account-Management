package Utils;

import android.content.Context;

import com.roomaccountmanagement.R;

public class CustomDialog
{
        public static CustomProgressDialog getCustomProgressDialog(Context context)
        {
            CustomProgressDialog customProgressDialog=new CustomProgressDialog(context);
            customProgressDialog.setContentView(R.layout.loading_dialog);
            customProgressDialog.setCancelable(false);
            return customProgressDialog;
        }
}
