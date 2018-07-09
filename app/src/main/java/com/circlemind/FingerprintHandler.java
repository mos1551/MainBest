package com.circlemind;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;


@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    // Constructor
    public FingerprintHandler(Context mContext) {
        context = mContext;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

            manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);

    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("خطای دریافت اثر انگشت \n" + errString);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("کمک برای بازکردن اثر انگشت \n" + helpString);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("خطا در ورود اثر انگشت");
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        ((Activity) context).finish();



        Intent intent = new Intent(context, ActivityFirstPage.class);
        context.startActivity(intent);
    }

    private void update(String e){
        TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorText);
        textView.setText(e);
    }

}
