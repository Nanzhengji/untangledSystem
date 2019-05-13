package com.myapp.zone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myapp.Adapter.ArticalAdapter;
import com.myapp.MainActivity;
import com.myapp.R;
import com.myapp.Utils.DaoUtils;
import com.myapp.ZoneActivity;
import com.myapp.entities.Artical;


import java.lang.reflect.Type;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ShowMyArticalActivity extends Activity {
    private ListView lv_artical;
    private ArrayList<Artical> articalList = new ArrayList<Artical>();
    private String user_id;
    private int imgid;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_artical);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        imgid = intent.getIntExtra("imgid",R.drawable.img01);

        //先判断网络状态
        if (!checkNetwork()) {
            Toast.makeText(getApplicationContext(), "网络无连接，请设置网络", Toast.LENGTH_LONG).show();
            Intent intent2 = new Intent("android.settings.WIRELESS_SETTINGS");
            startActivity(intent2);
            return;
        }
        //从数据库查出数据
        init_articles();

        TextView add = findViewById(R.id.show_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //到发布动态页面
                Intent intent1 = new Intent(getApplicationContext(),AddArticalActivity.class);
                intent1.putExtra("user_id",user_id);
                intent1.putExtra("imgid",imgid);
                startActivity(intent1);
                finish();
            }
        });



        lv_artical = findViewById(R.id.article_listview);

        if(articalList.size() != 0){
            //给listview添加适配器，添加数据
            ArticalAdapter adapter = new ArticalAdapter(getApplicationContext(),R.layout.item_artical,articalList);
            lv_artical.setAdapter(adapter);

            lv_artical.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Artical artical = articalList.get(i);

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
                        //将日期转化成yyyy-MM-dd格式
                        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String  dString = formatter.format(a_date);
                        //转到显示详细信息页面
                        Intent intent = new Intent(getApplicationContext(),ArticalDataActivity.class);
                        intent.putExtra("user_id",user_id);
                        intent.putExtra("artical_id",a_title);
                        intent.putExtra("a_content",a_content);
                        intent.putExtra("a_date",dString);
                        intent.putExtra("imgid",imgid);
                        startActivity(intent);

                    }catch (Exception e){
                        Log.d("syso",e.toString());
                    }
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"您暂时还没有动态，去添加吧",Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(getApplicationContext(),AddArticalActivity.class);
            intent1.putExtra("user_id",user_id);
            intent1.putExtra("imgid",imgid);
            startActivity(intent1);
            finish();
        }
    }

//从数据库中查出所有的动态（是个集合），解析成一个一个的动态
    public void init_articles(){
        ArrayList<Artical> articals = new ArrayList<Artical>();
        Type articalType = new TypeToken<ArrayList<Artical>>(){}.getType();
        try{
            String result = new DaoUtils().show_artical(user_id);
            if("400".equals(result)){
            }else{
                articals = new Gson().fromJson(result, articalType);
                for(Artical artical : articals){
                    Artical artical1 = new Artical(artical.getArtical_id(),artical.getContent(),artical.getArtical_date());
                    artical1.setImg(imgid);
                    articalList.add(artical1);
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
