package com.example.tsukune.datasecure.Encryption_Algorithm;

import android.os.AsyncTask;
import android.util.Base64;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Password_Encryption_Algorithm {

    private int ivSize = 16;

    //encryption key = password to unlock the storage

    public String Encrypt(String key, String str) throws Exception {

        //Generate random IV
        byte[] iv = new byte[ivSize];
        SecureRandom rng = new SecureRandom();
        rng.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        byte[] salt = key.getBytes();
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(key.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

        //Encrypt
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, secret, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(str.getBytes());

        //Combine IV with encrypted password
        byte[] combined = new byte[ivSize + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, ivSize);
        System.arraycopy(encrypted, 0, combined, ivSize, encrypted.length); //here ivSize start from 16 due to the array size is 0 to 15

        return Base64.encodeToString(combined, Base64.DEFAULT);
    }

    public String Decrypt(String key, String encryptedStr) throws Exception {

        //Extract IV
        byte[] iv = new byte[ivSize];
        byte[] encryptedStrBytes = encryptedStr.getBytes();
        System.arraycopy(encryptedStrBytes, 0, iv, 0, ivSize);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        byte[] salt = key.getBytes();
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(key.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secret, ivParameterSpec);

        byte[] decryptStr = cipher.doFinal(Base64.decode(encryptedStrBytes, Base64.DEFAULT));
        int length = decryptStr.length - ivSize;
        byte[] original = new byte[length];
        System.arraycopy(decryptStr, ivSize, original, 0, length);
        return new String(original);
    }

    public List<String> Ein_PS_Encryption (String encryptionKey, String ps_Name, String ps_Password) throws ExecutionException, InterruptedException {
        return new Ein_PS_Encryption_AsyncTask(encryptionKey, ps_Name, ps_Password).execute().get();
    }

    public List<String> Ein_PS_Decryption (String encryptionKey, String ps_Name, String ps_Password) throws ExecutionException, InterruptedException {
        return new Ein_PS_Decryption_AsyncTask(encryptionKey, ps_Name, ps_Password).execute().get();
    }

    private static class Ein_PS_Encryption_AsyncTask extends AsyncTask<List<String>, Void, List<String>> {

        private String encryptionKey, PS_Name, PS_Password;
        String Encrypted_PS_Name, Encrypted_PS_Password;
        private List<String> strList;
        Password_Encryption_Algorithm pea = new Password_Encryption_Algorithm();

        public Ein_PS_Encryption_AsyncTask(String encryptionKey, String PS_Name, String PS_Password) {
            this.encryptionKey = encryptionKey;
            this.PS_Name = PS_Name;
            this.PS_Password = PS_Password;
        }

        @Override
        protected List<String> doInBackground(List<String>... lists) {
            strList = new ArrayList<>();
            try {
                Encrypted_PS_Name = pea.Encrypt(encryptionKey, PS_Name);
                Encrypted_PS_Password = pea.Encrypt(encryptionKey, PS_Password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            strList.add(Encrypted_PS_Name);
            strList.add(Encrypted_PS_Password);
            return strList;
        }
    }

    private static class Ein_PS_Decryption_AsyncTask extends AsyncTask<List<String>, Void, List<String>> {

        private String encryptionKey, PS_Name, PS_Password;
        String Decrypted_PS_Name, Decrypted_PS_Password;
        private List<String> strList;
        Password_Encryption_Algorithm pea = new Password_Encryption_Algorithm();

        public Ein_PS_Decryption_AsyncTask(String encryptionKey, String PS_Name, String PS_Password) {
            this.encryptionKey = encryptionKey;
            this.PS_Name = PS_Name;
            this.PS_Password = PS_Password;
        }

        @Override
        protected List<String> doInBackground(List<String>... lists) {
            strList = new ArrayList<>();
            try {
                Decrypted_PS_Name = pea.Decrypt(encryptionKey, PS_Name);
                Decrypted_PS_Password = pea.Decrypt(encryptionKey, PS_Password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            strList.add(Decrypted_PS_Name);
            strList.add(Decrypted_PS_Password);
            return strList;
        }
    }
}
