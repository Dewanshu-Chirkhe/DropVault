package com.dewanshu.dropvault.util;

import java.security.SecureRandom;

public class CodeGenerator {

    private static final String HEX = "0123456789ABCDEF";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateCode() {

        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < 6; i++) {
            sb.append(HEX.charAt(RANDOM.nextInt(16)));
        }

        return sb.toString();
    }
}