package com.myapp.friend;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myapp.R;
import com.myapp.Utils.DaoUtils;


public class AddFriendActivity extends Activity {
    private EditText a_fid;
    private String code,user1_id,user2_id;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        //取出意图中的数据
        Intent intent = getIntent();
        user1_id = intent.getStringExtra("user_id");

        a_fid = findViewById(R.id.a_fid);

        Button btn_f_add_back = findViewById(R.id.btn_f_add_back);
        btn_f_add_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Button btn_add = findViewById(R.id.btn_add);
        MyListener listener = new MyListener();
        btn_add.setOnClickListener(listener);
    }
    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            user2_id = a_fid.getText().toString();
            if(user1_id.equals(user2_id)){
                Toast.makeText(AddFriendActivity.this, "不能添加自己哦", Toast.LENGTH_SHORT).show();
            }else {
                String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
                if(user2_id.matches(telRegex)) {
                    Thread thread = new Thread(find);
                    thread.start();
                }else{
                    Toast.makeText(AddFriendActivity.this, "输入的手机号格式不正确", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            code = data.getString("CODE");
            if ("200".equals(code)){
                Toast.makeText(AddFriendActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),ShowFriendActivity.class);
                intent.putExtra("user_id",user1_id);
                startActivity(intent);
                finish();
            }else if("500".equals(code)){
                Toast.makeText(AddFriendActivity.this, "你们已经是好友了", Toast.LENGTH_SHORT).show();
            }else if("300".equals(code)){
                Toast.makeText(AddFriendActivity.this, "要添加的用户不存在", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(AddFriendActivity.this, "添加失败"+code, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Runnable find = new Runnable() {
        @Override
        public void run() {
            code = new DaoUtils().add_friend(user1_id,user2_id);
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("CODE", code);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
