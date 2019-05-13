package com.myapp.zone;

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
import com.myapp.Adapter.ArticalAdapter;
import com.myapp.Adapter.FriendArticalAdapter;
import com.myapp.MainActivity;
import com.myapp.R;
import com.myapp.Utils.DaoUtils;
import com.myapp.ZoneActivity;
import com.myapp.entities.Artical;
import com.myapp.entities.FriendArtical;
import com.myapp.entities.User;
import com.myapp.friend.ShowFriendActivity;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShowFriendArtivalActivity extends Activity {
    private String user_id,fuid;
    private ListView lv_artical;
    private ArrayList<FriendArtical> friendarticalList = new ArrayList<FriendArtical>();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_friend_artical);

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

        //从数据库查出数据
        init_friendarticles();

        lv_artical = findViewById(R.id.friend_article_listview);

        if(friendarticalList.size() != 0){
            //给listview添加适配器，添加数据
            FriendArticalAdapter adapter = new FriendArticalAdapter(getApplicationContext(),R.layout.item_friend_artical,friendarticalList);
            lv_artical.setAdapter(adapter);

            lv_artical.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    FriendArtical artical = friendarticalList.get(i);
                    fuid = artical.getUser_id();
                    //先判断网络状态
                    if (!checkNetwork()) {
                        Toast.makeText(getApplicationContext(), "网络无连接，请设置网络", Toast.LENGTH_LONG).show();
                        Intent intent2 = new Intent("android.settings.WIRELESS_SETTINGS");
                        startActivity(intent2);
                        return;
                    }

                    String result = new DaoUtils().find_artical(artical.getArtical_id());
                    String a_title,a_content;
                    Date a_date;

                    try{
                        Artical artical1 = new Gson().fromJson(result, Artical.class);
                        a_title = artical1.getArtical_id();
                        a_content= artical1.getContent();
                        a_date = artical1.getArtical_date();
                        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String  date = formatter.format(a_date);
                        Intent intent = new Intent(getApplicationContext(),FriendArticalDataActivity.class);
                        intent.putExtra("user_id",user_id);
                        intent.putExtra("fuid",fuid);
                        intent.putExtra("artical_id",a_title);
                        intent.putExtra("a_content",a_content);
                        intent.putExtra("a_date",date);
                        startActivity(intent);

                    }catch (Exception e){
                        Log.d("syso",e.toString());
                    }
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"您的朋友暂时还没有发布动态",Toast.LENGTH_SHORT).show();
        }
    }

    public void init_friendarticles(){
        ArrayList<Artical> articals = new ArrayList<Artical>();
        Type articalType = new TypeToken<ArrayList<Artical>>(){}.getType();
        Type userType = new TypeToken<User>(){}.getType();
        try{
            String result = new DaoUtils().show_friend_artical(user_id);
            if("400".equals(result)){
            }else{
                articals = new Gson().fromJson(result, articalType);
                int i = 0;
                for(Artical artical : articals){
                    //通过用户评论关系表，查出user，解析得到user_id,user_name
                    String str = new DaoUtils().find_user(artical.getArtical_id());
                    if("400".equals(str)){
                    }else{
                        User user = new Gson().fromJson(str,userType);
                        FriendArtical artical1 = new FriendArtical(user.getUser_id(),user.getUser_name(),artical.getArtical_id(),artical.getContent(),artical.getArtical_date());
                        if(user.getSex().equals("女")){
                            artical1.setImg(R.drawable.img08);
                        }else{
                            artical1.setImg(R.drawable.img03);
                        }
                        friendarticalList.add(artical1);
                        i++;
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
