package com.circlemind;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import static com.circlemind.Constant.IP;


public class ActivitySplash extends Activity {




    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }
        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);


//        Delay For Page
//        Integer delay=3000;
//        Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(ActivitySplash.this, ActivityFirstPage.class);
//                startActivity(intent);
//            }
//        },delay);
        if (!hasInternetConnection(ActivitySplash.this)) {
            Intent intent = new Intent(ActivitySplash.this, ActivitySplashdiss.class);
            startActivity(intent);
            finish();
        } else {


//        final Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                if (!ActivityFirstPage.sliderpic.equals("")) {
//
//
//                    if (hasInternetConnection(getApplicationContext()) ) {
//                        Intent intent = new Intent(ActivitySplash.this, ActivitySignin.class);
//                        startActivity(intent);
//                        finish();
//                        timer.cancel();
////                        Toast.makeText(getApplicationContext(), "ارتباط برقرار است", Toast.LENGTH_LONG).show();
//
//                    } else {
//                        Intent intent = new Intent(ActivitySplash.this, ActivitySplashdiss.class);
//                        startActivity(intent);
//                        finish();
//                        timer.cancel();
//                        Toast.makeText(getApplicationContext(), "ارتباط با اینترنت قطع است !!!", Toast.LENGTH_LONG).show();
//                    }
//                }
////                else{
////                    Intent intent = new Intent(ActivitySplash.this, ActivitySplashdiss.class);
////                    startActivity(intent);
////                    finish();
//////                    Toast.makeText(getApplicationContext(), "ارتباط با سرور قطع است !!!", Toast.LENGTH_LONG).show();
////                }
//            }
//        }, 1, 3000);


            final Timer timer = new Timer();
            final Timer timercheck = new Timer();

            timercheck.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(ActivitySplash.this, ActivitySignin.class);
//                                            Intent intent = new Intent(ActivitySplash.this, ActivityFingerprint.class);
                                    startActivity(intent);
                                    timer.cancel();
                                    timercheck.cancel();
                                    finish();

                                }
                            });
                        }
                    }, 2000, 1000);
                }
            }, 3000, 1000);
        }
    }
}
