package com.myapp;


import android.app.ActionBar;
import android.app.Dialog;
import android.app.TabActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.personal.ChangePwdActivity;
import com.myapp.personal.HelpActivity;
import com.myapp.personal.LoginActivity;
import com.myapp.personal.RegistActivity;
import com.myapp.personal.ShowDataActivity;

public class MainActivity extends TabActivity implements View.OnClickListener, View.OnTouchListener {
    private TextView regist,login,logout,p_help,change_pwd,show_data;
    //滚动显示和隐藏menu时，手指滑动需要达到的速度。
    public static final int SNAP_VELOCITY = 200;
    private int screenWidth,leftEdge;
    //menu最多可以滑动到的右边缘。值恒为0，即marginLeft到达0之后，不能增加。menu完全显示时，留给content的宽度值
    private int rightEdge = 0,menuPadding = 220;
    // 布局视图。
    private View mainlayout,content, menu;
    //menu布局的参数，通过此参数来更改leftMargin的值。
    private LinearLayout.LayoutParams menuParams;
    // 记录手指按下时的横纵坐标。
    private float xDown,xMove,xUp;
    // menu当前是显示还是隐藏。只有完全显示或隐藏menu时才会更改此值，滑动过程中此值无效。
    private boolean isMenuVisible;
    // 用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;
    //主页面下方的选项卡TabHost。
    private TabHost tabHost = null;
    //使用轻量级的存储类SharedPreferences保存用户的账号信息
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String user_id = null;
    private TextView tv_uid;
    private int imgid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置网络权限，防止ANR（应用程序无响应）
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //初始化布局样式，可以左滑的样式
        initValues();

        //获取用户的登录信息
        pref= getSharedPreferences("userInfo",MODE_PRIVATE);
        editor=pref.edit();
        if(editor != null){
            user_id=pref.getString("user_id","");
            imgid = pref.getInt("imgid",R.drawable.img01);
            ImageView iv_img = findViewById(R.id.iv_picture);
            iv_img.setImageResource(imgid);
        }

        //设置屏幕的触摸事件，onTouch方法见250行
        mainlayout.setOnTouchListener(this);

