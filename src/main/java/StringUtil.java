/*
 * Developed by Daniel Chuev.
 * Last modified 24.12.18 1:37.
 * Copyright (c) 2018. All Right Reserved.
 */

import java.security.MessageDigest;

/**
 * @author Daniel Chuev
 */
class StringUtil {

    // applies Sha256 to a string and returns the result.
    static String applySha256(String input){

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // applies sha256 to our input,
            byte[] hash = digest.digest(input.getBytes("UTF-8"));

            StringBuffer hexString = new StringBuffer(); // this will contain hash as hexidecimal
            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    // returns difficulty string target, to compare to hash. difficulty of 5 will return "00000"
    static String getDificultyString(int difficulty) {
        return new String(new char[difficulty]).replace('\0', '0');
    }
}
