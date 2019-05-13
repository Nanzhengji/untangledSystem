package com.myapp.personal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myapp.MainActivity;
import com.myapp.R;
import com.myapp.Utils.DaoUtils;


public class RegistActivity extends Activity {
    private EditText editText, editText2, editText3;
    public String user_id, password, rePassword, code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载资源文件
        setContentView(R.layout.activity_regist);
        //设置权限，解决ANR问题
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //找到要操作的控件
        Button btn_regist = findViewById(R.id.btn_regist);
        editText =  findViewById(R.id.edittext);
        editText2 =  findViewById(R.id.edittext2);
        editText3 =  findViewById(R.id.edittext3);
        //设置按钮的点击事件
        MyListener listener = new MyListener();
        btn_regist.setOnClickListener(listener);
        Button btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            code = data.getString("CODE");
            //判断服务器端传回的响应码，根据不同的响应码做不同操作（200用户注册成功，500输入的账号已有人使用，或已经注册过，400注册失败）
            if (code.equals("200")) {
                //注册成功，跳转到登录页面
                Toast.makeText(RegistActivity.this, "注册成功,去登录...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else if (code.equals("500")) {
                //账号已经存在
                Toast.makeText(RegistActivity.this, "账号已存在，去登陆...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(RegistActivity.this, "注册失败"+code, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Runnable regist = new Runnable() {
        @Override
        public void run() {
            code = new DaoUtils().regist(user_id, password);
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("CODE", code);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };
    private class MyListener implements View.OnClickListener {
        public void onClick(View v) {
            user_id = editText.getText().toString();//获取用户输入的用户账号
            password = editText2.getText().toString();//获取用户输入的密码
            rePassword = editText3.getText().toString();//获取确认的密码
           if (user_id.length() == 0 || password.length() == 0 ||rePassword.length()==0 ) {
                Toast.makeText(RegistActivity.this, "账号、密码不能为空", Toast.LENGTH_SHORT).show();
           } else {
               String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
               if(user_id.matches(telRegex)){
                   if (!password.equals(rePassword)) {
                       Toast.makeText(RegistActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                   } else {

                       //调用DaoUtils的注册方法，向服务器端传入数据，将用户信息保存到数据库
                       Thread thread = new Thread(regist);
                       thread.start();
                   }
               }else{
                   Toast.makeText(RegistActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
               }
            }
        }
    }

}