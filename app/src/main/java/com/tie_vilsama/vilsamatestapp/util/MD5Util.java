package com.tie_vilsama.vilsamatestapp.util;

/**
 * Created by mac on 6/3/16.
 */
public class MD5Util {

    /**
     * Returns the hexadecimal String representation of the byte array result of a md5 digest
     * @param bytes md5 digest byte array
     * @return hexadecimal string of digest
     */
    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

}
