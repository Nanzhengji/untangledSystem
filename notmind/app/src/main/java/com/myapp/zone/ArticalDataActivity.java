package com.myapp.zone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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

public class ArticalDataActivity extends Activity {
    private String user_id,title,content,date,code,c_content,commment_id;
    private ListView lv_comment;
    private ArrayList<Comment> commentList = new ArrayList<Comment>();
    CommentAdapter adapter;
    private EditText et_content;
    private TextView tv_favor;
    private int imgid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artical_data);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView a_title = findViewById(R.id.a_title);
        TextView a_content = findViewById(R.id.a_content);
        TextView a_date = findViewById(R.id.tv_date);
        et_content = findViewById(R.id.et_content);
        tv_favor=findViewById(R.id.tv_favor);

        final Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        title = intent.getStringExtra("artical_id");
        content = intent.getStringExtra("a_content");
        date = intent.getStringExtra("a_date");
        imgid =intent.getIntExtra("imgid",R.drawable.img01);
        init_comment(title);

        a_title.setText("\t\t\t\t"+title);
        a_content.setText("\t\t\t\t"+content);
        a_date.setText("ID："+user_id+"\t\t发表时间："+date);

        String count = new DaoUtils().count_favor(title);
        tv_favor.setText("赞 "+count);

        lv_comment = findViewById(R.id.lv_comment_list);

        if(commentList.size() != 0) {
            //给listview添加适配器，添加数据
            adapter = new CommentAdapter(getApplicationContext(), R.layout.item_comment, commentList);
            lv_comment.setAdapter(adapter);
            //给listview添加点击事件的监听
            lv_comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                  /*
                  //删除（不管是不是自己发的评论，只要点击都可以删除，显然是不对的）
                    TextView c_id =view.findViewById(R.id.c_id);
                    String id = c_id.getText().toString();
                    //在数据库也删除
                    String code = new DaoUtils().delete_comment(user_id,title,id);
                    if("200".equals(code)){
                        commentList.remove(i);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(),"已删除",Toast.LENGTH_SHORT).show();
                    }*/

                    //不能删除别人发的评论（item_comment中c_id和u_id控件的visibility是gone不占页面空间）
                     //c_id是评论的id
                    TextView c_id =view.findViewById(R.id.c_id);
                    String id = c_id.getText().toString();
                    //u_id是评论人的id
                    TextView u_id = view.findViewById(R.id.u_id);
                    String uid =u_id.getText().toString();
                    //判断评论人是不是自己，是，说明此条评论是自己发的，则可以删除，否则提示不能删除
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

        //修改动态
        TextView btn_change = findViewById(R.id.show_a_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建意图，携带该条动态的信息，去修改页面
                Intent intent = new Intent(getApplicationContext(),UpdateArticalActivity.class);
                intent.putExtra("user_id",user_id);
                intent.putExtra("artical_id",title);
                intent.putExtra("a_content",content);
                intent.putExtra("a_date",date);
                intent.putExtra("imgid",imgid);
                startActivity(intent);
                finish();
            }
        });

        //删除动态
       TextView btn_del=findViewById(R.id.show_a_delete);
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = new DaoUtils().delete_artical(user_id,title);
                if("200".equals(code)){
                    Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                    //删除一条动态后跳转到显示动态列表页面
                    Intent show_a = new Intent(getApplicationContext(),ShowMyArticalActivity.class);
                    show_a.putExtra("user_id",user_id);
                    show_a.putExtra("imgid",imgid);
                    startActivity(show_a);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"删除失败"+code,Toast.LENGTH_SHORT).show();
                }
            }
        });
        //发表评论
        TextView btn_comment = findViewById(R.id.tv_comment);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入的内容
                c_content = et_content.getText().toString();
                if(c_content.length() == 0){
                    Toast.makeText(getApplicationContext(),"你还未输入评论内容",Toast.LENGTH_SHORT).show();
                }else{
                    commment_id =user_id.substring(7)+c_content.substring(1);
                    String result = new DaoUtils().add_comment(user_id,title,commment_id,c_content);

                    if("200".equals(result)){
                        Intent intent = new Intent(getApplicationContext(),ArticalDataActivity.class);
                        intent.putExtra("user_id",user_id);
                        intent.putExtra("artical_id",title);
                        intent.putExtra("a_content",content);
                        intent.putExtra("a_date",date);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"发表失败,不能包含图片和表情",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    //获取所有评论
    public void init_comment(String title){
        ArrayList<Comment> comments = new ArrayList<Comment>();
        Type commentType = new TypeToken<ArrayList<Comment>>(){}.getType();
        try{
            String result = new DaoUtils().show_comment(title);
            if("400".equals(result)){
                Log.d("syso","result是400");
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
