package com.example.myapp.toast;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapp.R;

/**
 * Created by aaron on 2015/2/9.
 */
public class MyRedToast {
    private Activity activity;

    public MyRedToast(Activity activity){
        this.activity = activity;
    }

    /**
     * 弹出自定义toast
     */
    public void showToast(CharSequence text){
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) this.activity.findViewById(R.id.toast_layout));
        Toast toast = Toast.makeText(this.activity.getApplicationContext(), R.string.app_toast_text, Toast.LENGTH_SHORT);
        TextView toastText = (TextView)layout.findViewById(R.id.toast_layout_text);
        toastText.setText(text);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

}
