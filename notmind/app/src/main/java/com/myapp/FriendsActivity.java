package com.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.myapp.friend.AddFriendActivity;
import com.myapp.friend.ChangePowerActivity;
import com.myapp.friend.DeleteFriendActivity;
import com.myapp.friend.ShowFriendActivity;
import com.myapp.zone.ShowFriendArtivalActivity;


public class FriendsActivity extends Activity {
    private TextView tv_show_friend;
    private TextView tv_user;
    private TextView tv_delete_friend;
    private TextView tv_change_power;
    private TextView tv_add_friend;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_friends);
        SharedPreferences myPreferences = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        user_id = myPreferences.getString("user_id",null);
        if(user_id != null){
            tv_user = findViewById(R.id.tv_user);
            tv_user.setText("ID:   "+user_id);
            //已经登录过了
            /*查看朋友列表*/
            tv_show_friend =findViewById(R.id.tv_show_friend);
            tv_show_friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent show_friend = new Intent(getApplicationContext(),ShowFriendActivity.class);
                    show_friend.putExtra("user_id",user_id);
                    startActivity(show_friend);

                  /*  AlertDialog.Builder builder = new AlertDialog.Builder(FriendsActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("正在加载数据...");
                    builder.show();
                    finish();*/
                }
            });
            //*添加朋友*/
            tv_add_friend = findViewById(R.id.tv_add_friend);
            tv_add_friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent add_friend = new Intent(getApplicationContext(),AddFriendActivity.class);
                    add_friend.putExtra("user_id",user_id);
                    startActivity(add_friend);
                }
            });
            /*删除朋友*/
            tv_delete_friend = findViewById(R.id.tv_delete_friend);
            tv_delete_friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent delete_friend = new Intent(getApplicationContext(),DeleteFriendActivity.class);
                    delete_friend.putExtra("user_id",user_id);
                    startActivity(delete_friend);
                }
            });
            /*修改朋友权限*/
            tv_change_power = findViewById(R.id.tv_change_power);
            tv_change_power.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent delete_friend = new Intent(getApplicationContext(),ChangePowerActivity.class);
                    delete_friend.putExtra("user_id",user_id);
                    startActivity(delete_friend);
                }
            });
        }else{
            //没有登录
            //查看朋友列表
            tv_show_friend = findViewById(R.id.tv_show_friend);
            tv_show_friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            });
            //添加朋友
            tv_add_friend = findViewById(R.id.tv_add_friend);
            tv_add_friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            });
            //删除朋友
            tv_delete_friend = findViewById(R.id.tv_delete_friend);
            tv_delete_friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            });
            //修改朋友权限
            tv_change_power = findViewById(R.id.tv_change_power);
            tv_change_power.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}