package com.myapp.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.myapp.R;

public class ChangePhotoActivity extends Activity {
    ImageView[] iv = new ImageView[12];

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos);
        int[] ids = { R.id.image_01, R.id.image_02, R.id.image_03, R.id.image_04,
                      R.id.image_05, R.id.image_06, R.id.image_07, R.id.image_08,
                      R.id.image_09,R.id.image_10, R.id.image_11, R.id.image_12 };
        final int[] imgId = { R.drawable.img01, R.drawable.img02, R.drawable.img03,
                             R.drawable.img04, R.drawable.img05, R.drawable.img06,
                             R.drawable.img07, R.drawable.img08, R.drawable.img09,
                            R.drawable.img10, R.drawable.img11, R.drawable.img12};
        for(int i=0;i<iv.length;i++){
            final int finalI = i;
            iv[i] = findViewById(ids[i]);
            iv[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = getIntent();
                    intent.putExtra("image",imgId[finalI]);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }
    }
}
