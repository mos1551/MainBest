package com.circlemind;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static com.circlemind.Constant.IP;


public class ActivityListDoreh extends AppCompatActivity {

    public static String dataListDoreh="";
    LinearLayout linearListDoreh;
    LinearLayout linearWaitDoreh;
    String phoneNumber;
    CustomeDoreh customeDoreh;
    LinearLayout.LayoutParams layoutParams;
    TextView txtDorehFaal;
    ImageView imgback;
    Integer id=0;
    SharedPreferences preferences;



    public void showAllDoreh() {
        try {
            JSONArray jsonarrayNomarat = new JSONArray(dataListDoreh);
            for (int i = 0; i < jsonarrayNomarat.length(); i++) {
                JSONObject object = jsonarrayNomarat.getJSONObject(i);
                String name = object.getString("name");
                String time = object.getString("time");
                String description = object.getString("description");
                id = object.getInt("id");
                String picUrl=object.getString("pic");

                createAllDoreh(name, time,description, id,picUrl);
                linearWaitDoreh.setVisibility(View.GONE);
                txtDorehFaal.setVisibility(View.GONE);
                //  Toast.makeText(G.context,roz,Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void createAllDoreh(String name, String time, String description, Integer id,String picUrl) {

        customeDoreh = new CustomeDoreh(ActivityListDoreh.this);
        customeDoreh.txtnameDoreh.setText(name);
        customeDoreh.txtDescriptionDoreh.setText(description);
        customeDoreh.txtTimeDoreh.setText(time);
        customeDoreh.txtNumberId.setText(id+"");
        customeDoreh.id=id;
        picUrl = IP + "/file/" + picUrl;
        Picasso.with(getApplicationContext()).load(picUrl).into(customeDoreh.imgDoreh);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearListDoreh.addView(customeDoreh);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdoreh);


        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(ActivityListDoreh.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(ActivityListDoreh.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ActivityListDoreh.this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            } else {
                //do something
            }
        } else {
            //do something
        }

        linearWaitDoreh=(LinearLayout)findViewById(R.id.linearWaitDoreh);
        linearListDoreh=(LinearLayout)findViewById(R.id.linearListDoreh);
        txtDorehFaal=(TextView)findViewById(R.id.txtDorehFaal);
        imgback=(ImageView)findViewById(R.id.imgback);



        new AsyncTaskListDoreh(IP + "/listDoreh.php").execute();

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!dataListDoreh.equals("")) {
                            if(dataListDoreh.equals("not"))
                            {
                                linearWaitDoreh.setVisibility(View.GONE);
                                txtDorehFaal.setVisibility(View.VISIBLE);
                            }else {
                                showAllDoreh();

                            }
                            timer.cancel();
                        }
                    }
                });

            }
        }, 2, 2000);


        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if(!dataListDoreh.equals(""))
        {
            if(dataListDoreh.equals("not"))
            {
                linearWaitDoreh.setVisibility(View.GONE);
                txtDorehFaal.setVisibility(View.VISIBLE);
            }else {
//                showAllDoreh();
            }
        }
        else{
//            linearWaitDoreh.setVisibility(View.GONE);
//            txtDorehFaal.setVisibility(View.VISIBLE);
        }


    }
}
