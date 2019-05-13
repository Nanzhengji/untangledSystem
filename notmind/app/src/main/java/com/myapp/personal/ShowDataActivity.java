package com.myapp.personal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;

import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.myapp.MainActivity;
import com.myapp.R;
import com.myapp.Utils.DaoUtils;
import com.myapp.entities.User;


public class ShowDataActivity extends Activity {
    public static final int SELECT_PIC = 1;
    String user_id,name,sex,address,motto,result;
    private ImageView iv_head;
    int age,imgid;
    TextView id;
    User user;
    TextView my_id,tv_name,tv_sex,tv_age,tv_address,tv_motto;
    private SharedPreferences pref;
    //使用SharedPreferences.Editor进行存储
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //找到要操作的控件
        my_id =findViewById(R.id.my_id);
        tv_name = findViewById(R.id.tv_name);
        tv_sex = findViewById(R.id.tv_sex);
        tv_address = findViewById(R.id.tv_address);
        tv_motto = findViewById(R.id.tv_motto);
        tv_age = findViewById(R.id.tv_age);
        TextView tv_add_data = findViewById(R.id.tv_add_data);
        //获得user_id
        final Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        imgid = intent.getIntExtra("imgid",R.drawable.img01);
        my_id.setText("ID：  "+user_id);
        //找到头像控件
        iv_head = findViewById(R.id.iv_head);
        iv_head.setImageResource(imgid);

        TextView changePhoto =findViewById(R.id.change_photo);
        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ChangePhotoActivity.class);
                startActivityForResult(intent,SELECT_PIC);
            }
        });
        //先判断网络状态
        if (!checkNetwork()) {
            Toast.makeText(getApplicationContext(), "网络无连接，请设置网络", Toast.LENGTH_LONG).show();
            Intent intent2 = new Intent("android.settings.WIRELESS_SETTINGS");
            startActivity(intent2);
            return;
        }

        pref= getSharedPreferences("userdata",MODE_PRIVATE);
        editor=pref.edit();
        if(editor != null){
        //说明以前查过直接显示
            name=pref.getString("user_name","佚名");
            age=pref.getInt("user_age",0);
            sex=pref.getString("user_sex","男");
            address=pref.getString("user_address","中国");
            motto=pref.getString("user_motto","每个人都是独一无二的自己");

            tv_name.setText("用户名：  "+name);
            tv_sex.setText("性别：  "+sex);
            tv_address.setText("地址：  "+address);
            tv_motto.setText("个性签名：  \n\n\t\t\t\t"+motto);
            tv_age.setText("年龄：  "+age);
        }else{
            //从数据库中查出用户数据
            Thread thread = new Thread(findUser);
            thread.start();
        }


        ButtonOnclickListener listener = new ButtonOnclickListener();
        tv_add_data.setOnClickListener(listener);

    }
    //更换头像的带结果的意图返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if(requestCode == SELECT_PIC && resultCode == RESULT_OK){
            imgid = data.getIntExtra("image",-1);
            if(imgid != -1){
                iv_head.setImageResource(imgid);
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            result = data.getString("result");

            Gson gson = new Gson();
            user = gson.fromJson(result, User.class);
            //取出各字段，设为Text值
            name = user.getUser_name().equals("null")?"佚名":user.getUser_name();
            sex = user.getSex().equals("null")?"男":user.getSex();
            address = user.getAddress().equals("null")? "中国":user.getAddress();
            motto = user.getMotto().equals("null")?"每个人都是独一无二的自己。":user.getMotto();

            age = user.getAge();
            tv_name.setText("用户名：  "+name);
            tv_sex.setText("性别：  "+sex);
            tv_address.setText("地址：  "+address);
            tv_motto.setText("个性签名：  \n\n\t\t\t\t"+motto);
            tv_age.setText("年龄：  "+age);

            //把个人信息存到SharedPreferences中长期保存
            SharedPreferences session_pref= getSharedPreferences("userdata",MODE_PRIVATE);
            SharedPreferences.Editor session_editor=session_pref.edit();
            session_editor.putString("user_name",name);
            session_editor.putString("user_sex",sex);
            session_editor.putString("user_address",address);
            session_editor.putString("user_motto",motto);
            session_editor.putInt("user_age",age);
            session_editor.commit();
        }
    };
    private Runnable findUser = new Runnable() {
        @Override
        public void run() {
            result = new DaoUtils().my_data(user_id);
            Message msg1 = new Message();
            Bundle data = new Bundle();
            data.putString("result", result);
            msg1.setData(data);
            handler.sendMessage(msg1);
        }
    };

    public class ButtonOnclickListener implements View.OnClickListener {
        public void onClick(View v) {
            //转到完善信息页面
            Intent intent = new Intent(getApplicationContext(),AddDataActivity.class);
            intent.putExtra("user_id",user_id);
            intent.putExtra("user_name",name);
            intent.putExtra("sex",sex);
            intent.putExtra("age",age);
            intent.putExtra("address",address);
            intent.putExtra("motto",motto);
            intent.putExtra("imgid",imgid);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences pref= getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putInt("imgid",imgid);
        Log.d("syso","showdata:"+imgid);
        editor.commit();
       Intent intent = new Intent(getApplicationContext(),MainActivity.class);
       startActivity(intent);
       finish();
    }
    //检查网络状态
    private boolean checkNetwork() {
        ConnectivityManager conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = conn.getActiveNetworkInfo();
        if (net != null && net.isConnected()) {
            return true;
        }
        return false;
    }
}



