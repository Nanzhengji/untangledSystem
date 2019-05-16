package com.myapp.zone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myapp.R;
import com.myapp.Utils.DaoUtils;
import com.myapp.entities.Artical;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateArticalActivity extends Activity {
    private String user_id, artical_id, a_content,code;
    private int imgid;
    private TextView tv_title;
    private EditText et_content;
    private Date a_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_artical);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        imgid = intent.getIntExtra("imgid", R.drawable.img01);
        artical_id = intent.getStringExtra("artical_id");
        a_content = intent.getStringExtra("a_content");

        tv_title = findViewById(R.id.update_title);
        tv_title.setText("主题：\t\t\t\t"+artical_id.substring(4));
        et_content = findViewById(R.id.update_content);
        et_content.setText(a_content);
        Button btn_update = findViewById(R.id.update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取值，保存到数据库
                String content = et_content.getText().toString();
                if(content.length() == 0){
                    Toast.makeText(getApplicationContext(),"动态内容不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    code = new DaoUtils().update_artical(artical_id,content);
                    if("200".equals(code)){
                        Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                        //再查出来
                        String result = new DaoUtils().find_artical(artical_id);
                        if(!"400".equals(result)){
                            Artical artical1 = new Gson().fromJson(result, Artical.class);
                            artical_id = artical1.getArtical_id();
                            a_content= artical1.getContent();
                            a_date = artical1.getArtical_date();
                            //将日期转化成yyyy-MM-dd格式
                            SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd");
                            String  dString = formatter.format(a_date);
                            //修改成功，到显示动态的页面
                            Intent intent = new Intent(getApplicationContext(),ArticalDataActivity.class);
                            intent.putExtra("user_id",user_id);
                            intent.putExtra("artical_id",artical_id);
                            intent.putExtra("a_content",a_content);
                            intent.putExtra("a_date",dString);
                            intent.putExtra("imgid",imgid);
                            startActivity(intent);
                            finish();
                        }else{
                            Log.d("syso","result:"+result);
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"发表失败,不能包含图片和表情",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}