package com.myapp.friend;

import android.app.Activity;
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

public class DeleteFriendActivity extends Activity {
    private EditText et_fid;
    private String user_id,fid,code,intent_fid;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_friend);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        intent_fid= intent.getStringExtra("fid");
        et_fid = findViewById(R.id.et_fid);
        et_fid.setText(intent_fid);

        Button btn_f_delete_back = findViewById(R.id.btn_f_delete_back);
        btn_f_delete_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Button btn_delete = findViewById(R.id.btn_delete);
        MyListener listener = new MyListener();
        btn_delete.setOnClickListener(listener);
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            code = data.getString("CODE");
            if ("200".equals(code)){
                Toast.makeText(DeleteFriendActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(getApplicationContext(),ShowFriendActivity.class);
               intent.putExtra("user_id",user_id);
               startActivity(intent);
            }else{
                Toast.makeText(DeleteFriendActivity.this, "删除失败"+code, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Runnable delete = new Runnable() {
        @Override
        public void run() {
            code = new DaoUtils().delete_friend(user_id,fid);
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("CODE", code);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };
    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            fid = et_fid.getText().toString();
            Thread thread = new Thread(delete);
            thread.start();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
