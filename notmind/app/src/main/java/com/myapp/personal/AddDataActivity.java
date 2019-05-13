package com.myapp.personal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.MainActivity;
import com.myapp.R;
import com.myapp.Utils.DaoUtils;

public class AddDataActivity extends Activity {
    String user_id,name,sex,address,motto,code,result;
    int age,imgid;
    TextView id;
    EditText edt_name,edt_sex,edt_address,edt_motto,edt_age;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        id =findViewById(R.id.my_id);
        edt_name = findViewById(R.id.my_name);
        edt_sex = findViewById(R.id.my_sex);
        edt_address = findViewById(R.id.my_address);
        edt_motto = findViewById(R.id.my_motto);
        edt_age = findViewById(R.id.my_age);

        Button btn_add_data = findViewById(R.id.btn_add_data);
        Button btn_add_back = findViewById(R.id.p_add_back);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        imgid = intent.getIntExtra("imgid",R.drawable.img01);

        name = intent.getStringExtra("user_name");
        age = intent.getIntExtra("age",0);
        sex = intent.getStringExtra("sex");
        address = intent.getStringExtra("address");
        motto = intent.getStringExtra("motto");
        id.setText("账号：      "+user_id);
        edt_name.setText(name);
        edt_age.setText(age+"");
        edt_sex.setText(sex);
        edt_address.setText(address);
        edt_motto.setText(motto);

        AddDataActivity.ButtonOnclickListener listener = new AddDataActivity.ButtonOnclickListener();
       btn_add_data.setOnClickListener(listener);
       btn_add_back.setOnClickListener(listener);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            result = data.getString("result");
            code = data.getString("CODE");
            if ("200".equals(code)) {
                Toast.makeText(AddDataActivity.this, "保存成功", Toast.LENGTH_LONG).show();

                //跳转到显示信息页面显示
                Intent intent1 = new Intent(getApplicationContext(), ShowDataActivity.class);
                intent1.putExtra("user_id",user_id);
                intent1.putExtra("imgid",imgid);
                startActivity(intent1);
            }else if("400".equals(code)) {
                Toast.makeText(AddDataActivity.this, "保存失败" + code, Toast.LENGTH_SHORT).show();
            }

        }
    };
    private Runnable addData = new Runnable() {
        @Override
        public void run() {
            code = new DaoUtils().add_data(user_id, name, sex, age, address, motto);
            Message msg2 = new Message();
            Bundle data = new Bundle();
            data.putString("CODE", code);
            msg2.setData(data);
            handler.sendMessage(msg2);
        }
    };
    private class ButtonOnclickListener implements View.OnClickListener {
        public void onClick(View v) {
            if (v.getId() == R.id.btn_add_data) {
                //获取输入的值
                name = edt_name.getText().toString();
                sex = edt_sex.getText().toString();
                String sage = edt_age.getText().toString();
                if (sage.length() == 0) {
                    age = 0;
                }
                age = Integer.parseInt(sage);
                address = edt_address.getText().toString();
                motto = edt_motto.getText().toString();
                add_data();
                //把个人信息存到SharedPreferences中长期保存
                SharedPreferences session_pref= getSharedPreferences("userdata",MODE_PRIVATE);
                SharedPreferences.Editor session_editor=session_pref.edit();
                session_editor.putString("user_name",name);
                session_editor.putString("user_sex",sex);
                session_editor.putString("user_address",address);
                session_editor.putString("user_motto",motto);
                session_editor.putInt("user_age",age);
                session_editor.commit();

            }else if(v.getId() == R.id.p_add_back){
                Intent intent = new Intent(getApplicationContext(),ShowDataActivity.class);
                intent.putExtra("user_id",user_id);
                intent.putExtra("imgid",imgid);
                startActivity(intent);
                finish();
            }else{}
        }
    }
    public void add_data(){
        Thread thread2 = new Thread(addData);
        thread2.start();
    }


}
