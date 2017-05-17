package io.github.xtman.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class ChecksumUtils {

    public static final int BUFFER_SIZE = 1024;

    public static String toHexString(byte[] digest, boolean uppercase) {
        BigInteger bi = new BigInteger(1, digest);
        return String.format("%0" + (digest.length << 1) + (uppercase ? "X" : "x"), bi);
    }

    public static String toHexString(byte[] digest) {
        return toHexString(digest, false);
    }

    public static byte[] md5(InputStream in) throws Throwable {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try {
            DigestInputStream dis = new DigestInputStream(in, md);
            byte[] buffer = new byte[BUFFER_SIZE];
            while (dis.read(buffer) != -1) {
                //
            }
            dis.close();
        } finally {
            in.close();
        }
        return md.digest();
    }

    public static byte[] md5(File f) throws Throwable {
        return md5(new BufferedInputStream(new FileInputStream(f)));
    }

    public static String md5sum(InputStream in) throws Throwable {
        return toHexString(md5(in), false);
    }

    public static String md5sum(File f) throws Throwable {
        return md5sum(new BufferedInputStream(new FileInputStream(f)));
    }

    public static byte[] md5(byte[] bytes) throws Throwable {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return md.digest(bytes);
    }

    public static String md5sum(byte[] bytes) throws Throwable {
        return toHexString(md5(bytes), false);
    }

    public static byte[] md5(String str) throws Throwable {
        return md5(str.getBytes());
    }

    public static String md5sum(String str) throws Throwable {
        return toHexString(md5(str), false);
    }
}
