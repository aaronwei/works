package com.example.myapp;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapp.aaronwei.PullDownView;
import com.example.myapp.adspter.MyAdspter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyActivity extends Activity implements View.OnClickListener, PullDownView.OnPullDownListener {

    private static final int WHAT_DID_LOAD_DATA = 0;
    private static final int WHAT_DID_REFRESH = 1;
    private static final int WHAT_DID_MORE = 2;
    private static final int PER_PAGE_SIZE = 25;
    private int currentPage = 1;
    private PullDownView mPullDownView;
    private ListView appListView;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private MyAdspter simAda = null;

    private TextView textView;
    private TextView settingView;
    private Handler mUIHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_DID_LOAD_DATA: {
                    List<Map<String, Object>> listData = (List<Map<String, Object>>) msg.obj;
                    if (!listData.isEmpty()) {
                        MyActivity.this.list.addAll(listData);
                        currentPage++;
                        simAda.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MyActivity.this, "无数据!", Toast.LENGTH_SHORT).show();
                    }
                    mPullDownView.notifyDidLoad();
                    break;
                }
                case WHAT_DID_REFRESH: {
                    currentPage = 1;
                    MyActivity.this.list = (List<Map<String, Object>>) msg.obj;
                    currentPage++;
                    simAda = new MyAdspter(MyActivity.this, MyActivity.this.list);
                    MyActivity.this.appListView.setAdapter(simAda);
                    simAda.notifyDataSetChanged();
                    mPullDownView.notifyDidRefresh();
                    break;
                }

                case WHAT_DID_MORE: {
                    List<Map<String, Object>> listData = (List<Map<String, Object>>) msg.obj;
                    if (listData.size() == 0) {
                        Toast.makeText(MyActivity.this, "无更多数据!", Toast.LENGTH_SHORT).show();
                    } else {
                        MyActivity.this.list.addAll(listData);
                        currentPage++;
                    }
                    simAda.notifyDataSetChanged();
                    mPullDownView.notifyDidMore();
                    break;
                }
            }

        }

    };

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


        mPullDownView = (PullDownView) findViewById(R.id.list_view);
        mPullDownView.setOnPullDownListener(this);
        appListView = mPullDownView.getListView();
        this.simAda = new MyAdspter(this, getData(1));
        this.appListView.setAdapter(this.simAda);
        this.appListView.setDividerHeight(12);
        mPullDownView.enableAutoFetchMore(true, 1);
        loadData();
        onRefresh();
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
        Toast toast = Toast.makeText(getApplicationContext(), R.string.app_toast_text, Toast.LENGTH_SHORT);
        TextView toastText = (TextView)layout.findViewById(R.id.toast_layout_text);
        toastText.setText("你点击的是："+text);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    /**
     * 填充数据
     *
     * @return
     */
    public List<Map<String, Object>> getData(Integer currentPage) {
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        List<Map<String, Object>> reaultList = new ArrayList<Map<String, Object>>();

        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        int indexCount = 0;


        for (int i = 0; i < packages.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            PackageInfo packageInfo = packages.get(i);
            // if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            //非系统应用
            map.put("image", packageInfo.applicationInfo.loadIcon(getPackageManager()));
            map.put("title", packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());
            if (packageInfo.versionName != null && !"".equals(packageInfo.versionName)) {
                String[] versions = packageInfo.versionName.split("\\.");
                if (versions.length >= 2) {
                    map.put("info", versions[0] + "." + versions[1]);
                } else {
                    map.put("info", versions[0] + ".0");
                }

            } else {
                map.put("info", "1.0");
            }
            list.add(map);
            // }
        }

        if (list.size() >= PER_PAGE_SIZE * currentPage.intValue()) {
            indexCount = PER_PAGE_SIZE * currentPage;
        } else {
            indexCount = list.size();
        }

        for (int i = PER_PAGE_SIZE * (currentPage - 1); i < indexCount; i++) {
            reaultList.add(list.get(i));
        }
        return reaultList;
    }

    private void loadData() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = mUIHandler.obtainMessage(WHAT_DID_LOAD_DATA);
                msg.obj = getData(1);
                msg.sendToTarget();
            }
        }).start();
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = mUIHandler.obtainMessage(WHAT_DID_REFRESH);
                msg.obj = getData(1);
                msg.sendToTarget();
            }
        }).start();
    }

    @Override
    public void onMore() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = mUIHandler.obtainMessage(WHAT_DID_MORE);
                msg.obj = getData(currentPage);
                msg.sendToTarget();
            }
        }).start();
    }
}
