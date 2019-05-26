package com.evani.nytimesmostpopular.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.evani.nytimesmostpopular.R;

public class NewYorkTimesProgressDialog extends Dialog {

    Context mContext;
    Dialog dialog1;

    public Dialog showDialog() {
        dialog1 = new NewYorkTimesProgressDialog(mContext);
        ProgressBar progressBar = new ProgressBar(mContext);
        dialog1.addContentView(progressBar, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (progressBar.getIndeterminateDrawable() != null) {
            progressBar.getIndeterminateDrawable().setColorFilter(mContext.getResources().getColor(R.color.wallet_holo_blue_light), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        try {
            dialog1.show();
        } catch (Exception e) {
            Log.e("Error" , "" + e.getMessage());
        }
        return dialog1;
    }

    public void dismissDialog()
    {
        if(dialog1!=null && dialog1.isShowing())
        {
            dialog1.dismiss();
        }
    }

    public NewYorkTimesProgressDialog(@NonNull Context context) {
        super(context, R.style.NewDialog);
        this.mContext = context;
    }
}
