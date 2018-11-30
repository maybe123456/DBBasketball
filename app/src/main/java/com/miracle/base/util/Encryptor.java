package com.miracle.base.util;


import java.security.Key;

import javax.crypto.Cipher;


/**
 * DES加密和解密工具,可以对字符串进行加密和解密操作  。
 *
 * @author p00121731
 */
public final class Encryptor {

    /**
     * 加密的密钥
     */
    private static String encryptorStringKey = "%$@#ABCD009876Vn)(!!!!!!!!!!";

    /**
     * 默认构造方法，使用默认密钥
     */
    private Encryptor() throws BaseException {
        this(strDefaultKey);
    }

    /**
     * 指定密钥构造方法
     *
     * @param strKey 指定的密钥
     * @throws Exception
     */
    private Encryptor(String strKey) throws BaseException {
        try {
            Key key = getKey(strKey.getBytes("utf-8"));
            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            throw new BaseException(e);
        }
        // Security.addProvider(new com.sun.crypto.provider.SunJCE());
    }

    /**
     * 字符串默认键值
     */
    private static String strDefaultKey = "national";
    /**
     * 加密工具
     */
    private Cipher encryptCipher = null;
    /**
     * 解密工具
     */
    private Cipher decryptCipher = null;

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    private static String byteArr2HexStr(byte[] arrB) throws BaseException {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append('0');
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     */
    private static byte[] hexStr2ByteArr(String strIn) throws BaseException {
        try {
            byte[] arrB = strIn.getBytes("utf-8");
            int iLen = arrB.length;
            // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
            byte[] arrOut = new byte[iLen / 2];
            for (int i = 0; i < iLen; i = i + 2) {
                String strTmp = new String(arrB, i, 2, "utf-8");
                arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
            }
            return arrOut;
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    /**
     * 加密字节数组
     *
     * @param arrB 需加密的字节数组
     * @return 加密后的字节数组
     */
    private byte[] encrypt(byte[] arrB) throws BaseException {
        try {
            return encryptCipher.doFinal(arrB);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    /**
     * 加密字符串
     *
     * @param strIn 需加密的字符串
     * @return 加密后的字符串
     */
    private String encrypt(String strIn) throws BaseException {
        try {
            return byteArr2HexStr(encrypt(strIn.getBytes("utf-8")));
        } catch (Exception e) {
            throw new BaseException(e);
        }

    }

    /**
     * 解密字节数组
     *
     * @param arrB 需解密的字节数组
     * @return 解密后的字节数组
     */
    private byte[] decrypt(byte[] arrB) throws BaseException {
        try {
            return decryptCipher.doFinal(arrB);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    /**
     * 解密字符串
     *
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     */
    private String decrypt(String strIn) throws BaseException {
        try {
            return new String(decrypt(hexStr2ByteArr(strIn)));
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     *
     * @param arrBTmp 构成该字符串的字节数组
     * @return 生成的密钥
     */


    private Key getKey(byte[] arrBTmp) throws BaseException {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];
        // 将原始字节数组转换为8位
//        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
//            arrB[i] = arrBTmp[i];
//        }
        int minLenth = arrBTmp.length<arrB.length?arrBTmp.length:arrB.length;
        System.arraycopy(arrBTmp,0,arrB,0,minLenth);
        // 生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
        return key;
    }


    /**
     * 加密方法
     *
     * @param data
     * @return
     */
    public static String encryptString(String data) {
        if (null == data) {
            return "";
        }
        try {
            Encryptor encryptor = new Encryptor(encryptorStringKey);//密钥
            return encryptor.encrypt(data);
        } catch (Exception e) {
//            LogUtils.info("exception", e);
            return "";
        }
    }

    /**
     * 解密方法
     *
     * @param data
     * @return
     */
    public static String decryptString(String data) {
        if (null == data) {
            return "";
        }
        try {
            Encryptor encryptor = new Encryptor(encryptorStringKey);//密钥
            return encryptor.decrypt(data);
        } catch (Exception e) {
//            LogUtils.info("exception", e);
            return "";
        }
    }
}

