package com.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myapp.Adapter.RecommendAdapter;
import com.myapp.Utils.DaoUtils;
import com.myapp.entities.RecommendInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RecommendActivity extends Activity  {
    private ListView listView;
    ArrayList<RecommendInfo> recommendInfoList = new ArrayList<RecommendInfo>();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private int imgid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        //先判断网络状态
        if (!checkNetwork()) {
            Toast.makeText(getApplicationContext(), "网络无连接，请设置网络", Toast.LENGTH_LONG).show();
            Intent intent2 = new Intent("android.settings.WIRELESS_SETTINGS");
            startActivity(intent2);
            return;
        }
        //联网获取推荐内容
        init_recommend();

        if(recommendInfoList.size() != 0){
            //使用适配器给listview添加数据
            listView = findViewById(R.id.recommend_list);
            RecommendAdapter adapter = new RecommendAdapter(getApplicationContext(),R.layout.item_recommend, recommendInfoList);
            listView.setAdapter(adapter);
        }else{
            Toast.makeText(getApplicationContext(),"暂无推荐内容",Toast.LENGTH_SHORT).show();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               RecommendInfo recommendInfo = recommendInfoList.get(i);
               String title=recommendInfo.getRec_title();
               String content=recommendInfo.getRec_content();
                Intent intent=new Intent(getApplicationContext(),RecomendInfoActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("content",content);
                startActivity(intent);
            }
        });
    }
    //获取所有的推荐内容
    public void init_recommend(){
        ArrayList<RecommendInfo> recommends = new ArrayList<RecommendInfo>();
        Type recommendInfoType = new TypeToken<ArrayList<RecommendInfo>>(){}.getType();
        try{
            String result = new DaoUtils().show_recommend();
            //result是一个json字符串，解析成一个集合
            if("500".equals(result)){
            }else if("400".equals(result)){
            }else {
                recommends = new Gson().fromJson(result,recommendInfoType);
                //forech循环，取出每一个user,转化成friend对象的集合
                for (RecommendInfo recommendInfo : recommends) {
                    RecommendInfo recommendInfo1 =new RecommendInfo(recommendInfo.getRec_title(), recommendInfo.getRec_content());
                    recommendInfoList.add(recommendInfo1);
                }
            }
        }catch (Exception e){
            Log.d("syso","RecommendActivity exception:"+e.toString());
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
