package com.myapp.zone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myapp.Adapter.CommentAdapter;
import com.myapp.R;
import com.myapp.Utils.DaoUtils;
import com.myapp.entities.Comment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class FriendArticalDataActivity extends Activity {
    private String user_id,fuid,title,content,date,code,c_content,commment_id;
    private ListView lv_comment;
    private ArrayList<Comment> commentList = new ArrayList<Comment>();
    CommentAdapter adapter;
    private TextView tv_favor;
    private EditText et_content;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_artical_data);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView a_title = findViewById(R.id.f_a_title);
        TextView a_content = findViewById(R.id.f_a_content);
        TextView a_date = findViewById(R.id.f_a_date);
        et_content = findViewById(R.id.et_fcontent);
        tv_favor=findViewById(R.id.f_a_favor);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        fuid =intent.getStringExtra("fuid");
        title = intent.getStringExtra("artical_id");
        content = intent.getStringExtra("a_content");
        date = intent.getStringExtra("a_date");

        a_title.setText("\t\t\t\t"+title.substring(4));
        a_content.setText("\t\t\t\t"+content);
        a_date.setText("ID: "+fuid+"\t\t发表时间："+date);
        String count = new DaoUtils().count_favor(title);
        tv_favor.setText("赞 "+count);

        //加载评论信息
        init_comment();

        ListView lv_comment = findViewById(R.id.f_a_comment_list);

        if(commentList.size() != 0) {
            //给listview添加适配器，添加数据
            adapter = new CommentAdapter(getApplicationContext(), R.layout.item_comment, commentList);
            lv_comment.setAdapter(adapter);
            lv_comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    //c_id是评论的id
                    TextView c_id =view.findViewById(R.id.c_id);
                    String id = c_id.getText().toString();
                    //u_id是评论人的id
                    TextView u_id = view.findViewById(R.id.u_id);
                    String uid =u_id.getText().toString();

                    if(uid.equals(user_id)){
                        Toast.makeText(getApplicationContext(),"删除...",Toast.LENGTH_SHORT).show();
                        //在数据库删除
                        String code = new DaoUtils().delete_comment(user_id,title,id);
                        if("200".equals(code)){
                            commentList.remove(i);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(),"已删除",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"不能删除别人发的评论",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


        tv_favor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点赞
                String result = new DaoUtils().favor(user_id,title);
                if("200".equals(result)){
                    Toast.makeText(getApplicationContext(),"已赞",Toast.LENGTH_SHORT).show();
                    String count = new DaoUtils().count_favor(title);
                    tv_favor.setText("赞 "+count);
                    Drawable like= getResources().getDrawable(R.drawable.like_press);
                    tv_favor.setCompoundDrawablesWithIntrinsicBounds(like,null,null,null);
                }
            }
        });
        //评论
        TextView btn_comment = findViewById(R.id.tv_f_comment);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入的内容
                c_content = et_content.getText().toString();
                if(c_content.length() != 0){
                commment_id =user_id.substring(7)+c_content.substring(1);

                String result = new DaoUtils().add_comment(user_id,title,commment_id,c_content);

                if("200".equals(result)){
                    Intent intent = new Intent(getApplicationContext(),FriendArticalDataActivity.class);
                    intent.putExtra("user_id",user_id);
                    intent.putExtra("fuid",fuid);
                    intent.putExtra("artical_id",title);
                    intent.putExtra("a_content",content);
                    intent.putExtra("a_date",date);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"发表失败"+result,Toast.LENGTH_SHORT).show();
                }
            }else{
                    Toast.makeText(getApplicationContext(),"没有输入评论内容",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    public void delete_comment(View view){
        Toast.makeText(getApplicationContext(),"删除",Toast.LENGTH_SHORT).show();

    }


    public void init_comment(){
        ArrayList<Comment> comments = new ArrayList<Comment>();
        Type commentType = new TypeToken<ArrayList<Comment>>(){}.getType();
        try{
            String result = new DaoUtils().show_comment(title);
            if("400".equals(result)){
            }else{
                comments = new Gson().fromJson(result, commentType);
                for(Comment comment : comments){
                    Comment comment1 = new Comment(comment.getUser_id(),comment.getComment_id(),comment.getC_content(),comment.getComment_date());
                    commentList.add(comment1);
                }
            }
        }catch (Exception e){
            Log.d("syso",e.toString());
        }
    }
}

