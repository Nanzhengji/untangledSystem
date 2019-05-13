package com.myapp.personal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.R;
import com.myapp.Utils.DaoUtils;
import com.myapp.play.FiveActivity;

public class ChangePwdActivity extends Activity {
    private EditText pwd1,pwd2,rpwd2;
    private String s_pwd1,s_pwd2,s_rpwd2,user_id,code;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        //设置网络权限，防止ANR（应用程序无响应）
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        pwd1 = findViewById(R.id.pwd1);
        pwd2 = findViewById(R.id.pwd2);
        rpwd2 = findViewById(R.id.rpwd2);
        Button btn_p_changepwd_back = findViewById(R.id.btn_p_changepwd_back);
        btn_p_changepwd_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Button btn_change_pwd = findViewById(R.id.btn_change_pwd);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user_id = bundle.getString("user_id");

        MyListener listener = new MyListener();
        btn_change_pwd.setOnClickListener(listener);

        TextView reset_pwd = findViewById(R.id.reset_pwd);
        reset_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePwdActivity.this);
                builder.setTitle("重置密码提醒");
                builder.setMessage("正在将您的密码重置为123456      [为了安全，稍后请修改您的密码] ！");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String result = new DaoUtils().reset_password(user_id);
                        if("200".equals(result)){
                            Toast.makeText(getApplicationContext(), "重置密码成功，请重新登录", Toast.LENGTH_SHORT).show();
                            //清空SharedPreferences内容，到登录页面
                            SharedPreferences pref= getSharedPreferences("autologin",MODE_PRIVATE);
                            SharedPreferences.Editor editor=pref.edit();
                            editor.putBoolean("autologin",false);
                            editor.commit();
                            SharedPreferences pref1= getSharedPreferences("userInfo",MODE_PRIVATE);
                            SharedPreferences.Editor editor1=pref1.edit();
                            editor1.clear();
                            editor1.commit();
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            code = data.getString("CODE");
            if ("200".equals(code)) {
                Toast.makeText(getApplicationContext(), "修改密码成功，请重新登录", Toast.LENGTH_SHORT).show();
                //，清空SharedPreferences内容，到登录页面
                SharedPreferences pref= getSharedPreferences("autologin",MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();
                editor.putBoolean("autologin",false);
                editor.commit();
                SharedPreferences pref1= getSharedPreferences("userInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor1=pref1.edit();
                editor1.clear();
                editor1.commit();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            } else if ("500".equals(code)) {
                Toast.makeText(getApplicationContext(), "原始密码输入错误", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "密码修改失败" + code, Toast.LENGTH_SHORT).show();
            }

        }
    };
    private Runnable change = new Runnable() {
        @Override
        public void run() {
            code = new DaoUtils().change_password(user_id, s_pwd1, s_pwd2);
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("CODE", code);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };
    private class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            s_pwd1 = pwd1.getText().toString();
            s_pwd2 = pwd2.getText().toString();
            s_rpwd2 = rpwd2.getText().toString();
            if (s_pwd1.length() == 0 || s_pwd2.length() == 0 || s_rpwd2.length() == 0) {
                Toast.makeText(getApplicationContext(), "原始密码、新密码、确认密码不能为空", Toast.LENGTH_SHORT).show();
            } else {
                if (s_pwd2.equals(s_rpwd2)) {
                    //修改密码是耗时操作，在子线程中做，为了避免由于线程执行顺序的问题导致父线程比子线程早执行（还没有得到返回的code，就给code赋值）
                    //所以使用handler消息机制
                    Thread thread = new Thread(change);
                    thread.start();
                } else {
                    Toast.makeText(getApplicationContext(), "确认密码和新密码输入不一致", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
