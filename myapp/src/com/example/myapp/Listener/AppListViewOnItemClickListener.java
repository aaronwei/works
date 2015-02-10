package com.example.myapp.Listener;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import com.example.myapp.R;
import com.example.myapp.toast.MyRedToast;

/**
 * Created by aaron on 2015/2/9.
 */
public class AppListViewOnItemClickListener implements AdapterView.OnItemClickListener {
    private Activity activity;

    public AppListViewOnItemClickListener(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        MyRedToast myRedToast = new MyRedToast(this.activity);
        TextView textViewTitle = (TextView)view.findViewById(R.id.title);
        TextView textViewInfo = (TextView)view.findViewById(R.id.info);
        myRedToast.showToast(textViewTitle.getText()+" 版本号"+textViewInfo.getText());
    }
}
