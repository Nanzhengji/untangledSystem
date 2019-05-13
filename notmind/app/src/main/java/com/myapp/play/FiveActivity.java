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

public class FiveActivity extends Activity {
    private EditText five_what,five_1,five_2,five_3,five_4,five_5;
    private Button five_btn;
    private String user_id,s_what,s1,s2,s3,s4,s5;
    private ArrayList<String> strlist = new ArrayList<String>();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_five);

        Intent intent = getIntent();
        user_id=intent.getStringExtra("user_id");

        five_what = findViewById(R.id.five_what);
        five_1 =findViewById(R.id.five_1);
        five_2 = findViewById(R.id.five_2);
        five_3 = findViewById(R.id.five_3);
        five_4 = findViewById(R.id.five_4);
        five_5 = findViewById(R.id.five_5);
        five_btn = findViewById(R.id.five_btn);
        five_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s_what = five_what.getText().toString().trim();
                s1= five_1.getText().toString().trim();
                s2 = five_2.getText().toString().trim();
                s3 = five_3.getText().toString().trim();
                s4 = five_4.getText().toString().trim();
                s5 = five_5.getText().toString().trim();
                if(s_what.length()==0 || s1.length()==0 || s2.length()==0 ||s3.length()==0 || s4.length()==0 || s5.length()==0){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(FiveActivity.this);
                    builder1.setTitle("温馨提示");
                    builder1.setMessage("你还没有完成输入哦  ^~^ ");
                    builder1.setPositiveButton("确定", null);
                    builder1.show();
                }else{

                    strlist.add(s1);
                    strlist.add(s2);
                    strlist.add(s3);
                    strlist.add(s4);
                    strlist.add(s5);
                    Intent intent = new Intent(getBaseContext(),ShowResultActivity.class);
                    intent.putExtra("title",s_what);
                    intent.putStringArrayListExtra("strlist", strlist);

                    //保存到数据库
                    new DaoUtils().save_play(user_id,s_what);

                    startActivity(intent);
                    finish();


                   /*
                      //算法计算结果
                    result = fun(s1,s2,s3,s4,s5);
                    //弹个对话框显示结果
                    AlertDialog.Builder builder = new AlertDialog.Builder(FiveActivity.this);
                    builder.setTitle(s_what+"？");
                    builder.setMessage("今天就选  "+result+"  吧  ^~^   别纠结了");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            five_btn.setText("确定");
                        }
                    });
                    builder.setNegativeButton("再试一次", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            five_btn.setText("再试一次");
                        }
                    });
                    builder.show();*/
                }
            }
        });
    }
   /* public String fun(String s1,String s2,String s3,String s4,String s5){
        //给每个选项一个权值，s1=0.4,s2=0.3,s1=0.15,s4=0.1,s5=0.05;(先输入的为比较想要的)
        float a1=0.4f, a2=0.3f, a3=0.15f,a4=0.1f,a5=0.05f;
        float sum1,sum2,sum3,sum4,sum5,max;
        //给s1,s2,s3一个随机值
        Random random = new Random();
        int n1 = random.nextInt(100);
        int n2 = random.nextInt(100);
        int n3 = random.nextInt(100);
        int n4 = random.nextInt(100);
        int n5 = random.nextInt(100);
        //用该随机值乘以权值
        sum1=a1*n1;
        sum2=a2*n2;
        sum3=a3*n3;
        sum4=a4*n4;
        sum5=a5*n5;
        //假设第一个数最大
        max=sum1;
        max = max>sum2?max:sum2;
        max = max>sum3?max:sum3;
        max = max>sum4?max:sum4;
        max = max>sum5?max:sum5;
        //返回最大的那个对应的选项
        if(max == sum1){
            return s1;
        }else if(max == sum2){
            return s2;
        }else if(max == sum3){
            return s3;
        }else if(max == sum4){
            return s4;
        }else{
            return s5;
        }
    }*/
}
