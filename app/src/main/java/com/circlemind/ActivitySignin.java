package com.circlemind;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static com.circlemind.Constant.IP;


public class ActivitySignin extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        finish();
    }

    public static String dataAuth = "";
    AppCompatButton login_btn;
    AppCompatEditText loginPass;
    AppCompatEditText loginPhone;
    LinearLayout linearWaitSignIn;
    AppCompatTextView login_activate_account;
    SharedPreferences preferences;
    AppCompatCheckBox login_pass_Remember;
    String passNumber = "";
    String phoneNumber = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_signin);

        login_btn = (AppCompatButton) findViewById(R.id.login_btn);
        loginPass = (AppCompatEditText) findViewById(R.id.login_pass);
        loginPhone = (AppCompatEditText) findViewById(R.id.login_phone);
        login_activate_account=(AppCompatTextView)findViewById(R.id.login_activate_account);
        login_pass_Remember=(AppCompatCheckBox)findViewById(R.id.login_pass_Remember);
        linearWaitSignIn=(LinearLayout)findViewById(R.id.linearWaitSignIn);

        login_activate_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivitySignin.this,ActivitySignUp.class);
                startActivity(intent);
            }
        });


//        phoneNumber = loginPhone.getText().toString();
//        passNumber = loginPass.getText().toString();

        preferences = PreferenceManager.getDefaultSharedPreferences(ActivitySignin.this);
        passNumber = preferences.getString("passNumber", "");
        phoneNumber=preferences.getString("phoneNumber","");


        loginPhone.setText(phoneNumber);
        loginPass.setText(passNumber);

            login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                            phoneNumber = loginPhone.getText().toString();
                            passNumber = loginPass.getText().toString();
                    if (!loginPass.getText().equals("") && !loginPhone.getText().equals("")) {
                        new AsyncTaskLogin(IP + "/login.php", phoneNumber, passNumber).execute();
                        linearWaitSignIn.setVisibility(View.VISIBLE);

                        final Timer timer = new Timer();
                        timer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!dataAuth.equals("")) {
                                            linearWaitSignIn.setVisibility(View.GONE);
                                            if (dataAuth.equals("not")) {
                                                Toast.makeText(ActivitySignin.this, "مشخصات وارد شده اشتباه است ...!", Toast.LENGTH_SHORT).show();
                                                timer.cancel();
                                            } else {
                                                if(login_pass_Remember.isChecked()) {
                                                    preferences = PreferenceManager.getDefaultSharedPreferences(ActivitySignin.this);
                                                    SharedPreferences.Editor editor = preferences.edit();
                                                    editor.putString("phoneNumber", phoneNumber);
                                                    editor.putString("passNumber", passNumber);
                                                    editor.commit();
                                                }
                                                Intent intent = new Intent(ActivitySignin.this, ActivityFirstPage.class);
                                                startActivity(intent);
                                                linearWaitSignIn.setVisibility(View.GONE);
                                                timer.cancel();
                                                finish();
                                            }
                                        }
                                    }
                                });
                            }
                        }, 2, 2000);
                    } else {
                        Toast.makeText(getApplicationContext(), "لطفا مقادیر را وارد کنید!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    }
}

