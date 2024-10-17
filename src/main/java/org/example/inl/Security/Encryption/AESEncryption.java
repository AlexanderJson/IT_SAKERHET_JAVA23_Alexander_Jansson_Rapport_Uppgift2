package org.example.inl.Security.Encryption;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


@Component
public class AESEncryption {

    private static final String AES = "AES";
    //env sen

    private final SecretKey secretKey;

    public AESEncryption(SecretKey secretKey) {
        this.secretKey = secretKey;
    }



    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(data.getBytes());

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(data));
        return new String(decrypted);
    }

}
