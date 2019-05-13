package com.myapp.play;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myapp.R;
import com.myapp.Utils.DaoUtils;

import java.util.ArrayList;

public class ThreeActivity extends Activity {
    private EditText three_what,three_1,three_2,three_3;
    private Button three_btn;
    private String user_id,s_what,s1,s2,s3;
    private ArrayList<String> strlist = new ArrayList<String>();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_three);

        Intent intent = getIntent();
        user_id=intent.getStringExtra("user_id");

        three_what = findViewById(R.id.three_what);
        three_1 =findViewById(R.id.three_1);
        three_2 = findViewById(R.id.three_2);
        three_3 = findViewById(R.id.three_3);
        three_btn = findViewById(R.id.three_btn);
        three_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s_what = three_what.getText().toString().trim();
                s1= three_1.getText().toString().trim();
                s2 = three_2.getText().toString().trim();
                s3 = three_3.getText().toString().trim();
                if(s_what.length()==0 || s1.length()==0 || s2.length()==0 ||s3.length()==0){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ThreeActivity.this);
                    builder1.setTitle("温馨提示");
                    builder1.setMessage("你还没有完成输入哦  ^~^ ");
                    builder1.setPositiveButton("确定", null);
                    builder1.show();
                }else{

                    strlist.add(s1);
                    strlist.add(s2);
                    strlist.add(s3);
                    Intent intent = new Intent(getBaseContext(),ShowResultActivity.class);
                    intent.putExtra("title",s_what);
                    intent.putStringArrayListExtra("strlist", strlist);

                    //保存到数据库
                    new DaoUtils().save_play(user_id,s_what);

                    startActivity(intent);
                    finish();
                }
            }
        });
    }
   /* public String fun(String s1,String s2,String s3){
        //给每个选项一个权值，s1=0.5,s2=0.3,s1=0.2;(先输入的为比较想要的,权值较高)
        float a1=0.5f, a2=0.3f, a3=0.2f;
        float sum1,sum2,sum3,max;
        //给s1,s2,s3一个随机值
        Random random = new Random();
        int n1 = random.nextInt(100);
        int n2 = random.nextInt(100);
        int n3 = random.nextInt(100);
        //用该随机值乘以权值
        sum1=a1*n1;
        sum2=a2*n2;
        sum3=a3*n3;
        //求出乘积最大的那个结果
        max=sum1;
        max = max>sum2?max:sum2;
        max = max>sum3?max:sum3;
        //返回乘积最大的结果对应的那个选项
        if(max == sum1){
            return s1;
        }else if(max == sum2){
            return s2;
        }else{
            return s3;
        }
    }*/
}
