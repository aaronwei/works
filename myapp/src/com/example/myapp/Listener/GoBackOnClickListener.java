package com.example.myapp.Listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.example.myapp.MyActivity;
import com.example.myapp.R;


/**
 * Created by aaron on 2015/2/10.
 */
public class GoBackOnClickListener implements View.OnClickListener {
    private Activity activity;

    public GoBackOnClickListener(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this.activity, MyActivity.class);
        activity.startActivityForResult(intent, 0);
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_rigth_out);
    }
}
