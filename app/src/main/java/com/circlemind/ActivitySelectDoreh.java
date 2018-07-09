package com.circlemind;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivitySelectDoreh extends AppCompatActivity {

    public static String dataListVoice="";
    CustomeVoice customeVoice;
    LinearLayout.LayoutParams layoutParams;
    LinearLayout linearVoiseList;
    TextView txtDorehFaal;
    ProgressBar progressSearchVoice;
//    ProgressDialog mProgressDialog;


    public void showAllVoice() {
        try {
            JSONArray jsonarrayNomarat = new JSONArray(dataListVoice);
            for (int i = 0; i < jsonarrayNomarat.length(); i++) {
                JSONObject object = jsonarrayNomarat.getJSONObject(i);
                String name = object.getString("number");
                String time = object.getString("time");
                String path=object.getString("path");
                String hajm=object.getString("hajm");

                createAllVoice(name, time,path,hajm);
                //  Toast.makeText(G.context,roz,Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void createAllVoice(String name, String time, String path,String hajm) {

        customeVoice = new CustomeVoice(ActivitySelectDoreh.this);
        customeVoice.txtNameDownload.setText(name);
        customeVoice.txtTimeDownload.setText(time);
        customeVoice.txtاHajmDownload.setText(hajm);
        customeVoice.path=path;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearVoiseList.addView(customeVoice);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectdoreh);

        linearVoiseList=(LinearLayout)findViewById(R.id.linearVoiseList);
        txtDorehFaal=(TextView)findViewById(R.id.txtDorehFaal);
        progressSearchVoice=(ProgressBar)findViewById(R.id.progressSearchVoice);



        if (!dataListVoice.equals("")) {
            progressSearchVoice.setVisibility(View.GONE);
            showAllVoice();
        } else {
            progressSearchVoice.setVisibility(View.GONE);
        }


//        mProgressDialog = new ProgressDialog(ActivitySelectDoreh.this);
//        mProgressDialog.setMessage("در حال دانلود");
////        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        mProgressDialog.setCancelable(true);

//        nothingDriver.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final DownloadTask downloadTask = new DownloadTask(ActivitySelectDoreh.this,progressSearchVoice);
//                downloadTask.execute(IP+"file/p.jpg");

//                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialog) {
//                        downloadTask.cancel(true);
//                    }
//                });
//            }
//        });


    }

    public void newVoice(String name,String time,String path){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Voice voice =new Voice(name, time,path);
        dbHandler.addVoice(voice);
    }

    public void lookupVoice (View view,String name) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        Voice voice =dbHandler.findVoice(name);

        if (view != null) {
            Toast.makeText(getApplicationContext(),voice.getVoiceName(),Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),"موردی پیدا نشد!!!",Toast.LENGTH_SHORT).show();
        }
    }

    public void removeVoice (View view,String name) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        boolean result = dbHandler.deleteVoice(name);

        if (result)
        {
            Toast.makeText(getApplicationContext(),"حذف شد",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(),"موردی پیدا نشد!!!",Toast.LENGTH_SHORT).show();
    }



}
