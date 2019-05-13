package com.myapp.zone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myapp.R;
import com.myapp.Utils.DaoUtils;

import java.net.URLEncoder;

public class AddArticalActivity extends Activity {
    private EditText et_title,et_content;
    private String user_id,title,content,code;
    private int imgid;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artical);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        imgid = intent.getIntExtra("imgid",R.drawable.img01);
        et_title = findViewById(R.id.z_add_title);
        et_content = findViewById(R.id.z_add_content);

        Button btn_a_add_back = findViewById(R.id.z_add_back);
        btn_a_add_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Button btn_a_add = findViewById(R.id.z_add_ok);
        btn_a_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 title = et_title.getText().toString();
                 content = et_content.getText().toString();
                 //输入为空时提示输入不能为空
                if(title.length()== 0 ||content.length() == 0){
                    Toast.makeText(getApplicationContext(),"输入不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    try{
                        code = new DaoUtils().add_artical(user_id, title, content);
                    }catch(Exception e){
                        Log.d("syso","AddArticalActivity exception"+e.toString());
                    }
                    if("200".equals(code)){
                        Toast.makeText(getApplicationContext(),"发表成功",Toast.LENGTH_SHORT).show();
                        //发表成功，到显示动态的页面
                        Intent show_a = new Intent(getApplicationContext(),ShowMyArticalActivity.class);
                        show_a.putExtra("user_id",user_id);
                        show_a.putExtra("imgid",imgid);
                        startActivity(show_a);
                        finish();
                    }else{
                        //Toast.makeText(getApplicationContext(),"发表失败"+code,Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"发表失败，不能包括图片和表情",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
