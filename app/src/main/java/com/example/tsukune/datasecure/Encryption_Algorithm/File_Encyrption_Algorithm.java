package com.example.tsukune.datasecure.Encryption_Algorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class File_Encyrption_Algorithm {

    public void file_encrypt_decrypt(int cipherMode, String key, File inputFile, File outputFile) throws Exception {

        FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(outputFile);

        byte[] salt = key.getBytes();
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(key.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

        if (cipherMode == Cipher.ENCRYPT_MODE){
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            Write(cis, fos);
        }
        else if (cipherMode == Cipher.DECRYPT_MODE){
            cipher.init(Cipher.DECRYPT_MODE, secret);
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);
            Write(fis, cos);
        }
    }

    public void Write(InputStream in, OutputStream out) throws Exception {
        byte[] buffer = new byte[8];
        int numberOfBytes;
        while((numberOfBytes = in.read(buffer)) != -1) {
            out.write(buffer, 0, numberOfBytes);
        }
        out.flush();
        out.close();
        in.close();
    }
}
