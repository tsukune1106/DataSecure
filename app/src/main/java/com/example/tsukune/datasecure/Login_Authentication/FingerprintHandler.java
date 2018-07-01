package com.example.tsukune.datasecure.Login_Authentication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tsukune.datasecure.Menu;
import com.example.tsukune.datasecure.R;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private CancellationSignal cancellationSignal;
    private static Context context;
    Login_Fingerprint lf = new Login_Fingerprint();

    public FingerprintHandler (Context mContext) {
        this.context = mContext;
    }

    public void StartAuth (FingerprintManager fm, FingerprintManager.CryptoObject cryptoObject) {
        cancellationSignal = new CancellationSignal();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            lf.getNotification("Permission is not granted to use fingerprint authentication", false, context);
        }
        fm.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        lf.getNotification("Authentication Error!", false, context);
    }

    @Override
    public void onAuthenticationFailed() {
        lf.getNotification("Authentication Failed: Please place your fingerprint at the scanner a little longer", false, context);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        Toast.makeText(context, "Access Granted!", Toast.LENGTH_LONG).show();
        context.startActivity(new Intent(context, Menu.class));

    }
}
