package com.myapp.personal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.MainActivity;
import com.myapp.R;
import com.myapp.Utils.CodeUtils;
import com.myapp.Utils.DaoUtils;

import java.net.URLEncoder;

public class LoginActivity extends Activity {
    private EditText editText, editText2,et_code;
    private String user_id, password,validateCode,code;
    private CodeUtils codeUtils;
    String showCode;
    ImageView iv_code;
    private CheckBox cb;
    private SharedPreferences pref;
    //使用SharedPreferences.Editor进行存储
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载资源文件
        setContentView(R.layout.activity_login);
        //当程序的主线程因为IO读写，网络阻塞，外部存储设备被独占，系统负荷（load）过高，都有可能导致ANR（activity not response）。
        // 这个时候，可以用StrictMode，利用严苛模式来解决问题：
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        pref= getSharedPreferences("autologin",MODE_PRIVATE);
        editor=pref.edit();
        //找到待操作的控件
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_getcode = findViewById(R.id.btn_getcode);
        View btn2 = findViewById(R.id.regist);
        editText =  findViewById(R.id.edittext);
        editText2 =  findViewById(R.id.edittext2);
        et_code = findViewById(R.id.et_code);
        iv_code = findViewById(R.id.iv_code);
        cb = findViewById(R.id.cb_remember);

        boolean isRemember=pref.getBoolean("autologin",false);
        if(isRemember){
            String account=pref.getString("user_id",editText.getText().toString());
            String password=pref.getString("password",editText2.getText().toString());
            editText.setText(account);
            editText2.setText(password);
            cb.setChecked(true);
        }
        //给按钮添加点击事件
        ButtonOnclickListener listener = new ButtonOnclickListener();
        btn_login.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        //点击获取验证码，生成验证码
        btn_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取验证码
                codeUtils = CodeUtils.getInstance();
                Bitmap bitmap = codeUtils.createBitmap();
                iv_code.setImageBitmap(bitmap);
                showCode = codeUtils.getCode();//获取图片显示的验证码
            }
        });
        TextView tv_forget = findViewById(R.id.tv_forget_pwd);
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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

    public class ButtonOnclickListener implements View.OnClickListener {
        public void onClick(View v) {
            //点击登录按钮
            if (v.getId() == R.id.btn_login) {
                user_id = editText.getText().toString();//获取用户输入的用户名
                password = editText2.getText().toString();//获取用户输入的密码
                validateCode = et_code.getText().toString();//获取用户输入的验证码
                if (user_id.length() == 0 || password.length() == 0 || validateCode.length()==0) {
                    Toast.makeText(LoginActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    //先判断验证码(不区分大小写)
                    if(!showCode.equalsIgnoreCase(validateCode)){
                        Toast.makeText(LoginActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "登录中...", Toast.LENGTH_LONG).show();
                        code = new DaoUtils().login(user_id,password);

                        if (("200".equals(code))) {
                            //登陆成功，将账号密码保存到sharedPreferences对象中，跳转到“主”页面
                            //如果记住密码被选中，那么就将账号密码存储起来
                            if(cb.isChecked()){
                                editor.putBoolean("autologin",true);
                                editor.putString("user_id",user_id);
                                editor.putString("password",password);
                            }else{
                                editor.clear();
                            }
                            //数据提交
                            editor.commit();
                            SharedPreferences session_pref= getSharedPreferences("userInfo",MODE_PRIVATE);
                            SharedPreferences.Editor session_editor=session_pref.edit();
                            session_editor.putString("user_id",user_id);
                            session_editor.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }  else {
                            //登陆失败
                            Toast.makeText(LoginActivity.this, "账号或密码错误"+code, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }else if (v.getId() == R.id.regist) {
                //点击注册按钮，跳转到注册页面
                Intent intent = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
            }
        }
    }
}
