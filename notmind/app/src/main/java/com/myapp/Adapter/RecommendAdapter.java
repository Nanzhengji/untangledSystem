package com.myapp.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.R;
import com.myapp.entities.RecommendInfo;

import java.util.List;

public class RecommendAdapter extends ArrayAdapter {
    private final int resourceId;
    public RecommendAdapter(Context context, int resource, List<RecommendInfo> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecommendInfo recommendInfo =(RecommendInfo) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个View对象
        TextView tv_title = view.findViewById(R.id.rec_title);//获取该布局内的文本视图
        TextView tv_content = view.findViewById(R.id.rec_content);//获取该布局内的文本视图

        tv_title.setText(recommendInfo.getRec_title());
        tv_content.setText(recommendInfo.getRec_content());
        return view;
    }
}
