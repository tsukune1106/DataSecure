package com.example.tsukune.datasecure;

import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Password_Storage {

    //encryption key = password to unlock the storage

    public String Encrypt(String key, String password) throws Exception {

        //Generate random IV
        byte[] ivBytes = new byte[16];
        SecureRandom rng = new SecureRandom();
        rng.nextBytes(ivBytes);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        //Generate Encryption Key
        SecretKeySpec secKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        //Encrypt
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, secKeySpec, iv);
        byte[] encrypted = cipher.doFinal(password.getBytes());
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    public String Decrypt(String key, String initVector, String EncryptedPassword) throws Exception {

        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes());
        SecretKeySpec secKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secKeySpec, iv);

        byte[] original = cipher.doFinal(Base64.decode(EncryptedPassword, Base64.DEFAULT));
        return new String(original);
    }
}
