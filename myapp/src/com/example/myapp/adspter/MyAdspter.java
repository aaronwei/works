package com.example.myapp.adspter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.myapp.R;

import java.util.List;
import java.util.Map;

/**
 * Created by aaron on 2015/2/7.
 */
public class MyAdspter extends BaseAdapter {

    private List<Map<String,Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public MyAdspter(Context context,List<Map<String,Object>> data){
        this.context = context;
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
            zujian.view=(Button)view.findViewById(R.id.view);
            zujian.info=(TextView)view.findViewById(R.id.info);
            view.setTag(zujian);
        }else{
            zujian = (Zujian)view.getTag();
        }
        //绑定数据
        zujian.image.setBackgroundResource((Integer)data.get(i).get("image"));
        zujian.title.setText((String)data.get(i).get("title"));
        zujian.info.setText((String)data.get(i).get("info"));
        return view;
    }

}
