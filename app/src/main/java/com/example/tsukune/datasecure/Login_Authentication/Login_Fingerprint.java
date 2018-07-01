package com.example.tsukune.datasecure.Login_Authentication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsukune.datasecure.Entity.User;
import com.example.tsukune.datasecure.FS_Menu.Access_File_Function;
import com.example.tsukune.datasecure.MainActivity;
import com.example.tsukune.datasecure.R;
import com.example.tsukune.datasecure.UserDB.UserViewModel;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;

public class Login_Fingerprint extends Fragment {

    private UserViewModel userViewModel;
    private TextView textView_Login_Username3, notification;
    private View view;
    private User user;
    public static int REQUEST_CODE_USE_FINGERPRINT_PERMISSION = 1;

    //FingerprintManager & KeyguardManager
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private FingerprintHandler fingerprintHandler;
    private FingerprintManager.CryptoObject cryptoObject;

    //KeyStore
    private KeyStore keyStore;
    private Cipher cipher;
    private String KEY_NAME = "AndroidKey";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login_fingerprint, container, false);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        textView_Login_Username3 = view.findViewById(R.id.textView_Login_Username3);

        notification = view.findViewById(R.id.fingerprint_notification);

        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                user = users.get(0);
                textView_Login_Username3.setText(user.getUsername());
            }
        });

        notification.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager = (FingerprintManager) getActivity().getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getActivity().getSystemService(KEYGUARD_SERVICE);

            if (!fingerprintManager.isHardwareDetected()) {
                notification.setText("Your device doesn't support fingerprint authentication");
            }
            else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
                notification.setText("Please enable the fingerprint authentication's permission");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_USE_FINGERPRINT_PERMISSION);
            }
            else if (!keyguardManager.isKeyguardSecure()) {
                notification.setText("Please enable lock screen security in your device's settings");
            }
            else if (!fingerprintManager.hasEnrolledFingerprints()) {
                notification.setText("No fingerprint configured. Please register at least one fingerprint in your device's Settings");
            }
            else {
                notification.setVisibility(View.GONE);
                GenerateKey();

                if (CipherInit()) {

                    cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    fingerprintHandler = new FingerprintHandler(getActivity());
                    fingerprintHandler.StartAuth(fingerprintManager, cryptoObject);
                }
            }
        }
        return view;
    }

    public void getNotification(String s, boolean b, Context context) {
        notification = view.findViewById(R.id.fingerprint_notification);
        notification.setText(s);

        if (b == false) {
            notification.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }
        else {
            notification.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void GenerateKey(){
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        }
        catch (KeyStoreException | IOException | CertificateException | NoSuchAlgorithmException
                | InvalidAlgorithmParameterException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean CipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC
            + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);

            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);

            cipher.init(Cipher.ENCRYPT_MODE, key);

            return true;
        }
        catch (KeyPermanentlyInvalidatedException e) {
            return false;
        }
        catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_USE_FINGERPRINT_PERMISSION) {
            int grantResultsLength = grantResults.length;
            if (grantResultsLength > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission Granted to use fingerprint now!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else {
                Toast.makeText(getActivity(), "Permission Denied to use fingerprint!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
