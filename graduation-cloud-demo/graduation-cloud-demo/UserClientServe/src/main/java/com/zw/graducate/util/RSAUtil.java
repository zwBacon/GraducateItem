package com.zw.graducate.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author ZhangWei
 * @version 1.0
 * Create by 2023/12/3 14:51
 */

public class RSAUtil {

    public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIbvs9hQBwKkA3JSawcl+4MiJr0VwhOy672Wd9sTYFsTB3hI7E" +
            "8EVPARZNn3976a2M/3soUQGiEaTDL5UcTkqhzKkG/Q7tiAR6/AyCm/HJhjS/XTgIH+TTKMIVXuVzsXFjlWAQqsyJdMiQyvW6QKdZW9qm9EJLogVyxP+SSM+B8NAgMBAAECg" +
            "YEAhj0FH9dNghUE0MCpdS0WL/jTrRxuPQase6mrhyiZnUErF0EExf87OLE1MZr8voRx2UNEOBgyxmfREozyCfyqNg1OdGYEHSyuJ9wglkhq8GVYO8IzI29Mqej0MSprtsE0BP" +
            "AKBHRU/DWP19ej5bv5ZnAhLs10K7uVEsuGwJJYcMECQQDibedUr7tnGfojyjFY0vCAaVwgS0vXfno7WQyAXUz0Fv8Uy1q9nyF0RrkeA8BOk7S4ljE77ufX0rr2qL7kHW8pAkE" +
            "AmI718EnQCKKJUjrQUl4iG/lYoNwW2QnxTGZmESyFwkS95PTt8K4GVHpICqRNP1JJBNxVSEVts/eA4zrxPAoBRQJBAJxxEsOQJwq1B/5yVGXqWABgyyYE4AGjgRBAFkMaM3Dx8ou" +
            "LdMZOi+6qbnwuW0/u/Y4LNzkRd13GWybQsBMrwwECQEULptmavpG55kaWIcS1n+BjSK59DcYrDs+SJK2vJdaXwA4IoEvmpyzCrypJ1EBNYIjXo61y5sSlxuqQua9/o7UCQGYdM3/" +
            "mF/FEC3wxdfQq0Pw/Pwn8RQxg1natRfoTyzOJDfE/YUYGjIEe2pQtDI1s+IRCwrXOB0cySbpaSHCjr5U=";

    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCG77PYUAcCpANyUmsHJfuDIia9FcITsuu9lnfbE2BbEwd4SOxPBFTwEWTZ9/e+mtjP97" +
            "KFEBohGkwy+VHE5KocypBv0O7YgEevwMgpvxyYY0v104CB/k0yjCFV7lc7FxY5VgEKrMiXTIkMr1ukCnWVvapvRCS6IFcsT/kkjPgfDQIDAQAB";

    /**
     * @param data
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static String decode(String data, boolean print) throws NoSuchProviderException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(PRIVATE_KEY.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
        PrivateKey privateKey = keyFactory.generatePrivate(priPKCS8);
        String decryptData = RSAUtil.decryptData(data, privateKey);
        return decryptData;
    }

    public static String decode(String data) throws NoSuchProviderException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        return decode(data, true);
    }

    /**
     * @param data
     * @param publicKey
     * @return
     */
    public static String encryptData(String data, PublicKey publicKey) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] dataToEncrypt = data.getBytes("utf-8");
            byte[] encryptedData = cipher.doFinal(dataToEncrypt);
            String encryptString = Base64.encodeBase64String(encryptedData);
            return encryptString;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * @param data
     * @param privateKey
     * @return
     */
    public static String decryptDataForOPUser(String data, PrivateKey privateKey) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA", "BC");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] descryptData = Base64.decodeBase64(data);
            byte[] descryptedData = cipher.doFinal(descryptData);
            String srcData = new String(descryptedData, "utf-8");
            return srcData.substring(srcData.length() - 36, srcData.length());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * @param data
     * @param privateKey
     * @return
     */
    public static String decryptData(String data, PrivateKey privateKey) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] descryptData = Base64.decodeBase64(data);
            byte[] descryptedData = cipher.doFinal(descryptData);
            String srcData = new String(descryptedData, "utf-8");
            return srcData;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * @return
     */
    public static PublicKey generatePublicKey() {
        return generatePublicKey(PUBLIC_KEY);
    }

    /**
     * @return
     */
    public static PrivateKey generatePrivateKey() {
        return generatePrivateKey(PRIVATE_KEY);
    }

    /**
     * @return
     */
    public static PublicKey generatePublicKey(String publicKey) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            X509EncodedKeySpec priPKCS8 = new X509EncodedKeySpec(Base64.decodeBase64(publicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            return keyFactory.generatePublic(priPKCS8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return
     */
    public static PrivateKey generatePrivateKey(String privateKey) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            return keyFactory.generatePrivate(priPKCS8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //加密
        String encryptDataString = encryptData("264017Zw@@@", generatePublicKey());
        System.out.println(encryptDataString);
        //解密
        String decryptDataString = decryptData("LKYVEAHVe80sLgjS6w9bploTG/BSaasbcytzmEmWtJ6CMmzsPyEVwrMTaQNvWN31YTVp9y7KAIhXjGFEezWlWVnZBmmnCg0xIHrp9xIwN65hBA1JLhsDNdjvY4zfFV5oPKRxYmivLzv/+kf+9nPYdt088Id/nsf+jedUqc/QXKk=", generatePrivateKey());
        System.out.println(decryptDataString);

    }

}
