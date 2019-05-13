package com.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RecomendInfoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_info);

        //设置推荐内容 允许复制（因为内容中可能包括管理员推荐的链接、淘口令等）
        // android:textIsSelectable="true"
        // android:textColorHighlight="@color/colorBg"
        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String content=intent.getStringExtra("content");

        TextView tv_title=findViewById(R.id.tv_title);
        TextView tv_content=findViewById(R.id.tv_content);
        tv_title.setText(title);
        tv_content.setText("\t\t\t\t"+content);
    }
}
