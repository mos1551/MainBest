package com.circlemind;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class AsyncTaskAddUser extends AsyncTask{
    public String link;
    public String name;
    public String code;
    public String mobile;
    public String tel;
    public String pass;
    public int gender;


    public AsyncTaskAddUser(String link, String name,String code,
                            String mobile,String tel,String pass, int gender){

        this.link=link;
        this.name=name;
        this.code=code;
        this.mobile=mobile;
        this.tel=tel;
        this.pass=pass;
        this.gender=gender;


    }
    @Override
    protected Object doInBackground(Object[] objects) {

        try{
            String data= URLEncoder.encode("name","UTF8")+"="+URLEncoder.encode(name,"UTF8");
            data+="&"+URLEncoder.encode("code","UTF8")+"="+URLEncoder.encode(code+"","UTF8");
            data+="&"+URLEncoder.encode("mobile","UTF8")+"="+URLEncoder.encode(mobile+"","UTF8");
            data+="&"+URLEncoder.encode("tel","UTF8")+"="+URLEncoder.encode(tel+"","UTF8");
            data+="&"+URLEncoder.encode("pass","UTF8")+"="+URLEncoder.encode(pass+"","UTF8");
            data+="&"+URLEncoder.encode("gender","UTF8")+"="+URLEncoder.encode(gender+"","UTF8");


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
            ActivitySignUp.dataAdd=builder.toString();

        }catch (Exception e)
        {

        }
        return "";
    }
}