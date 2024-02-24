package com.zw.graducate.util;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2023/12/3 14:41
 */

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

public class AESUtil {
    /**
     * 加密的Key
     */
    public static final String AESKEY = "2311d32f6fbf5fb09w01e733b67958dd";
    /**
     * 同意是用的编码
     */
    private static String AESCODE = "UTF-8";


    public static SecretKeySpec generateMySQLAESKey(final String key, final String encoding) {
        try {
            byte[] finalKey = new byte[16];
            int i = 0;
            for (byte b : key.getBytes(encoding)) {
                finalKey[i++ % 16] ^= b;
            }
            return new SecretKeySpec(finalKey, "AES");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES 解密
     * data : 待解密的数据
     */
    public static String decrpt(String data) {
        // Decrypt
        try {
            Cipher decryptCipher = Cipher.getInstance("AES");
            decryptCipher.init(Cipher.DECRYPT_MODE, generateMySQLAESKey(AESKEY, AESCODE));
            return new String(decryptCipher.doFinal(Hex.decodeHex(data.toCharArray())));
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * AES加密
     * data : 待加密 的数据
     */
    public static String encrpt(String data) {
        // Encrypt
        try {
            Cipher encryptCipher = Cipher.getInstance("AES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, generateMySQLAESKey(AESKEY, AESCODE));
            char[] code = Hex.encodeHex(encryptCipher.doFinal(data.getBytes(AESCODE)));
            StringBuilder builder = new StringBuilder();
            for (char d : code) {
                builder.append(d);
            }
            return builder.toString().toUpperCase();

        } catch (Exception e) {
            return "";
        }
    }

    public static void main(String[] args) throws Exception {
//        空值可以加密
        String s = AESUtil.encrpt("16716658936");
        String str = AESUtil.decrpt("CCEE02F97D4DA8DA54961707C3A82050");
        System.out.println(s);
        System.out.println(str);
    }

}
