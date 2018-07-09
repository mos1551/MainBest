package com.circlemind;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class AsyncTaskListDoreh extends AsyncTask{

    public String link;
    //public String phoneNumber;

    public AsyncTaskListDoreh(String link){

        this.link=link;
        //this.phoneNumber=phoneNumber;
    }
    @Override
    protected Object doInBackground(Object[] objects) {

        try{
//            String data= URLEncoder.encode("codeM","UTF8")+"="+URLEncoder.encode(phoneNumber,"UTF8");


            URL url=new URL(link);
            URLConnection connection=url.openConnection();

            connection.setDoOutput(true);
//            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(connection.getOutputStream());
//            outputStreamWriter.write(data);
//            outputStreamWriter.flush();


            BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder=new StringBuilder();

            String line=null;

            while((line=reader.readLine())!=null){
                builder.append(line);
            }
            ActivityListDoreh.dataListDoreh=builder.toString();

        }catch (Exception e)
        {

        }
        return "";
    }
}