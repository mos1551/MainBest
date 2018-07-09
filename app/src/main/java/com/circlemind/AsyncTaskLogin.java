package com.circlemind;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class AsyncTaskLogin extends AsyncTask{
    public String link;
    public String phoneNumber;
    public String passNumber;
    public AsyncTaskLogin(String link, String phoneNumber, String passNumber){

        this.link=link;
        this.phoneNumber=phoneNumber;
        this.passNumber=passNumber;
    }
    @Override
    protected Object doInBackground(Object[] objects) {

        try{
            String data= URLEncoder.encode("mobile","UTF8")+"="+URLEncoder.encode(phoneNumber,"UTF8");
            data+="&"+URLEncoder.encode("pass","UTF8")+"="+URLEncoder.encode(passNumber,"UTF8");


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
            ActivitySignin.dataAuth=builder.toString();


        }catch (Exception e)
        {

        }
        return "";
    }
}