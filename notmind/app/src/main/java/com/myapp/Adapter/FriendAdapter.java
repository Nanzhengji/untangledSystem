package com.myapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.R;
import com.myapp.entities.Friend;
import java.util.List;


public class FriendAdapter extends ArrayAdapter{
    private final int resourceId;
    public FriendAdapter( Context context, int resource, List<Friend> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Friend friend = (Friend)getItem(position);// 获取当前项的Friend实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个View对象
        //TextView tv_f_id = view.findViewById(R.id.tv_f_id);
        TextView tv_f_name = view.findViewById(R.id.tv_f_name);//获取该布局内的文本视图
        ImageView iv_f_img = view.findViewById(R.id.item_f_img);//获取该布局内的图片视图
        //tv_f_id.setText(friend.getFid());
        tv_f_name.setText(friend.getFname()+"("+friend.getFid()+")");
        iv_f_img.setImageResource(friend.getFimgid());//为图片视图设置内容

        return view;
    }
}
