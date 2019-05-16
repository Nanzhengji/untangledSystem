package com.myapp.friend;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myapp.Adapter.FriendAdapter;
import com.myapp.FriendsActivity;
import com.myapp.R;
import com.myapp.Utils.DaoUtils;
import com.myapp.ZoneActivity;
import com.myapp.entities.Friend;

import com.myapp.entities.User;

import java.lang.reflect.Type;

import java.util.ArrayList;


public class ShowFriendActivity extends Activity {
    private ArrayList<Friend> friendList = new ArrayList<Friend>();
    ListView listView;
    String user_id;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_friend);
        //解决联网问题
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        //先判断网络状态
        if (!checkNetwork()) {
            Toast.makeText(getApplicationContext(), "网络无连接，请设置网络", Toast.LENGTH_LONG).show();
            Intent intent2 = new Intent("android.settings.WIRELESS_SETTINGS");
            startActivity(intent2);
            return;
        }

       /* AlertDialog.Builder builder = new AlertDialog.Builder(ShowFriendActivity.this);
        builder.setTitle("提示");
        builder.setMessage("正在加载数据...");
        builder.show();*/

        //得到所有的朋友
        getAllFriend();

        if(friendList.size() != 0){
        //使用适配器给listview添加数据
        listView = findViewById(R.id.friend_listview);
        FriendAdapter adapter = new FriendAdapter(getApplicationContext(),R.layout.item_friend,friendList);
        listView.setAdapter(adapter);

        //设置listview的点击事件（点击查看详细信息）
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Friend friend = friendList.get(i);
                //先判断网络状态
                if (!checkNetwork()) {
                    Toast.makeText(getApplicationContext(), "网络无连接，请设置网络", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent("android.settings.WIRELESS_SETTINGS");
                    startActivity(intent2);
                    return;
                }

                String result = new DaoUtils().find_friend(friend.getFid());
                String name,sex,address,motto;
                int age;
                try{
                    User user = new Gson().fromJson(result, User.class);
                    name = user.getUser_name().equals("null")?"佚名":user.getUser_name();
                    sex = user.getSex().equals("null")?"男":user.getSex();
                    address = user.getAddress().equals("null")?"中国":user.getAddress();
                    motto = user.getMotto().equals("null")?"每个人都是独一无二的存在":user.getMotto();
                    age = user.getAge();

                    Intent intent = new Intent(getApplicationContext(),FriendDataActivity.class);
                    intent.putExtra("user_id",user_id);
                    intent.putExtra("my_id",user_id);
                    intent.putExtra("fid",friend.getFid());
                    intent.putExtra("fname",name);
                    intent.putExtra("fsex",sex);
                    intent.putExtra("faddress",address);
                    intent.putExtra("fmotto",motto);
                    intent.putExtra("fage",age);
                    startActivity(intent);
                }catch (Exception e){
                    Log.d("syso",e.toString());
                }
            }
        });
        }else{
            //没有朋友
            Toast.makeText(getApplicationContext(),"您暂时还没有朋友，去添加吧",Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(getApplicationContext(),AddFriendActivity.class);
            intent1.putExtra("user_id",user_id);
            startActivity(intent1);
            finish();
        }
    }

    //从数据库查出所有的朋友
    public void getAllFriend(){
        ArrayList<User> users = new ArrayList<User>();
        Type usersType = new TypeToken<ArrayList<User>>(){}.getType();
        try{
            String result = new DaoUtils().show_friend(user_id);
            //result是一个userS 的json字符串，解析成一个userS的集合
            if("400".equals(result)){
                //没有朋友friendList为空
            }else {
                users = new Gson().fromJson(result,usersType);
                //forech循环，取出每一个user,转化成friend对象的集合
                for (User user : users) {
                    if(user.getSex().equals("男")){
                        Friend friend = new Friend(user.getUser_id(),user.getUser_name().equals("null")?"佚名":user.getUser_name(), R.drawable.img03);
                        friendList.add(friend);
                    }else{
                        Friend friend = new Friend(user.getUser_id(),user.getUser_name(), R.drawable.img08);
                        friendList.add(friend);
                    }
                }
            }
        }catch (Exception e){
            Log.d("syso",e.toString());
        }
    }
    //检查网络状态
    private boolean checkNetwork() {
        ConnectivityManager conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = conn.getActiveNetworkInfo();
        if (net != null && net.isConnected()) {
            return true;
        }
        return false;
    }


}
