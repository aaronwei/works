package com.example.myapp.adspter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapp.R;

import java.util.List;
import java.util.Map;

/**
 * Created by aaron on 2015/2/7.
 */
public class MyAdspter extends BaseAdapter {

    private List<Map<String,Object>> data;
    private LayoutInflater layoutInflater;

    public MyAdspter(Context context,List<Map<String,Object>> data){

        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Zujian zujian = null;
        if(view==null){
            zujian = new Zujian();
            view = layoutInflater.inflate(R.layout.list,null);
            zujian.image = (ImageView)view.findViewById(R.id.image);
            zujian.title=(TextView)view.findViewById(R.id.title);
            zujian.info=(TextView)view.findViewById(R.id.info);
            zujian.lastUpdateTime = (TextView)view.findViewById(R.id.lastUpdateTime);
            zujian.isRun = (TextView)view.findViewById(R.id.isRun);
            view.setTag(zujian);
        }else{
            zujian = (Zujian)view.getTag();
        }
        //绑定数据
        zujian.image.setImageDrawable((Drawable) data.get(i).get("image"));
        zujian.title.setText((String)data.get(i).get("title"));
        zujian.info.setText((String)data.get(i).get("info"));
        zujian.lastUpdateTime.setText("最后更新时间："+(String)data.get(i).get("lastUpdateTime"));
        zujian.isRun.setText((String)data.get(i).get("isRun"));
        if (i != 0) {
            view.setBackgroundColor(Color.parseColor("#66CCCC"));
        }
        return view;
    }

    public static class Zujian {
        ImageView image;
        TextView title;
        TextView info;
        TextView lastUpdateTime;
        TextView isRun;
    }

}
