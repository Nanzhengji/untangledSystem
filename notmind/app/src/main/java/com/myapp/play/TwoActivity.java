package com.myapp.play;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myapp.R;
import com.myapp.Utils.DaoUtils;

import java.util.ArrayList;

public class TwoActivity extends Activity {
     private EditText et_what,et_et1,et_et2;
     private Button two_btn;
     private String user_id,s_what,s1,s2;
    private ArrayList<String> strlist = new ArrayList<String>();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_two);

        Intent intent = getIntent();
        user_id=intent.getStringExtra("user_id");

        et_what = findViewById(R.id.et_what);
        et_et1 = findViewById(R.id.et_et1);
        et_et2 = findViewById(R.id.et_et2);
        two_btn = findViewById(R.id.two_btn);
        two_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s_what = et_what.getText().toString().trim();
                s1 = et_et1.getText().toString().trim();
                s2 = et_et2.getText().toString().trim();
                if(s_what.length()==0 || s1.length()==0 || s2.length()==0){
                   AlertDialog.Builder builder1 = new AlertDialog.Builder(TwoActivity.this);
                    builder1.setTitle("温馨提示");
                    builder1.setMessage("你还没有完成输入哦  ^~^ ");
                    builder1.setPositiveButton("确定", null);
                    builder1.show();
                }else {

                    strlist.add(s1);
                    strlist.add(s2);

                    Intent intent = new Intent(getBaseContext(),ShowResultActivity.class);
                    intent.putExtra("title",s_what);
                    intent.putStringArrayListExtra("strlist",  strlist);
                    //保存到数据库
                    new DaoUtils().save_play(user_id,s_what);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}
