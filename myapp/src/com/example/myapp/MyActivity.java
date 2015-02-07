package com.example.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.example.myapp.adspter.MyAdspter;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyActivity extends Activity implements View.OnClickListener{

    private TextView textView;
    private TextView settingView;
    private ListView listView = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        intView();
    }

    /**
     * 初始化页面控件，以及控件事件添加
     */
    public void intView(){
        this.textView = (TextView)this.findViewById(R.id.share);
        this.textView.setOnClickListener(this);
        this.settingView = (TextView)this.findViewById(R.id.setting);
        this.settingView.setOnClickListener(this);
        listView = (ListView)this.findViewById(R.id.list_view);
        List<Map<String,Object>> list = getData();
        listView.setAdapter(new MyAdspter(this,list));
    }

    /**
     * 页面 button onClick 事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.share:
                this.showToast(this.textView.getText());
                break;
            case R.id.setting:
                this.showToast(this.settingView.getText());
                break;
            case R.id.list_view:
                this.showToast(this.settingView.getText());
                break;
            default:
                break;
        }
    }

    /**
     * 弹出自定义toast
     */
    public void showToast(CharSequence text){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
        Toast toast = Toast.makeText(getApplicationContext(),R.string.app_toast_text,Toast.LENGTH_SHORT);
        TextView toastText = (TextView)layout.findViewById(R.id.toast_layout_text);
        toastText.setText("你点击的是："+text);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image", R.drawable.ic_launcher);
            map.put("title", "标题"+i);
            map.put("info", "详细信息"+i);
            list.add(map);
        }
        return list;
    }
}
