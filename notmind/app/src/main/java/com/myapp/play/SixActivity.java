package com.myapp.play;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myapp.R;
import com.myapp.Utils.DaoUtils;

import java.util.ArrayList;

public class SixActivity extends Activity {
    private EditText six_what,six_1,six_2,six_3,six_4,six_5,six_6;
    private Button six_btn;
    private String user_id,s_what,s1,s2,s3,s4,s5,s6;
    private ArrayList<String> strlist = new ArrayList<String>();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_six);

        Intent intent = getIntent();
        user_id=intent.getStringExtra("user_id");

        six_what = findViewById(R.id.six_what);
        six_1 =findViewById(R.id.six_1);
        six_2 = findViewById(R.id.six_2);
        six_3 = findViewById(R.id.six_3);
        six_4 = findViewById(R.id.six_4);
        six_5 = findViewById(R.id.six_5);
        six_6 = findViewById(R.id.six_6);
        six_btn = findViewById(R.id.six_btn);
        six_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s_what = six_what.getText().toString().trim();
                s1= six_1.getText().toString().trim();
                s2 = six_2.getText().toString().trim();
                s3 = six_3.getText().toString().trim();
                s4 = six_4.getText().toString().trim();
                s5 = six_5.getText().toString().trim();
                s6 = six_6.getText().toString().trim();
                if(s_what.length()==0 || s1.length()==0 || s2.length()==0 ||s3.length()==0 || s4.length()==0 || s5.length()==0 || s6.length()==0){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SixActivity.this);
                    builder1.setTitle("温馨提示");
                    builder1.setMessage("你还没有完成输入哦  ^~^ ");
                    builder1.setPositiveButton("确定", null);
                    builder1.show();
                }else{

                    strlist.add(s1);
                    strlist.add(s2);
                    strlist.add(s3);
                    strlist.add(s4);
                    strlist.add(s5);
                    strlist.add(s6);
                    Intent intent = new Intent(getBaseContext(),ShowResultActivity.class);
                    intent.putExtra("title",s_what);
                    intent.putStringArrayListExtra("strlist", strlist);

                    //保存到数据库
                    new DaoUtils().save_play(user_id,s_what);

                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}
