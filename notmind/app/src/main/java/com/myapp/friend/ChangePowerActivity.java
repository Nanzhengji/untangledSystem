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

public class ChangePowerActivity extends Activity {
    private EditText et_power_fid;
    private String user_id,fid,code;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_power);
        et_power_fid = findViewById(R.id.et_power_fid);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        fid = intent.getStringExtra("fid");
        if(fid != null){
            et_power_fid.setText(fid);
        }

        Button f_change_back = findViewById(R.id.f_change_back);
        f_change_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Button btn_change_power = findViewById(R.id.btn_change_power);
        MyListener listener = new MyListener();
        btn_change_power.setOnClickListener(listener);

    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            code = data.getString("CODE");
            if ("200".equals(code)){
                Toast.makeText(ChangePowerActivity.this, "修改权限成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),ShowFriendActivity.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(ChangePowerActivity.this, "修改权限失败"+code, Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Runnable change = new Runnable() {
        @Override
        public void run() {
            code = new DaoUtils().change_power(user_id,fid);
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
            fid = et_power_fid.getText().toString();
            Thread thread = new Thread(change);
            thread.start();
        }
    }


}
