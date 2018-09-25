package com.cas.utils;

/**
 * Created by lep on 18-9-2.
 */
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;


public class PasswordHelper {
    private static String algorithmName = "md5";

    private static int hashIterations = 2;

    public static String encryptPassword(String passwd, String salt) {
        String newPassword = new SimpleHash(
                algorithmName,
                passwd,
                ByteSource.Util.bytes(salt),
                hashIterations).toHex();
        return newPassword;

    }
}
