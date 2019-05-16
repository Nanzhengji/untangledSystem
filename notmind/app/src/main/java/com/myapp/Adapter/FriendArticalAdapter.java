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
import com.myapp.entities.FriendArtical;

import java.text.SimpleDateFormat;
import java.util.List;

public class FriendArticalAdapter extends ArrayAdapter {
    private final int resourceId;
    public FriendArticalAdapter(Context context, int resource, List<FriendArtical> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FriendArtical artical = (FriendArtical)getItem(position);//获得当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个View对象

        TextView a_uid = view.findViewById(R.id.a_uid);
        TextView a_atitle = view.findViewById(R.id.a_atitle);
        TextView a_date = view.findViewById(R.id.a_date);
        ImageView a_img = view.findViewById(R.id.a_img);
        a_img.setImageResource(artical.getImg());

        a_uid.setText(artical.getUser_name()+"\t("+artical.getUser_id()+")");
        a_atitle.setText(artical.getArtical_id().substring(4));

        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd");
        String  date = formatter.format(artical.getA_date());
        a_date.setText(date);
        return view;
    }
}
