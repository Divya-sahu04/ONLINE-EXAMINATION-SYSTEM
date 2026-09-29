package com.onlineexam.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    private PasswordUtil() {
        // Prevent object creation
    }

    public static String hashPassword(
            String password) {

        return BCrypt.hashpw(
                password,
                BCrypt.gensalt(12)
        );
    }

    public static boolean checkPassword(
            String password,
            String hashedPassword) {

        if (password == null ||
                hashedPassword == null) {

            return false;
        }

        return BCrypt.checkpw(
                password,
                hashedPassword
        );
    }
}
