package com.circlemind;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.PowerManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.circlemind.Constant.PATHMAIN;


public class DownloadTask extends AsyncTask<String,Integer,String> {
    private Context context;
//    public ProgressDialog mProgressDialog;
    public ProgressBar mProgressDialog;
    private PowerManager.WakeLock mWakeLock;
    public String name;

    public DownloadTask(Context context, ProgressBar mProgressDialog,String name) {
        this.context = context;
        this.mProgressDialog=mProgressDialog;
        this.name=name;
    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
//                return "فایل مورد نظر وجود ندارد " + connection.getResponseCode()
//                        + " " + connection.getResponseMessage();
                return "فایل مورد نظر وجود ندارد ";
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
//            output = new FileOutputStream("/sdcard/file_name.extension");
            output = new FileOutputStream("/sdcard/"+PATHMAIN+"/"+name);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // take CPU lock to prevent CPU from going off if the user
        // presses the power button during download
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();
//        mProgressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // if we get here, length is known, now set indeterminate to false
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgress(progress[0]);


    }

    public Context getContext() {
        return context;
    }

    @Override
    protected void onPostExecute(String result) {
        mWakeLock.release();
//        mProgressDialog.dismiss();
        if (result != null) {
            Toast.makeText(context, "خطا در دریافت !!! ", Toast.LENGTH_LONG).show();
        }else {
            CustomeVoice customeVoice=new CustomeVoice(getContext());
            customeVoice.linearMainDonPlay.callOnClick();
            Toast.makeText(context, " فایل دریافت شد ", Toast.LENGTH_SHORT).show();
            ActivitySelectDoreh activitySelectDoreh=new ActivitySelectDoreh();
            activitySelectDoreh.newVoice(name,0+"",PATHMAIN);

        }
    }


}
