package com.myapp.friend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myapp.R;
import com.myapp.Utils.DaoUtils;

public class FriendDataActivity extends Activity {
    private String user_id, fid, fname, fsex, faddress, fmotto;
    private int fage;
    private TextView id, name, age, sex, address, motto, tv_all,power;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_data);
        //接收意图，显示数据
        final Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        fid = intent.getStringExtra("fid");
        fname = intent.getStringExtra("fname");
        fage = intent.getIntExtra("fage", 0);
        faddress = intent.getStringExtra("faddress");
        fsex = intent.getStringExtra("fsex");
        fmotto = intent.getStringExtra("fmotto");
        tv_all = findViewById(R.id.tv_all);
        tv_all.setText(fname + "：");
        id = findViewById(R.id.fid);
        name = findViewById(R.id.fname);
        age = findViewById(R.id.fage);
        sex = findViewById(R.id.fsex);
        address = findViewById(R.id.faddress);
        motto = findViewById(R.id.fmotto);
        power = findViewById(R.id.fpower);

        id.setText("账 号：  " + fid);
        name.setText("用户名： " + fname);
        age.setText("年 龄：  " + fage);
        sex.setText("性 别：    " + fsex);
        address.setText("地 址：    " + faddress);
        motto.setText("个性签名：   " + fmotto);
        //查出权限
        String code = new DaoUtils().show_power(user_id,fid);
        if("200".equals(code)){
            power.setText("权限：  有访问空间的权限");
        }else{
            power.setText("权限：  没有访问空间的权限");
        }

        //返回按钮
        TextView btn_back = findViewById(R.id.btn_f_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowFriendActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
                finish();
            }
        });
        //删除按钮
        TextView btn_f_delete = findViewById(R.id.btn_f_delete);
        btn_f_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //去删除好友页面
                Intent delete_f = new Intent(getApplicationContext(), DeleteFriendActivity.class);
                delete_f.putExtra("user_id", user_id);
                delete_f.putExtra("fid", fid);
                startActivity(delete_f);
            }
        });
        TextView btn_f_changeP = findViewById(R.id.btn_f_changePower);
        btn_f_changeP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //去修改权限页面
                Intent change = new Intent(getApplicationContext(), ChangePowerActivity.class);
                change.putExtra("user_id", user_id);
                change.putExtra("fid", fid);
                startActivity(change);
            }
        });
    }
}