        //TabWidget是TabHost标签页下部的按钮, 可以点击按钮切换选项卡;
        tabHost = getTabHost();
        tabHost.getTabWidget().setDividerDrawable(null);
        //TabSpec 选项卡界面
        TabHost.TabSpec page1 = tabHost.newTabSpec("tab1")
                //给tab设置文字和图标
                .setIndicator(composeLayout("不纠结", R.drawable.zone_press))
                .setContent(new Intent(this, PlayActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        tabHost.addTab(page1);

        TabHost.TabSpec page2 = tabHost.newTabSpec("tab2")
                .setIndicator(composeLayout("推荐", R.drawable.play_press))
                .setContent(new Intent(this, RecommendActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        tabHost.addTab(page2);

        TabHost.TabSpec page3 = tabHost.newTabSpec("tab3")
                .setIndicator(composeLayout("朋友", R.drawable.friends_press))
                .setContent(new Intent(this, FriendsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        tabHost.addTab(page3);

        TabHost.TabSpec page4 = tabHost.newTabSpec("tab4")
                .setIndicator(composeLayout("空间", R.drawable.personal_press))
                .setContent(new Intent(this, ZoneActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        tabHost.addTab(page4);
        //默认打开第一个
        tabHost.setCurrentTabByTag("tab1");
        View view = tabHost.getTabWidget().getChildAt(0);
        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.f_bg));
        //tabHost菜单项改变的时候更换背景
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                tabHost.setCurrentTabByTag(tabId);
                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    View view = tabHost.getTabWidget().getChildAt(i);
                      if (tabHost.getCurrentTab() == i) {//选中
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.f_bg));//选中后的背景
                    } else {//不选中
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tag));//非选择的背景
                    }
                }
            }
        });

        //获得左边菜单的控件，设置点击事件
        regist = findViewById(R.id.tv_regist);
        login = findViewById(R.id.tv_login);
        show_data = findViewById(R.id.p_show_data);
        change_pwd = findViewById(R.id.p_change_pwd);
        logout = findViewById(R.id.tv_logout);
        p_help = findViewById(R.id.p_help);

        if(user_id != null){
            //登录了
            tv_uid = findViewById(R.id.tv_uid);
            tv_uid.setText("ID:  "+user_id);
            regist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"已经登录，无需注册",Toast.LENGTH_SHORT).show();
                }
            });
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"已经登录，无需重复登录",Toast.LENGTH_SHORT).show();
                }
            });
            show_data.setOnClickListener(this);
            change_pwd.setOnClickListener(this);
        }else{
            //没登录
            regist.setOnClickListener(this);
            login.setOnClickListener(this);
            show_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            });
            change_pwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            });
        }
        logout.setOnClickListener(this);
        p_help.setOnClickListener(this);
    }

    //控件的点击事件
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.tv_regist:
                Intent regist = new Intent(getApplicationContext(),RegistActivity.class);
                startActivity(regist);
                break;
            case R.id.tv_login:
                Intent login = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(login);
                break;
            case R.id.p_show_data:
                Intent show = new Intent(getApplicationContext(),ShowDataActivity.class);
                show.putExtra("user_id",user_id);
                show.putExtra("imgid",imgid);
                startActivity(show);
                break;
            case R.id.p_change_pwd:
                Intent change = new Intent(getApplicationContext(),ChangePwdActivity.class);
                change.putExtra("user_id",user_id);
                startActivity(change);
                break;
            case R.id.tv_logout:
                //注销   清空SharedPreferences中保存的用户的账号信息
                editor.clear();
                editor.commit();
                //清空自动登录的内容
                SharedPreferences pref1= getSharedPreferences("autologin",MODE_PRIVATE);
                SharedPreferences.Editor editor1=pref1.edit();
                editor1.clear();
                editor1.commit();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"已注销，请重新登录",Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.p_help:
                Intent help = new Intent(getApplicationContext(),HelpActivity.class);
                startActivity(help);
                break;
        }
    }
    //给Tab中的TextView设置文字和图标
    public View composeLayout(String s, int i) {
        TextView tv = new TextView(this);
        tv.setText(s);
        tv.setTextSize(12f);
        tv.setGravity(Gravity.CENTER);
        Drawable nav_up = getResources().getDrawable(i);
        //设置图片的边距和与文字的位置
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        tv.setCompoundDrawables(null, nav_up, null, null);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(12);
        return tv;
    }
     // 初始化一些关键性数据。包括获取屏幕的宽度，给content布局重新设置宽度，给menu布局重新设置宽度和偏移距离等。
    private void initValues() {
        //获取窗口管理器
        WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕宽度
        screenWidth = window.getDefaultDisplay().getWidth();
        //获取页面布局控件
        mainlayout = findViewById(R.id.mainlayout);
        content = findViewById(R.id.content);
        menu = findViewById(R.id.menu);
        //填充子容器menu
        menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();
        // 将menu的宽度设置为屏幕宽度减去menuPadding（左侧菜单占大部分，右侧菜单占menuPadding宽220）
        menuParams.width = screenWidth - menuPadding;
        // 左边缘的值赋值为menu宽度的负数
        leftEdge = -menuParams.width;
        // menu的leftMargin设置为左边缘的值，这样初始化时menu就变为不可见
        menuParams.leftMargin = leftEdge;
        // 将content的宽度设置为屏幕宽度
        content.getLayoutParams().width = screenWidth;
    }
    //屏幕的触摸事件
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时，记录按下时的横坐标
                xDown = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                // 手指移动时，对比按下时的横坐标，计算出移动的距离，来调整menu的leftMargin值，从而显示和隐藏menu
                xMove = event.getRawX();
                int distanceX = (int) (xMove - xDown);
                if (isMenuVisible) {
                    menuParams.leftMargin = distanceX;
                } else {
                    menuParams.leftMargin = leftEdge + distanceX;
                }
                if (menuParams.leftMargin < leftEdge) {
                    menuParams.leftMargin = leftEdge;
                } else if (menuParams.leftMargin > rightEdge) {
                    menuParams.leftMargin = rightEdge;
                }
                menu.setLayoutParams(menuParams);
                break;
            case MotionEvent.ACTION_UP:
                // 手指抬起时，进行判断当前手势的意图，从而决定是滚动到menu界面，还是滚动到content界面
                xUp = event.getRawX();
                if (wantToShowMenu()) {
                    if (shouldScrollToMenu()) {
                        scrollToMenu();
                    } else {
                        scrollToContent();
                    }
                } else if (wantToShowContent()) {
                    if (shouldScrollToContent()) {
                        scrollToContent();
                    } else {
                        scrollToMenu();
                    }
                }
                recycleVelocityTracker();
                break;
        }
        return true;
    }
    //如果手指移动的距离是负数(左滑)，且当前menu是可见的，则认为当前手势是想要显示content。当前手势想显示content返回true，否则返回false。
    private boolean wantToShowContent() {
        return xUp - xDown < 0 && isMenuVisible;
    }
    private boolean wantToShowMenu() {
        return xUp - xDown > 0 && !isMenuVisible;
    }
    // 判断是否应该滚动将menu展示出来。如果手指移动距离大于屏幕的1/2，或者手指移动速度大于SNAP_VELOCITY，就认为应该滚动将menu展示出来。
    // 如果应该滚动将menu展示出来返回true，否则返回false。
    private boolean shouldScrollToMenu() {
        return xUp - xDown > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }
    private boolean shouldScrollToContent() {
        return xDown - xUp + menuPadding > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }
    // 将屏幕滚动到menu界面，滚动速度设定为30.
    private void scrollToMenu() {
        new ScrollTask().execute(50);
    }
    private void scrollToContent() {
        new ScrollTask().execute(-50);
    }
    // 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。content界面的滑动事件
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }
     // 获取手指在content界面滑动的速度。
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }
    //回收VelocityTracker对象。
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    class ScrollTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... speed) {
            int leftMargin = menuParams.leftMargin;
            // 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。
            while (true) {
                leftMargin = leftMargin + speed[0];
                if (leftMargin > rightEdge) {
                    leftMargin = rightEdge;
                    break;
                }
                if (leftMargin < leftEdge) {
                    leftMargin = leftEdge;
                    break;
                }
                publishProgress(leftMargin);
                // 为了要有滚动效果产生，每次循环使线程睡眠20毫秒，这样肉眼才能够看到滚动动画。
                sleep(5);
            }
            if (speed[0] > 0) {
                isMenuVisible = true;
            } else {
                isMenuVisible = false;
            }
            return leftMargin;
        }
        @Override
        protected void onProgressUpdate(Integer... leftMargin) {
            menuParams.leftMargin = leftMargin[0];
            menu.setLayoutParams(menuParams);
        }
        @Override
        protected void onPostExecute(Integer leftMargin) {
            menuParams.leftMargin = leftMargin;
            menu.setLayoutParams(menuParams);
        }
    }
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
