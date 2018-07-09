package com.circlemind;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;

import static com.circlemind.Constant.PATHMAIN;

public class ActivityListVoice extends AppCompatActivity {

    public String direc = "";
    LinearLayout linearListVoiceDown;
    CustomeListVoice customeListVoice;
    ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listvoice);

        linearListVoiceDown=(LinearLayout)findViewById(R.id.linearListVoiceDown);
        imgback=(ImageView)findViewById(R.id.imgback);
        customeListVoice=new CustomeListVoice(this);


        direc = Environment.getExternalStorageDirectory() + File.separator;
        File folder = new File(direc, PATHMAIN);
        if (!folder.exists())
        {
            folder.mkdir();
        }

        File fileVoice = new File(direc + PATHMAIN + "/", "");
        if (!fileVoice.exists()) {
//            customeListVoice.
        } else {
//            linearPlay.setVisibility(VISIBLE);
//            linearDownload.setVisibility(GONE);
        }









        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
