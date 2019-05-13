package com.myapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.personal.LoginActivity;
import com.myapp.play.FiveActivity;
import com.myapp.play.FourActivity;
import com.myapp.play.ShowHelpActivity;
import com.myapp.play.SixActivity;
import com.myapp.play.ThreeActivity;
import com.myapp.play.TwoActivity;

public class PlayActivity extends Activity {
    private Button btn_ok;
    private RadioGroup rg_group;
    private RadioButton rb_two,rb_three,rb_four,rb_five,rb_six;
    private String user_id;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_play);

        SharedPreferences myPreferences = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        user_id = myPreferences.getString("user_id",null);

        btn_ok = findViewById(R.id.btn_ok);
        rg_group = findViewById(R.id.rg_group);
        rb_two = findViewById(R.id.rb_two);
        rb_three = findViewById(R.id.rb_three);
        rb_four = findViewById(R.id.rb_four);
        rb_five = findViewById(R.id.rb_five);
        rb_six = findViewById(R.id.rb_six);
        TextView tv_show_help = findViewById(R.id.tv_show_help);
        tv_show_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ShowHelpActivity.class);
                startActivity(intent);
            }
        });

        if(user_id != null) {
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //获取radiobutton的值
                    if (rb_two.isChecked()) {
                        //跳转到两个选项的页面
                        Intent two = new Intent(getApplicationContext(), TwoActivity.class);
                        two.putExtra("user_id", user_id);
                        startActivity(two);
                    } else if (rb_three.isChecked()) {
                        Intent three = new Intent(getApplicationContext(), ThreeActivity.class);
                        three.putExtra("user_id", user_id);
                        startActivity(three);
                    } else if (rb_four.isChecked()) {
                        Intent four = new Intent(getApplicationContext(), FourActivity.class);
                        four.putExtra("user_id", user_id);
                        startActivity(four);
                    } else if (rb_five.isChecked()) {
                        Intent five = new Intent(getApplicationContext(), FiveActivity.class);
                        five.putExtra("user_id", user_id);
                        startActivity(five);
                    } else if (rb_six.isChecked()) {
                        Intent six = new Intent(getApplicationContext(), SixActivity.class);
                        six.putExtra("user_id", user_id);
                        startActivity(six);
                    } else {
                        Toast.makeText(getApplicationContext(), "请先完成选择，再点击确定", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"请先登录...",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
