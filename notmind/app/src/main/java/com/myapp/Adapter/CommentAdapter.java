package com.myapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.R;
import com.myapp.Utils.DaoUtils;
import com.myapp.entities.Artical;
import com.myapp.entities.Comment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommentAdapter extends ArrayAdapter {
    private final int resourceId;
    public CommentAdapter( Context context, int resource,List<Comment> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = (Comment) getItem(position);//获得当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个View对象

        TextView c_user = view.findViewById(R.id.c_user);
        TextView c_content = view.findViewById(R.id.c_content);
        TextView c_id = view.findViewById(R.id.c_id);
        TextView u_id = view.findViewById(R.id.u_id);

        c_id.setText(comment.getComment_id());
        u_id.setText(comment.getUser_id());
        //将日期转化成yyyy-MM-dd格式
        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String  date = formatter.format(comment.getComment_date());
        c_user.setText(comment.getUser_id()+"\t\t("+date+")");
        c_content.setText(comment.getC_content());

        return view;
    }
}
