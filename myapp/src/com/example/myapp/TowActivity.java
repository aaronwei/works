package com.example.myapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;
import com.example.myapp.Listener.GoBackOnClickListener;

/**
 * Created by aaron on 2015/2/10.
 */
public class TowActivity extends Activity {

    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tow);
        this.textView = (TextView)this.findViewById(R.id.goBack);
        this.textView.setOnClickListener(new GoBackOnClickListener(TowActivity.this));
    }


}
