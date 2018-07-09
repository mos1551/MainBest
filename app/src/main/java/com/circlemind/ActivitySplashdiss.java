package com.circlemind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class ActivitySplashdiss extends AppCompatActivity {

    ImageView imgrefresh;

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashdiss);

        imgrefresh=(ImageView)findViewById(R.id.imgrefresh);

        imgrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivitySplashdiss.this,ActivitySplash.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
