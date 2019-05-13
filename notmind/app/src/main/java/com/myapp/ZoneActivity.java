package com.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.zone.AddArticalActivity;
import com.myapp.zone.DeleteArticalActivity;
import com.myapp.zone.ShowFriendArtivalActivity;
import com.myapp.zone.ShowMyArticalActivity;
import com.myapp.zone.ShowPlayActivity;

public class ZoneActivity extends Activity {
    private TextView tv_add_artical,tv_show_myartical,tv_z_user,tv_f_artical,tv_play;
    private String user_id;
    private int imgid;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_zone);

        SharedPreferences myPreferences = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        if(myPreferences != null){
            user_id = myPreferences.getString("user_id",null);
            imgid = myPreferences.getInt("imgid",R.drawable.img01);
        }else{
            user_id = null;
        }
        if(user_id != null) {
            tv_z_user = findViewById(R.id.tv_z_user);
            tv_z_user.setText("ID： "+user_id);
            //已经登录过了
            /*发布动态*/
            tv_add_artical = findViewById(R.id.tv_add_artical);
            tv_add_artical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent add_artical = new Intent(getApplicationContext(),AddArticalActivity.class);
                    add_artical.putExtra("user_id",user_id);
                    add_artical.putExtra("imgid",imgid);
                    startActivity(add_artical);
                }
            });

            /*查看我的动态*/
            tv_show_myartical = findViewById(R.id.tv_show_artical);
            tv_show_myartical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent show_artical = new Intent(getApplicationContext(),ShowMyArticalActivity.class);
                    show_artical.putExtra("user_id",user_id);
                    show_artical.putExtra("imgid",imgid);
                    startActivity(show_artical);

                  /*  AlertDialog.Builder builder = new AlertDialog.Builder(ZoneActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("正在加载数据...");
                    builder.show()*/;
                }
            });
            /*查看朋友的动态*/
            tv_f_artical = findViewById(R.id.tv_show_f_artical);
            tv_f_artical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent f_artical = new Intent(getApplicationContext(),ShowFriendArtivalActivity.class);
                    f_artical.putExtra("user_id",user_id);
                    startActivity(f_artical);
                   /* AlertDialog.Builder builder = new AlertDialog.Builder(ZoneActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("正在加载数据...");
                    builder.show();*/


                }
            });
            /*我的纠结经历*/
            tv_play =findViewById(R.id.tv_show_play);
            tv_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent showPlay = new Intent(getApplicationContext(),ShowPlayActivity.class);
                    showPlay.putExtra("user_id",user_id);
                    startActivity(showPlay);
                }
            });
        }else{
            //没有登录
            //发布动态
            tv_add_artical = findViewById(R.id.tv_add_artical);
            tv_add_artical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            });

            //查看我的动态
            tv_show_myartical = findViewById(R.id.tv_show_artical);
            tv_show_myartical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            });
            /*查看朋友的动态*/
            tv_f_artical = findViewById(R.id.tv_show_f_artical);
            tv_f_artical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            });
            /*我的纠结经历*/
            tv_play =findViewById(R.id.tv_show_play);
            tv_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
