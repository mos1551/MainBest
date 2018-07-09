package com.circlemind;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.circlemind.Constant.IP;

public class ActivityFirstPage extends AppCompatActivity {


    LinearLayout linearDorehHa;
    LinearLayout linearErtebat;
    SharedPreferences preferences;
    ImageView imgExit;
    ImageView imgSetting;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_firstpage);

        imgExit=(ImageView) findViewById(R.id.imgExit);
        linearDorehHa = (LinearLayout) findViewById(R.id.linearDorehHa);
        linearErtebat=(LinearLayout)findViewById(R.id.linearErtebat);
        imgSetting= (ImageView) findViewById(R.id.imgSetting);
//        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);


        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityFirstPage.this,ActivitySetting.class);
                startActivity(intent);
            }
        });

        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = PreferenceManager.getDefaultSharedPreferences(ActivityFirstPage.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("phoneNumber", "");
                editor.putString("passNumber","");
                editor.commit();
                finish();
            }
        });

        linearErtebat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ActivityFirstPage.this,ActivityAbout.class);
                startActivity(intent);
            }
        });


        linearDorehHa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                            Intent intent = new Intent(ActivityFirstPage.this, ActivityListDoreh.class);
                            startActivity(intent);
            }
        });



    }
}
