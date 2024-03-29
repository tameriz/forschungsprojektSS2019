package util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

//AESCodec for encrypt and decrypt strings which will be send
public class AESCodec{

    public static SecretKey generateKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
        return new SecretKeySpec(password.getBytes(), "AES");
    }

    public static byte[] encryptMsg(String message, SecretKey secret)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException,
                   IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
        /* Encrypt the message. */
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));
        return cipherText;
    }

    public static String decryptMsg(byte[] cipherText, SecretKey secret)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException,
                   InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException,
                   IllegalBlockSizeException, UnsupportedEncodingException{
        /* Decrypt the message, given derived encContentValues and initialization vector. */
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        String decryptString = new String(cipher.doFinal(cipherText), "UTF-8");
        return decryptString;
    }

    public void encrypt(String key, String message)
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException,
                   UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
                   InvalidParameterSpecException{
        SecretKey secret = generateKey(key);
        this.encryptMsg(message, secret);
    }

    public void decrypt(String key, String message)
            throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException,
                   UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
                   InvalidParameterSpecException, InvalidAlgorithmParameterException{
        byte[] messageByte = message.getBytes();
        SecretKey secret = generateKey(key);
        this.decryptMsg(messageByte, secret);
    }
}
