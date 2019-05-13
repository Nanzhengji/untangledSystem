package com.myapp.play;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.R;
import com.myapp.Utils.PlayView;
import java.util.ArrayList;
import java.util.List;


public class ShowResultActivity extends Activity {
    private PlayView playView;
    private String title;
    private ArrayList<String> strlist = new ArrayList<String>();
    private ImageView btn;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_view);

        playView = findViewById(R.id.id_playview);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        strlist = intent.getStringArrayListExtra("strlist");

        final TextView tv_title = findViewById(R.id.play_title);
        tv_title.setText(title+" ？");
        playView.setStrs(strlist,strlist.size());
         btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!playView.isStart()){
                    playView.playStart();

                    //换张图片(图片上有指针，有文字)
                    btn.setImageResource(R.drawable.stop);
                }else{
                    if(! playView.isShouldEnd()){
                        playView.playEnd();
                        //换张图片
                        btn.setImageResource(R.drawable.node);
                    }
                }
            }
        });



    }
}
