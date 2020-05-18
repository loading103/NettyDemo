package jmeter.plugins.functional.samplers.mydemo.util;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import jmeter.plugins.functional.samplers.mydemo.bean.RsaUtil;

public class AesUtil {

    private static final String AES = "AES";

    private static final String IV_PARAMETER_SPEC = "dMitHORyqbeYVE0o";

    private static final String CIPHER_MODEL = "AES/CBC/PKCS7Padding";

    private static final String[] ORIGIN_STR_ARRAY = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456".split("");
    /**
     * 加密
     */
    public static String encrypt(String key, String content) {
        byte[] encryptedBytes = new byte[0];
        try {
            byte[] contentByte = content.getBytes("UTF-8");
            // 注意，为了能与 iOS 统一
            // 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
            byte[] keyByte = key.getBytes();
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, AES);
            byte[] initParam = IV_PARAMETER_SPEC.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            // 指定加密的算法、工作模式和填充方式
            Cipher cipher = Cipher.getInstance(CIPHER_MODEL);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
            encryptedBytes = cipher.doFinal(contentByte);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("AesUtil", "内容:" + content + "异常:" + e.toString());
        }
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    /**
     * 解密
     */
    public static String decrypt(String key, String content) {
        byte[] decryptBytes = new byte[0];
        try {
            byte[] contentByte = Base64.decode(content, Base64.DEFAULT);
            byte[] keyByte = key.getBytes();
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, AES);
            byte[] initParam = IV_PARAMETER_SPEC.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            // 指定加密的算法、工作模式和填充方式
            Cipher cipher = Cipher.getInstance(CIPHER_MODEL);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
            decryptBytes = cipher.doFinal(contentByte);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("AesUtil", "内容:" + content + "异常:" + e.toString());
        }
        return new String(decryptBytes);
    }


    private static final Random random = new Random();
    public static String generateIv() {
        return randomStr();
    }
    public static String generateKey() {
        return randomStr();
    }
    public static final String randomStr() {
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < 16; i++) {
            int index = random.nextInt(ORIGIN_STR_ARRAY.length);
            buffer.append(ORIGIN_STR_ARRAY[index]);
        }
        return buffer.toString();
    }

    /**
     * 加密
     */
    public static String encrypt(String key,String iv, String content) {
        byte[] encryptedBytes = new byte[0];
        try {
            byte[] contentByte = content.getBytes("UTF-8");
            // 注意，为了能与 iOS 统一
            // 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
            byte[] keyByte = key.getBytes();
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, AES);
            byte[] initParam = iv.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            // 指定加密的算法、工作模式和填充方式
            Cipher cipher = Cipher.getInstance(CIPHER_MODEL);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
            encryptedBytes = cipher.doFinal(contentByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64Utils.encode(encryptedBytes);
    }

    public static String decrypt(String key,String iv, String content) {
        if(!TextUtils.isEmpty(key)|| !TextUtils.isEmpty(iv)){
            return "";
        }
        byte[] decryptBytes = new byte[0];
        try {
            byte[] contentByte =Base64Utils.decode(content);
            byte[] keyByte = key.getBytes();
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, AES);
            byte[] initParam = iv.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            // 指定加密的算法、工作模式和填充方式
            Cipher cipher = Cipher.getInstance(CIPHER_MODEL);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
            decryptBytes = cipher.doFinal(contentByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(decryptBytes);
    }


}
