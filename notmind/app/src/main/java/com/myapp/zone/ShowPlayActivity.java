package com.myapp.zone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.myapp.R;
import com.myapp.Utils.DaoUtils;
import com.myapp.entities.Play;

import java.lang.reflect.Type;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ShowPlayActivity extends Activity {
    private String user_id;
    private ArrayList<HashMap<String,String>> listItem=new ArrayList<HashMap<String, String>>();;
    private ListView lv_plays;
    private Button btn_pre,btn_next;
    private View.OnClickListener clickListener;
    private MyAdapter myAdapter;
    //索引从0开始，一页显示10条记录
    int VIEW_COUNT=10;
    int index =0;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_play);
        //设置网络权限，防止ANR（应用程序无响应）
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");

        lv_plays = findViewById(R.id.play_listview);
        btn_pre=findViewById(R.id.btn_pre);
        btn_next=findViewById(R.id.btn_next);

        //先判断网络状态
        if (!checkNetwork()) {
            Toast.makeText(getApplicationContext(), "网络无连接，请设置网络", Toast.LENGTH_LONG).show();
            Intent intent2 = new Intent("android.settings.WIRELESS_SETTINGS");
            startActivity(intent2);
            return;
        }
        //从数据库查出所有的记录
        init_play();

        myAdapter=new MyAdapter(this);
        lv_plays.setAdapter(myAdapter);

        clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.btn_pre:
                        preView();
                        break;
                    case R.id.btn_next:
                        nextView();
                        break;
                }
            }
        };
        btn_pre.setOnClickListener(clickListener);
        btn_next.setOnClickListener(clickListener);
        checkButton();
    }
    public void preView(){
        index--;
        //刷新listview里的数值（每一次notifyDataSetChange()都会引起界面的重绘 ）
        myAdapter.notifyDataSetChanged();
        //检查按钮的状态
        checkButton();
    }
    public void nextView(){
        index++;
        myAdapter.notifyDataSetChanged();
        checkButton();
    }
    public void checkButton(){
        //索引值小于等于0，表示不能向前翻页了，以经到了第一页了
        if(index <= 0){
            btn_pre.setText("第一页");
            btn_pre.setEnabled(false);
        }else{
            btn_pre.setText("上一页");
            btn_pre.setEnabled(true);
        }
        //值的长度减去前几页的长度，剩下的就是这一页的长度，如果这一页的长度比View_Count小，表示这是最后的一页
        if(listItem.size() - index*VIEW_COUNT <= VIEW_COUNT){
            btn_next.setText("最末页");
            btn_next.setEnabled(false);
        }else{
            btn_next.setText("下一页");
            btn_next.setEnabled(true);
        }
    }
    public class MyAdapter extends BaseAdapter{
        Activity activity;
        public MyAdapter(Activity activity){
            this.activity=activity;
        }
        //设置每一页的长度
        @Override
        public int getCount() {
            // ori表示到目前为止的前几页的总共的个数
            int ori=VIEW_COUNT*index;
            //值的总个数-前几页的个数就是这一页要显示的个数，如果比默认的值小，说明这是最后一页，只需显示这么多就可以了
            if(listItem.size()-ori<VIEW_COUNT){
                return listItem.size()-ori;
            }else{
                //如果比默认的值还要大，说明一页显示不完，还要用换一页显示，这一页用默认的值显示满就可以了。
                return VIEW_COUNT;
            }
        }
        @Override
        public Object getItem(int i) {
            return i;
        }
        @Override
        public long getItemId(int i) {
            return i;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_play, null);
            TextView play_detail = view.findViewById(R.id.play_detail);
            play_detail.setText(listItem.get(i+index*VIEW_COUNT).get("play_detail"));
            return view;
        }
    }
    public void init_play(){
        ArrayList<Play> plays = new ArrayList<Play>();
        Type playsType = new TypeToken<ArrayList<Play>>(){}.getType();
        try{
            String result = new DaoUtils().show_play(user_id);
            if("400".equals(result)){
            }else{
                plays = new Gson().fromJson(result, playsType);
                for(Play play : plays){
                    HashMap<String,String> map =new HashMap<String,String>();
                    map.put("play_detail",new SimpleDateFormat("yyyy-MM-dd").format(play.getDate())+"\t\t\t\t纠结"+play.getPlay_title());
                   listItem.add(map);
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
