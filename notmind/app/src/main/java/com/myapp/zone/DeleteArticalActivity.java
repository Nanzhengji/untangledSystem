package com.myapp.zone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myapp.R;
import com.myapp.Utils.DaoUtils;

public class DeleteArticalActivity extends Activity {
    private String user_id,artical_id,code,title;
    private EditText et_aid;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_artical);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final EditText z_del_id = findViewById(R.id.z_del_id);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        artical_id = intent.getStringExtra("artical_id");
        z_del_id.setText(artical_id);
        Button btn_a_back = findViewById(R.id.btn_a_back);
        btn_a_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Button btn_a_delete = findViewById(R.id.btn_a_delete);
        btn_a_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = z_del_id.getText().toString();
                code = new DaoUtils().delete_artical(user_id,title);
                if("200".equals(code)){
                    Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                    Intent show_a = new Intent(getApplicationContext(),ShowMyArticalActivity.class);
                    show_a.putExtra("user_id",user_id);
                    startActivity(show_a);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"删除失败"+code,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
