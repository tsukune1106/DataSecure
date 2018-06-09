package com.example.tsukune.datasecure;

import android.util.Base64;
import org.mindrot.jbcrypt.BCrypt;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Password_Storage_Function {

    private int ivSize = 16;

    //encryption key = password to unlock the storage

    public String Encrypt(String key, String password) throws Exception {

        //Generate random IV
        byte[] ivBytes = new byte[ivSize];
        SecureRandom rng = new SecureRandom();
        rng.nextBytes(ivBytes);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        //Hash Encryption Key
        String hashedKey = BCrypt.hashpw(key, BCrypt.gensalt(10));
        SecretKeySpec secKeySpec = new SecretKeySpec(hashedKey.getBytes("UTF-8"), "AES");

        //Encrypt
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, secKeySpec, iv);
        byte[] encrypted = cipher.doFinal(password.getBytes());

        //Combine IV with encrypted password
        byte[] combined = new byte[ivSize + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, ivSize);
        System.arraycopy(encrypted, 0, combined, ivSize, encrypted.length); //here ivSize start from 16 due to the array size is 0 to 15

        return Base64.encodeToString(combined, Base64.DEFAULT);
    }

    public String Decrypt(String key, String EncryptedPassword) throws Exception {

        //Extract IV
        byte[] ivBytes = new byte[ivSize];
        System.arraycopy(EncryptedPassword, 0, ivBytes, 0, ivSize);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        //Extract encrypted password
        int encryptedSize = EncryptedPassword.length() - ivSize;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(EncryptedPassword, ivSize, encryptedBytes, 0, encryptedSize);

        SecretKeySpec secKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secKeySpec, iv);

        byte[] original = cipher.doFinal(Base64.decode(EncryptedPassword, Base64.DEFAULT));
        return new String(original);
    }
}
