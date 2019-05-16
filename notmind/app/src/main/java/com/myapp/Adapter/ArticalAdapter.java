package com.myapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.R;
import com.myapp.entities.Artical;

import java.util.List;

public class ArticalAdapter extends ArrayAdapter {
    private final int resourceId;
    public ArticalAdapter( Context context, int resource,List<Artical> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        Artical artical = (Artical)getItem(position);//获得当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个View对象
        TextView a_title = view.findViewById(R.id.a_title);
        TextView a_content = view.findViewById(R.id.a_content);
        ImageView a_img = view.findViewById(R.id.a_img);
        a_img.setImageResource(artical.getImg());
        a_title.setText(artical.getArtical_id().substring(4));
        a_content.setText(artical.getContent());
        return view;
    }
}
