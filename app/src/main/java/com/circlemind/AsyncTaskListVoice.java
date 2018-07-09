package com.circlemind;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class AsyncTaskListVoice extends AsyncTask {
    public String link;
    public String id;

    public AsyncTaskListVoice(String link,String id){

        this.link=link;
        this.id=id;
    }
    @Override
    protected Object doInBackground(Object[] objects) {

        try{
            String data= URLEncoder.encode("id","UTF8")+"="+URLEncoder.encode(id,"UTF8");


            URL url=new URL(link);
            URLConnection connection=url.openConnection();

            connection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(connection.getOutputStream());
            outputStreamWriter.write(data);
            outputStreamWriter.flush();


            BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder=new StringBuilder();

            String line=null;

            while((line=reader.readLine())!=null){
                builder.append(line);
            }
            ActivitySelectDoreh.dataListVoice=builder.toString();

        }catch (Exception e)
        {

        }
        return "";
    }
}
