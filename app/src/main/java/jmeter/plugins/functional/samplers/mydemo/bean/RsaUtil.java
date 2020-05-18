package jmeter.plugins.functional.samplers.mydemo.bean;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import jmeter.plugins.functional.samplers.mydemo.util.Base64Utils;

public class RsaUtil{
    /**
     * RSA加密
     */
    public static String encrypts( String str, String publicKey ) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64Utils.decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64Utils.encode(cipher.doFinal(str.getBytes("UTF-8")));
    }

}