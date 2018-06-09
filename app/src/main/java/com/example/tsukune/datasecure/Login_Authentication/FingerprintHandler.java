package com.example.tsukune.datasecure.Login_Authentication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import com.example.tsukune.datasecure.Login_Authentication.Login_Fingerprint;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private CancellationSignal cancellationSignal;
    private Context context;
    Login_Fingerprint lf = new Login_Fingerprint();

    public FingerprintHandler (Context mContext) {
        this.context = mContext;
    }

    public void StartAuth (FingerprintManager fm, FingerprintManager.CryptoObject cryptoObject) {
        cancellationSignal = new CancellationSignal();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
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
        lf.getNotification("Access Granted!", true, context);
    }
}
