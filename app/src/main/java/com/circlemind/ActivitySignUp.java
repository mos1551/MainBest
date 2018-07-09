package com.circlemind;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static com.circlemind.Constant.IP;

public class ActivitySignUp extends AppCompatActivity {


    public static String dataAdd = "";
    LinearLayout linearOkSend;
    LinearLayout linearcancel;

    AppCompatEditText signUp_name;
    AppCompatEditText signUp_CodeM;
    AppCompatEditText signUp_Mobile;
    AppCompatEditText signUp_Tel;

    AppCompatEditText signUp_Pass;
    AppCompatEditText signUp_Pass_Repeat;
    AppCompatRadioButton signUp_Male;
    AppCompatRadioButton signUp_FeMale;

    String name = "";
    String codem;
    String mobile;
    String tel;
    String pass = "";
    String rePass = "";
    int gender = 1;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        linearOkSend = (LinearLayout) findViewById(R.id.linearOkSend);
        linearcancel = (LinearLayout) findViewById(R.id.linearcancel);

        signUp_name = (AppCompatEditText) findViewById(R.id.signUp_name);
        signUp_CodeM = (AppCompatEditText) findViewById(R.id.signUp_CodeM);
        signUp_Mobile = (AppCompatEditText) findViewById(R.id.signUp_Mobile);
        signUp_Tel = (AppCompatEditText) findViewById(R.id.signUp_Tel);
        signUp_Pass = (AppCompatEditText) findViewById(R.id.signUp_Pass);
        signUp_Male = (AppCompatRadioButton) findViewById(R.id.signUp_Male);
        signUp_FeMale = (AppCompatRadioButton) findViewById(R.id.signUp_Female);
        signUp_Pass_Repeat = (AppCompatEditText) findViewById(R.id.signUp_Pass_Repeat);


        linearOkSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = signUp_name.getText().toString();
                codem = signUp_CodeM.getText().toString();
                mobile = signUp_Mobile.getText().toString();
                tel = signUp_Tel.getText().toString();

                pass = signUp_Pass.getText().toString();
                rePass = signUp_Pass_Repeat.getText().toString();
                if (signUp_Male.isChecked()) {
                    gender = 1;
                } else {
                    gender = 0;
                }


//               /


//                OK Code Async

                if(pass.equals(rePass)) {
                    new AsyncTaskAddUser(IP + "/AddUser.php", name, codem, mobile, tel, pass, gender).execute();
                    final ProgressDialog dialog = new ProgressDialog(ActivitySignUp.this);
                    dialog.setMessage("لطفا منتظر بمانید...");
                    dialog.show();
                    final Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (dataAdd.equals("sucess")) {
                                        Intent intent=new Intent(ActivitySignUp.this,ActivityFirstPage.class);
                                        startActivity(intent);
                                        dialog.cancel();
                                        timer.cancel();
                                        finish();

                                    }
                                }
                            });
                        }
                    }, 2, 3000);
                }else{
                    Toast.makeText(ActivitySignUp.this,"رمز عبور و تکرار آن یکسان نیست",Toast.LENGTH_SHORT).show();
                }


            }
        });

        linearcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
