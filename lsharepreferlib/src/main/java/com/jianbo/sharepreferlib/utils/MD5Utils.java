package com.jianbo.sharepreferlib.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MD5Utils {
    public static String MD5(String srcString) {
        if (TextUtils.isEmpty(srcString)) {
            throw new IllegalArgumentException("srcString cannot be null!");
        }

        try {
            return MD5(srcString.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String MD5(byte[] srcBytes) {
        if (srcBytes == null) {
            throw new IllegalArgumentException("bytes cannot be null!");
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(srcBytes);
            byte[] bytes = md.digest();
            int i = 0;
            StringBuffer buffer = new StringBuffer("");
            for (int offset = 0; offset < bytes.length; offset++) {
                i = bytes[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buffer.append("0");
                }
                buffer.append(Integer.toHexString(i));
            }
            return buffer.toString();
        } catch (Exception e) {
        }
        return "";
    }
    private MD5Utils() {
    }
}