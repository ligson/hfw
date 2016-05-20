package org.ligson.fw.string.encode;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

/**
 * Created by ligson on 2016/3/28.
 * hash工具类
 *
 * @author ligson
 */
public class HashHelper {

    public static byte[] hash(byte[] source, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(source);
            return digest.digest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String hash(String source, String algorithm, String charset) {
        try {
            byte[] buffer = source.getBytes(charset);
            byte[] bytes = hash(buffer, algorithm);
            return Hex.encodeHexString(bytes).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] md5(byte[] source) {
        return hash(source, "MD5");
    }


    public static String md5(String source, String charset) {
        return hash(source, "MD5", charset);
    }

    public static String md5(String source) {
        return md5(source, "UTF-8");
    }

    public static String sha1(String source, String charset) {
        return hash(source, "SHA1", charset);
    }

    public static String sha1(String source) {
        return md5(source, "SHA1");
    }
}
